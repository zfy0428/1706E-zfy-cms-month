package com.zfy.cms.service;

import java.util.List;

import com.zfy.cms.entity.Cat;

public interface CatService {
	//根据频道去获取下面的分类
	List<Cat> getListByChnlId(Integer id);
}
