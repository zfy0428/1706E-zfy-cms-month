package com.zfy.cms.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.zfy.cms.entity.Article;
import com.zfy.cms.service.ArticleService;

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
}
