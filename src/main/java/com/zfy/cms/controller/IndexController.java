package com.zfy.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zfy.cms.entity.Article;
import com.zfy.cms.entity.Cat;
import com.zfy.cms.entity.Channel;
import com.zfy.cms.service.ArticleService;
import com.zfy.cms.service.CatService;
import com.zfy.cms.service.ChannelService;
import com.zfy.cms.web.PageUtils;

/*
 * 首頁
 */
@Controller
public class IndexController {
	
	@Autowired
	ChannelService channelSer;
	
	@Autowired
	CatService catSer;
	@Autowired
	ArticleService articleSer;
	//获取所有的栏目
	@RequestMapping({"index","/"})
	public String index(HttpServletRequest request,
		    @RequestParam(defaultValue="0")Integer chnId,
			@RequestParam(defaultValue="0")Integer catId,
			@RequestParam(defaultValue="1")Integer page
			){
		//获取所有的频道
		List<Channel> list = channelSer.getAllChnls();
		if(chnId!=0){
			//获取该栏目下的所有分类
			 List<Cat> catygories = catSer.getListByChnlId(chnId);
			 request.setAttribute("catygories",catygories);
			//根据文章的分类和频道获取文章 
			 PageInfo<Article> articleList = articleSer.list(chnId,catId,page);
			 request.setAttribute("articles",articleList);
			 PageUtils.page(request, "/index?chnId="+chnId+"&catId=" + catId, 10, articleList.getList(),
						(long)articleList.getTotal(), articleList.getPageNum());
		}else{
			//首页热门
			 PageInfo<Article> articleList = articleSer.hostList(page);
			 request.setAttribute("articles",articleList);
			 PageUtils.page(request, "/index", 10, articleList.getList(),
						(long)articleList.getTotal(), articleList.getPageNum());
		}
		//获取最新文章
		List<Article> lastList = articleSer.last(5);
		 request.setAttribute("lastList",lastList);
	
		request.setAttribute("chnls", list);
		request.setAttribute("chnId",chnId);
		request.setAttribute("catId",catId);
		return "index";
	}

}
