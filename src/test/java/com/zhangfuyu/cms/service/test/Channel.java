package com.zhangfuyu.cms.service.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhangfuyu.cms.entity.Cat;
import com.zhangfuyu.cms.service.CatService;
import com.zhangfuyu.cms.service.ChannelService;

public class Channel extends BaseTest{
	
	@Autowired
	ChannelService channelSer;
	@Autowired
	CatService catSer;
	@Test
	public void testChannel(){
		List<com.zhangfuyu.cms.entity.Channel> allChls = channelSer.getAllChnls();
		allChls.forEach(x->{
			System.out.println("x is" + x);
		});
	}
	@Test
	public void testCat(){
		List<Cat> listByChnId = catSer.getListByChnlId(1);
		listByChnId.forEach(x->{
			System.out.println("cat id" + listByChnId);
		});
	}
	

}
