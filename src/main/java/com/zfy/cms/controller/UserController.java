package com.zfy.cms.controller;

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

import com.github.pagehelper.PageInfo;
import com.zfy.cms.comon.ConstClass;
import com.zfy.cms.entity.Article;
import com.zfy.cms.entity.User;
import com.zfy.cms.service.ArticleService;
import com.zfy.cms.service.UserService;
import com.zfy.cms.web.PageUtils;


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
		}else{
			request.getSession().setAttribute(ConstClass.SESSION_USER_KEY, loginUser);
			if(loginUser.getRole()==ConstClass.USER_ROLE_GENRAL){
				System.out.println("home");
				return "redirect:home";
				//				return "index/index";
			}else if(loginUser.getRole()==ConstClass.USER_ROLE_ADMIN){
				System.out.println("index");
				return "redirect:../admin/index";
			}else{
				//其他情况
				System.out.println("12");
				return "user/login";
			}

		}
	}
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


}
