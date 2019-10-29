package com.zhangfuyu.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhangfuyu.cms.entity.Article;
import com.zhangfuyu.cms.entity.Comment;
import com.zhangfuyu.cms.entity.User;

public interface UserMapper {
//注册
	@Insert("insert into cms_user(username,password,gender,create_time)"
			+ "values(#{username},#{password},#{gender},now())")
	int register(User user);
	//根据用户名称查询用户是否存在
	@Select("select id,username,password,role,locked,head_picture from cms_user where username=#{value}")
	User findByName(String username);
	//上传头像
	@Update("update cms_user set head_picture=#{head_picture} where id=#{id}")
	int addHead_picture(User user);
	//用户管理
	List<User> list();
	//修改用户的id用户的状态
	@Update("update cms_user set locked=#{locked} where id=#{id}")
	int update(Article article);
	
	@Select("select c.*,u.username as userName from cms_comment c left join cms_user u on u.id=c.userId"
			+ " where u.id=#{value} ")
	List<Comment> queryMyComment(Integer id);

}
