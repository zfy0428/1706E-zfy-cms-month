package com.zhangfuyu.cms.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.zhangfuyu.cms.comon.ArticleType;
import com.zhangfuyu.cms.entity.Article;
import com.zhangfuyu.cms.service.ArticleService;

public class TestArticle extends BaseTest{

	@Autowired
	ArticleService ser;
	@Test
	public void testList(){
		int chnId=1;
		PageInfo<Article> list = ser.list(2, 0, 1);
		if(list!=null && list.getList()!=null){
			list.getList().forEach(article->{
				System.out.println("article id" + article);
			});
		}
	}

	@Test
	public void testAddAarticle() {
		
		Article article1 = new Article();
		article1.setArticleType(ArticleType.HTML);
		article1.setTitle("测试html 文章");
		ser.add(article1);
		
		
		Article article2 = new Article();
		article2.setArticleType(ArticleType.IMAGE);
		article2.setTitle("测试html 文章");
		ser.add(article2);
		
	} 
	
}
