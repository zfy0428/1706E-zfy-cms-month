package com.zhangfuyu.cms.service;

import java.util.List;

import com.zhangfuyu.cms.entity.Article;
import com.zhangfuyu.cms.entity.Comment;
import com.zhangfuyu.cms.entity.User;

public interface UserService {
	//z注册
	int register(User user);
	//登录
	User login(User user);
	////判断用户名是否已占用
	boolean checkUserExist(String username);
	//上传头像
	int addHead_picture(User user);
	//进入用户管理
	List<User> list();
	//修改用户的状态
	int update(Article article);
	List<Comment> queryMyComment(Integer id);
}
   