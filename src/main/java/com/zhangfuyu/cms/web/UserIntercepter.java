package com.zhangfuyu.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.zhangfuyu.cms.comon.ConstClass;
import com.zhangfuyu.cms.entity.User;

/**
 * 
 * @author zhuzg
 * 
 */
public class UserIntercepter implements HandlerInterceptor {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断用户是否登录 							根据key获取值  
		User loginUser = (User)request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		//如果用户为空
		if(loginUser==null) {
			// request. 
			//跳转登录页面进行登录
			request.setAttribute("errorMsg", "權限不足");
			request.getRequestDispatcher("/user/login").forward(request, response);
			return false;
		}
		return true;
	}

}
