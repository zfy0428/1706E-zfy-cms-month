package com.zhangfuyu.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangfuyu.cms.dao.ChannelMapper;
import com.zhangfuyu.cms.entity.Channel;
import com.zhangfuyu.cms.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService{
	
	@Autowired
	ChannelMapper mapper;
	//获取所有的栏目
	@Override
	public List<Channel> getAllChnls() {
		// TODO Auto-generated method stub
		return mapper.listAll();
	}


}
