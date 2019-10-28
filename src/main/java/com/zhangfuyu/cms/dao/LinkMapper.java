package com.zhangfuyu.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhangfuyu.cms.entity.Link;

public interface LinkMapper {
	//获取友情链接
	@Select("select * from cms_link")
	List<Link> linkList();
	//添加
	@Insert("insert into cms_link (http,name) values(#{http},#{name})")
	int addLink(Link link);
	//删除
	@Delete("delete from cms_link where id =#{id}")
	int deleteLink(String id);
	
	
	@Update("update cms_link set http=#{http} ,name=#{name} where id=#{id}")
	int updat(Link link);
	//根据link的id查询link
	@Select("select * from cms_link where id=#{value}")
	Link getById(String id);
}
