package com.zfy.cms.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.zfy.cms.entity.User;

public interface UserMapper {
//注册
	@Insert("insert into cms_user(username,password,gender,create_time)"
			+ "values(#{username},#{password},#{gender},now())")
	int register(User user);
	//根据用户名称查询用户是否存在
	@Select("select id,username,password,role from cms_user where username=#{value}")
	User findByName(String username);

}
