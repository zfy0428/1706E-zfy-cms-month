package com.zfy.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zfy.cms.entity.Article;

public interface ArticleService {
	//根据文章的分类和频道获取文章 
	PageInfo<Article> list(Integer chnId, Integer catId, Integer page);
	//热门文章
	PageInfo<Article> hostList(Integer page);
	//获取最新文章    sum是指获取的条数
	List<Article> last(int sum);
	// 显示一篇具体的文章  srticleId 文章的ID
	//根据文章的主键 获取文章的内容
	Article findById(Integer srticleId);
	//添加文章
	int add(Article artilce);
	//根据用户id去查找文章列表  id用户id 
	PageInfo<Article> listArticleByUserId(Integer id, Integer page);
	//根据ID删除用户自己文章
	int remove(Integer id);
	//修改文章
	int update(Article artilce);
	//获取需要管理的文章
	PageInfo<Article> getAdminArticles(Integer page, Integer status);
	/**
	 * 审核文章
	 * @param articleId 文章ID
	 * @param status 审核后的状态 
	 * @return
	 */
	int updateStatus(Integer articleId, int status);
	//修改热门
	int updateHot(Integer articleId, int status);

}
