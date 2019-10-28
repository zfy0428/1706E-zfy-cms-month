package com.zfy.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.util.HSSFColor.TEAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zfy.cms.entity.Article;
import com.zfy.cms.entity.Cat;
import com.zfy.cms.entity.Channel;
import com.zfy.cms.entity.Link;
import com.zfy.cms.entity.Special;
import com.zfy.cms.service.ArticleService;
import com.zfy.cms.service.CatService;
import com.zfy.cms.service.ChannelService;
import com.zfy.cms.service.LinkService;
import com.zfy.cms.service.SpecialService;
import com.zfy.cms.utils.PageUtils;

/*
 * 首頁
 */
@Controller
public class IndexController {
	
	@Autowired
	ChannelService channelSer;
	
	@Autowired
	LinkService linkSer;
	
	@Autowired
	CatService catSer;
	@Autowired
	ArticleService articleSer;
	@Autowired
	SpecialService specialService;
	//获取所有的栏目
	@RequestMapping({"index","/"})
	public String index(HttpServletRequest request,
		    @RequestParam(defaultValue="0")Integer chnId,
			@RequestParam(defaultValue="0")Integer catId,
			@RequestParam(defaultValue="1")Integer page
			) throws InterruptedException{
		//添加匿名内部類
		Thread t1 = new Thread() {
			
			public void run() {
//				//获取所有的频道
				List<Channel> channels = channelSer.getAllChnls();
				request.setAttribute("chnls", channels);
			}; 
		};
		
		//获取友情链接
		List<Link> linkList = linkSer.linkList();
		request.setAttribute("linkList", linkList);
		Thread t2;
		if(chnId!=0) {
			//获取该栏目下的所有分类
			t2=new Thread() {
				public void run() {
					List<Cat> catygories = catSer.getListByChnlId(chnId); 
					request.setAttribute("catygories", catygories);
					//获取该栏目下的文章
					PageInfo<Article>  articleList = articleSer.list(chnId,catId,page);
					request.setAttribute("articles", articleList);
					PageUtils.page(request, "/index?chnId="+chnId+"&catId=" + catId, 10, articleList.getList(),
							(long)articleList.getTotal(), articleList.getPageNum());
					//request.setAttribute("pageStr", pageStr);
				}
			};
		}else {
			// 首页热门
			// 获取热门文章
			t2=new Thread() {
				public void run() {
					PageInfo<Article>  articleList = articleSer.hostList(page);
					request.setAttribute("articles", articleList);
					PageUtils.page(request, "/index", 10, articleList.getList(),
							(long)articleList.getTotal(), articleList.getPageNum());
				}
			};
			
		}
		//获取最新文章
		Thread t3 = new Thread(){
			public void run() {
				List<Article> lastList = articleSer.last(5);
				 request.setAttribute("lastList",lastList);
			}
		};
		
		List<Special> specials = new ArrayList<Special>();
		//获取专辑文章
		List<Special> list = specialService.list();
		for (Special special : list) {
			//根据专辑id获取专辑列表
			Special newSpecial = specialService.findById(special.getId());
			specials.add(newSpecial);
		}
		
		//线程启动
		t1.start();
		t2.start();
		t3.start();
		//使线程堵塞  在子线程执行完毕 之后执行主线程 
		t1.join();
		t2.join();
		t3.join();
		
		
		
		
		request.setAttribute("specials", specials);
		request.setAttribute("chnId",chnId);
		request.setAttribute("catId",catId);
		return "index";
	}

}
