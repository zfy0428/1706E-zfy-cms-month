package com.zfy.cms.service;

import java.util.List;

import com.zfy.cms.entity.Special;

public interface SpecialService {
	//獲取所有的专辑
	List<Special> list();
	//添加专辑
	int add(Special special);
	//根据id查询special本身以及里面的所有文章
	Special findById(Integer id);
	//向专辑添加文章
	int addArticle(Integer specId, Integer articleId);
	//从专辑中移除文章
	int removeArticle(Integer specId, Integer articleId);
	//根据专辑id获取专辑的文章
	List<Special> getlist(Integer aId);
	//修改专辑
	int update(Special special);

}
