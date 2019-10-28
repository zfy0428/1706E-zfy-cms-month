package com.zfy.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfy.cms.dao.LinkMapper;
import com.zfy.cms.entity.Link;
import com.zfy.cms.service.LinkService;
@Service
public class LinkServiceImpl implements LinkService{

	@Autowired
	LinkMapper mapper;
	//获取友情链接
	@Override
	public List<Link> linkList() {
		// TODO Auto-generated method stub
		return mapper.linkList();
	}
	//添加联机
	@Override
	public int addLink(Link link) {
		// TODO Auto-generated method stub
		return mapper.addLink(link);
	}
	//删除链接
	@Override
	public int deleteLink(String id) {
		// TODO Auto-generated method stub
		return mapper.deleteLink(id);
	}
	//修改链接
	@Override
	public int update(Link link) {
		// TODO Auto-generated method stub
		return mapper.updat(link);
	}
	//根据link的id查询link
	@Override
	public Link getById(String id) {
		// TODO Auto-generated method stub
		return mapper.getById(id);
	}


}
