package com.zhangfuyu.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangfuyu.cms.dao.CatMapper;
import com.zhangfuyu.cms.entity.Cat;
import com.zhangfuyu.cms.service.CatService;

@Service
public class CatServiceImpl implements CatService {
	
	@Autowired
	CatMapper mapper;
	//根据频道去获取下面的分类
	@Override
	public List<Cat> getListByChnlId(Integer id) {
		// TODO Auto-generated method stub
		return mapper.getListByChnId(id);
	}

}
