package com.zfy.cms.service;

import com.zfy.cms.entity.User;

public interface UserService {

	int register(User user);
	User login(User user);
	boolean checkUserExist(String username);
}
   