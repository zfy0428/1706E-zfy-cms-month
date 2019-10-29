package com.zhangfuyu.cms.service.test;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhangfuyu.cms.entity.Article;
import com.zhangfuyu.cms.service.ArticleService;

/**
 * 测试事务
 * @author zhuzg
 *
 */
public class TestTranscation extends BaseTest {
	
	@Autowired
	ArticleService articleService;

	/**
	 * 
	 */
	@Test
	public void testAddArticle() {
		Article article = new Article();
		article.setContent("测试内容22");
		article.setTags(" zhSANG1222,LISI22");
		articleService.add(article);
		
		
	}
}
