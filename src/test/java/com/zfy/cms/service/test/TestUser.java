package com.zfy.cms.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfy.cms.entity.User;
import com.zfy.cms.service.UserService;

public class TestUser extends BaseTest{
	
	@Autowired
	UserService ser;
	@Test
	public void testRegister(){
		
		User user = new User("zhangsan","password",1);
		int register = ser.register(user);
//		asserTure(register>0);
	}

	
}
  