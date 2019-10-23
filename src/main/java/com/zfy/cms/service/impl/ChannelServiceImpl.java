package com.zfy.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfy.cms.dao.ChannelMapper;
import com.zfy.cms.entity.Channel;
import com.zfy.cms.service.ChannelService;

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
