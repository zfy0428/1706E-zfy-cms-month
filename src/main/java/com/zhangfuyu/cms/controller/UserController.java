package com.zhangfuyu.cms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangfuyu.cms.comon.ConstClass;
import com.zhangfuyu.cms.entity.Article;
import com.zhangfuyu.cms.entity.Comment;
import com.zhangfuyu.cms.entity.Special;
import com.zhangfuyu.cms.entity.User;
import com.zhangfuyu.cms.service.ArticleService;
import com.zhangfuyu.cms.service.UserService;
import com.zhangfuyu.cms.utils.PageUtils;


@Controller()
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService ser;

	@Autowired
	ArticleService articleService;
	
	

	@GetMapping("register")	//接受get的请求 
	//	@RequestMapping(value="register",method=RequestMethod.GET)
	public String register(){
		return "user/register";
	}
	
	
	/**
	 * 跳转到我的评论
	 * @param request
	 * @return
	 */
	@GetMapping("myComment")
	public String myComment(HttpServletRequest request,@RequestParam(defaultValue="1")Integer page){
	
//		获取User
		User user = (User)request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		Integer id = user.getId();
		
		PageHelper.startPage(page, 5);
//		根据User查询
		List<Comment> myComments = ser.queryMyComment(id);
		
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(myComments);
		String pageStr = PageUtils.pageLoad(pageInfo.getPageNum(), pageInfo.getPages(), "/user/myComment", 5);
		request.setAttribute("myComments", myComments);
		request.setAttribute("page", pageStr);
		return "my/myComment";
		
		
	}
	//是拦截
	@RequestMapping("index")
	public String index(){
		return "index";
	}
	//判断用户名是否已经被占用
	@RequestMapping("checkExist")
	@ResponseBody
	public boolean checkExist(String username) {
		return !ser.checkUserExist(username);
	}	

	@PostMapping("register")	//接受post的请求 @Validated是用户的校验
	public String register(HttpServletRequest request,
			@Validated User user,
			BindingResult errorResult) {
		if(errorResult.hasErrors()){
			System.out.println("出错啦");
			return "user/register";
		} 
		int result = ser.register(user);
		if(result>0){
			return "redirect:login";
		}else{
			request.setAttribute("errorMsg", "系统错误，请稍候重试");
			System.out.println("出错啦踢踢踢踢踢");
			return "user/register";
		}


	}
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login(){
		return "user/login";
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		//清空
		request.getSession().removeAttribute(ConstClass.SESSION_USER_KEY);
		return "user/login";
	}

	@PostMapping("login")	//接受post的请求 @Validated是用户的校验
	public String login(HttpServletRequest request,
			@Validated User user,
			BindingResult errorResult) {
		if(errorResult.hasErrors()){
			return "user/login";
		}
		//登录
		User loginUser = ser.login(user);
		if(loginUser==null){
			request.setAttribute("errorMsg", "用户名或密码错误");
			return "user/login";
		}else if(loginUser.getLocked()==1){
			request.setAttribute("errorMsg", "賬戶已經被禁用");
			return "user/login";
		}else{
			if(loginUser.getRole()==ConstClass.USER_ROLE_GENRAL){
				request.getSession().setAttribute(ConstClass.SESSION_USER_KEY, loginUser);
				System.out.println("home");
				return "redirect:home";
				//				return "index/index";
			}else if(loginUser.getRole()==ConstClass.USER_ROLE_ADMIN){
				
				request.getSession().setAttribute(ConstClass.SESSION_ADMIN_KEY, loginUser);
				System.out.println("index");
				return "redirect:../admin/index";
			}else{
				//其他情况
				System.out.println("12");
				return "user/login";
			}

		}
	}
//	 //进入用户管理
//	@RequestMapping("list")
//	public String getlist(HttpServletRequest request) {
//		//查询用户 进入用户列表
//		List<User> list = ser.list();
//		request.setAttribute("list", list);
//		return "admin/article/userlist";
//	}
	//进入个人中心(普通注册用户) 
	@RequestMapping("home")
	public String home(HttpServletRequest request){

		return "my/home";
	}
	//进入个人中心 获取我的文章
	@RequestMapping("myarticleList")
	public String myarticles(HttpServletRequest request,@RequestParam(defaultValue="1")Integer page){
		//获取用户id
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		//根据用户id获取文章
		PageInfo<Article> pageArticles = articleService.listArticleByUserId(loginUser.getId(),page);
		//生成页面信息
		PageUtils.page(request, "/user/myarticleList", 10,pageArticles.getList(), (long)pageArticles.getPageSize(), pageArticles.getPageNum());
		request.setAttribute("pageArticles", pageArticles);
		return "my/list";
	}

	//根据Id删除用户自己的文章
	@RequestMapping("delArticle")
	@ResponseBody
	public boolean delArtucle(Integer id){

		return articleService.remove(id)>0;
	}
	 // 跳转到上传页面
	@GetMapping("toAddhead_picture")
	public String toAddhead_picture() {
		return "my/addhead_picture";
	}
	//上传头像
	@PostMapping("addhead_picture")
	public String addHead_picture(HttpServletRequest request,MultipartFile file) throws IllegalStateException, IOException {
		User user = (User)request.getSession().getAttribute("SESSION_USER_KEY");
		procesFile(file,user);

		ser.addHead_picture(user);
		return "redirect:home";

	}
	/**
	 * 处理接收到的文件
	 */
	
	private void procesFile(MultipartFile file,User user) throws IllegalStateException, IOException {

		// 原来的文件名称
		
		if(file.isEmpty()||"".equals(file.getOriginalFilename()) || file.getOriginalFilename().lastIndexOf('.')<0 ) {
			user.setHead_picture("");
			return;
		}
		//原文件的名称
		String originName = file.getOriginalFilename();
		// 原文件的扩张名 找到.的位置
		String suffixName = originName.substring(originName.lastIndexOf('.'));
		//根据日期分成不同的级别
		SimpleDateFormat sdf=  new SimpleDateFormat("yyyyMMdd");
		//得到一个新的路径
		String path = "d:/pic/" + sdf.format(new Date());
		File pathFile = new File(path);
		//判断路径是否存在
		if(!pathFile.exists()){
			//如果文件不存在 创建一个新的路径
			pathFile.mkdir();
		}
		//定义destFileName  UUID去生成文件名  + 原文件的扩张名 
		String destFileName = path + "/" +  UUID.randomUUID().toString() + suffixName;
		//把原文件保存到新文件的地址   destFileName文件存储的绝对路径
		File distFile = new File( destFileName);
		file.transferTo(distFile);//文件另存到这个目录下边
		//将图片位置存下来 使用字符分割得到图片的地址
		user.setHead_picture(destFileName.substring(7));
		
	}

}
