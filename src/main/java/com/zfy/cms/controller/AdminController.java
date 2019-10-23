package com.zfy.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zfy.cms.comon.ConstClass;
import com.zfy.cms.comon.ResultMsg;
import com.zfy.cms.entity.Article;
import com.zfy.cms.entity.User;
import com.zfy.cms.service.ArticleService;
import com.zfy.cms.web.PageUtils;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	ArticleService artSer;

	@RequestMapping("index")
	public String index(){

		return "admin/index";
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
		return "admin/article/detail";

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
		User loginUser = (User)request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
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
}
