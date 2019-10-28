package com.zhangfuyu.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangfuyu.cms.dao.ArticleMapper;
import com.zhangfuyu.cms.dao.SpecialMapper;
import com.zhangfuyu.cms.entity.Special;
import com.zhangfuyu.cms.service.SpecialService;
@Service
public class SpecialServiceImpl implements SpecialService{
	@Autowired
	SpecialMapper specialMapper; 
	
	@Autowired
	ArticleMapper  articleMapper; 
	//查询专辑
	@Override
	public List<Special> list() {
		List<Special> list =  specialMapper.list();
		for (Special special : list) {
			special.setArticleNum(articleMapper.getArticleNum(special.getId()));
		}
		return list;
	}
	//添加专辑
	@Override
	public int add(Special special) {
		// TODO Auto-generated method stub
		return specialMapper.add(special);
	}
	@Override
	public Special findById(Integer id) {
		//根据专辑id查找专辑
		Special special = specialMapper.findById(id);
		//根据专辑id查找列表
		special.setArtilceList(articleMapper.findBySepecailId(id));
		return special;
	}
	//向专辑添加文章
	@Override
	public int addArticle(Integer specId, Integer articleId) {
		// TODO Auto-generated method stub
		return specialMapper.addArticle( specId,  articleId);
	}
	//从专辑中移除文章
	@Override
	public int removeArticle(Integer specId, Integer articleId) {
		// TODO Auto-generated method stub
		return specialMapper.removeArticle(specId, articleId);
	}
	//根据专辑id获取专辑内的文章
	@Override
	public List<Special> getlist(Integer aId) {
		// TODO Auto-generated method stub
		return specialMapper.getList(aId);
	}
	//修改专辑
	@Override
	public int update(Special special) {
		// TODO Auto-generated method stub
		return specialMapper.update(special);
	}

}
