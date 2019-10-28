package com.zfy.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Utils.Md5Utils;

import com.zfy.cms.dao.UserMapper;
import com.zfy.cms.entity.Article;
import com.zfy.cms.entity.Comment;
import com.zfy.cms.entity.User;
import com.zfy.cms.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper mapper;
	
	@Override
	public int register(User user) {
		// TODO Auto-generated method stub
		User exisUser = mapper.findByName(user.getUsername());
		if(exisUser!=null){
			return -1;//用户已经存在
		}
		//对账户密码进行加盐
		user.setPassword(Md5Utils.md5(user.getPassword(),user.getUsername()));
		return mapper.register(user);
	} 
	//判断用户名是否已占用
	@Override
	public boolean checkUserExist(String username) {
		// TODO Auto-generated method stub
		return null!= mapper.findByName(username);
		
	}
	//接受登录请求
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		//加密
		String pwdStr = Md5Utils.md5(user.getPassword(),user.getUsername());
		//根据用户名称获取用户
		User loginUser = mapper.findByName(user.getUsername());
		if(loginUser!=null && pwdStr.equals(loginUser.getPassword())){
			return loginUser;
		}
		return null;
	}
	//添加头像
	@Override
	public int addHead_picture(User user) {
		return mapper.addHead_picture(user);
	}
	//进入管理用户
	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		return mapper.list();
	}
	//修改用户的状态
	@Override
	public int update(Article article) {
		return mapper.update(article);
	}
	@Override
	public List<Comment> queryMyComment(Integer id) {
		// TODO Auto-generated method stub
		return  mapper.queryMyComment(id);
	}

	

}
