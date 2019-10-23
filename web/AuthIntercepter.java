package com.zfy.cms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.zfy.cms.comon.ConstClass;
import com.zfy.cms.entity.User;

/**
 * 
 * @author zhuzg
 * 
 */
public class AuthIntercepter implements HandlerInterceptor {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		User loginUser = (User)request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		if(loginUser==null) {
			// request.
			request.getRequestDispatcher("/user/login").forward(request, response);
			return false;
		}
		return true;
	}

}
