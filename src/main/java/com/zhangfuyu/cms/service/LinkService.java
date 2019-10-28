package com.zhangfuyu.cms.service;

import java.util.List;

import com.zhangfuyu.cms.entity.Link;

public interface LinkService {
	//友情链接
	List<Link> linkList();
	//添加
	int addLink(Link link);
	//根据id删除
	int deleteLink(String id);
	//查询link
	Link getById(String id);
	//修改
	int update(Link link);

}
