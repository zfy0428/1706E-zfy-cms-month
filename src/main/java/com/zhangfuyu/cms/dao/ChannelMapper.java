package com.zhangfuyu.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.zhangfuyu.cms.entity.Channel;

public interface ChannelMapper {
	//获取所有的栏目
	@Select("select * from cms_channel order by id")
	List<Channel> listAll();
//	根据id获取对应的频道
	List<Channel> findById(Integer id);
}
