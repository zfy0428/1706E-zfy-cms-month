package com.zhangfuyu.cms.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Utils.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zhangfuyu.cms.comon.ArticleType;
import com.zhangfuyu.cms.comon.ConstClass;
import com.zhangfuyu.cms.comon.ResultMsg;
import com.zhangfuyu.cms.entity.Article;
import com.zhangfuyu.cms.entity.Link;
import com.zhangfuyu.cms.entity.User;
import com.zhangfuyu.cms.service.ArticleService;
import com.zhangfuyu.cms.service.LinkService;
import com.zhangfuyu.cms.service.UserService;
import com.zhangfuyu.cms.utils.PageUtils;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	ArticleService artSer;

	@Autowired
	UserService ser;
	
	@Autowired
	LinkService linkSer;

	@RequestMapping("index")
	public String index(){

		return "admin/index";
	}
	//获取友情链接
	@RequestMapping("linkList")
	public String list(HttpServletRequest request,@RequestParam(defaultValue="1")Integer page) {
		
		PageHelper.startPage(page, 5);
		List<Link> linkList = linkSer.linkList();
		
		PageInfo<Link> pageInfo = new PageInfo<Link>(linkList);
		String pageStr = PageUtils.pageLoad(pageInfo.getPageNum(), pageInfo.getPages(), "/admin/linkList", 5);
		//获取友情连接
		request.setAttribute("linkList", linkList);
		
		
		request.setAttribute("page", pageStr);
		
		return "admin/article/link";
	}

	//友情链接的添加的调用方法
	@GetMapping("toAddLink")
	public String addlink( ) {
			return "admin/addLink";
	}
	//友情链接的添加
	@RequestMapping("addLink")
	@ResponseBody
	public ResultMsg addLink(Link link){
		int i = linkSer.addLink(link);
		if(!StringUtils.isUrl(link.getHttp())){
			return new ResultMsg(3, "请输入正确的网址", "");
		}
		if(i>0) {
			return new ResultMsg(1, "添加成功", "");
		}else {
			return new ResultMsg(2, "添加失败，请与管理员联系", "");
		}
//		return "redirect:index";
	}
	@GetMapping("toUpdateLink")
	public String updateLink(HttpServletRequest request,String id) {
		//根据link的id查找link
		Link link = linkSer.getById(id);
		request.setAttribute("link", link);
		return "admin/updateLink";
	}
	//友情链接的修改
	@ResponseBody
		@PostMapping("updateLink")
		public boolean updateLink(Link link){
			return linkSer.update(link)>0;
		}
	//友情链接的删除
	@RequestMapping("deleteLink")
	public String deleteLink(String id){
		int i = linkSer.deleteLink(id);
		return "redirect:index";
	}
	
	//进入用户管理
	@RequestMapping("list")
	public String getlist(HttpServletRequest request,@RequestParam(defaultValue="1")Integer page) {
		//查询用户 进入用户列表
		PageHelper.startPage(page, 5);
		List<User> list = ser.list();
		PageInfo<User> pageInfo = new PageInfo<User>(list);
		String pageStr = PageUtils.pageLoad(pageInfo.getPageNum(), pageInfo.getPages(), "/admin/list", 5);
		
		request.setAttribute("page", pageStr);
		request.setAttribute("list", list);
		return "admin/article/userlist";
	}
	//获取需要管理的文章
	@RequestMapping("manArticle")
	public String adminArticle(HttpServletRequest request,@RequestParam(defaultValue="1")Integer page
			,@RequestParam(defaultValue="0")Integer status){
		PageInfo<Article> pageInfo = artSer.getAdminArticles(page,status);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("status", status);
		String pageStr = PageUtils.pageLoad(pageInfo.getPageNum(), pageInfo.getPages(), "/admin/manArticle?status="+status, 10);
		request.setAttribute("page", pageStr);
		return "admin/article/list";
	}
	//管理文章详情的回显
	@RequestMapping("getArticle")
	public String getArticle(HttpServletRequest request,Integer id){
		Article article = artSer.findById(id);
		request.setAttribute("article", article);
		if(article.getArticleType()==ArticleType.HTML){
			return "admin/article/detail";
		}else{
			Gson gson = new Gson();
			//将图片列表转换成文本内容
			article.setImgList(gson.fromJson(article.getContent(), List.class));
			request.setAttribute("article", article);
			return "admin/article/slieimgarticle";
		}

	}
	/**
	 * 审核文章
	 * @param request
	 * @param articleId 文章ID
	 * @param status 审核后的状态 1审核已通过 2审核未通过
	 * @return
	 */
	@RequestMapping("checkArticle")
	@ResponseBody
	public ResultMsg checkArticle(HttpServletRequest request,Integer articleId,int status){
		//获取session用户
		User loginUser = (User)request.getSession().getAttribute(ConstClass.SESSION_ADMIN_KEY);
		//判断用户是否登录
		if(loginUser == null){
			return new ResultMsg(2, "对不起,您尚未登录,不能审核文章,请登录用户", null);
		}
		//判断用户不是管理用户
		if(loginUser.getRole()!=ConstClass.USER_ROLE_ADMIN){
			return new ResultMsg(2, "对不起,您没用权限审核文章", null);
		}
		//根据文章ID查询文章是否存在
		Article article = artSer.findById(articleId);
		//判断文章是否存在
		if(article==null){
			return new ResultMsg(2, "抱歉,没有这篇文章", null);
		}
		//判断文章的状态
		if(article.getStatus()==status){
			return new ResultMsg(2, "这篇文章的状态就是您想要的状态,无需此操作", null);
		}
		int result = artSer.updateStatus(articleId,status);
		//保存审核是否成功  如果result>0 审核成功
		if(result>0){
			return new ResultMsg(1, "恭喜,审核已通过", null);
		}else{
			return new ResultMsg(5, "抱歉,操作失败,请重新操作", null);
		}
	}
	/**
	 * 设置文明
	 * @param request
	 * @param articleId 文章的id
	 * @param status 热门的状态
	 * @return
	 */
	@RequestMapping("sethot")
	@ResponseBody
	public ResultMsg sethot(HttpServletRequest request,Integer articleId,int status){
		//获取session用户
		User loginUser = (User)request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		//判断用户是否登录
		if(loginUser == null){
			return new ResultMsg(2, "对不起,您尚未登录,不能修改文章热门状态,请登录用户", null);
		}
		//判断用户不是管理用户
		if(loginUser.getRole()!=ConstClass.USER_ROLE_ADMIN){
			return new ResultMsg(2, "对不起,您没用权限设置热门", null);
		}
		//根据文章ID查询文章是否存在
		Article article = artSer.findById(articleId);
		//判断文章是否存在
		if(article==null){
			return new ResultMsg(2, "抱歉,没有这篇文章", null);
		}
		//判断文章的状态
		if(article.getHot()==status){
			return new ResultMsg(2, "这篇文章的状态就是您想要的状态,无需此操作", null);
		}

		int result = artSer.updateHot(articleId,status);
		//修改热门否成功  如果result>0 审核成功
		if(result>0){
			return new ResultMsg(1, "恭喜,操作通过", null);
		}else{
			return new ResultMsg(5, "抱歉,操作失败,请重新操作", null);
		}
	}

	//修改用户的状态
	@ResponseBody
	@RequestMapping("userupadte")
	public boolean userupdate(HttpServletResponse response,HttpServletRequest request,Article article) throws ServletException, IOException {
		return ser.update(article)>0;
	}
}
