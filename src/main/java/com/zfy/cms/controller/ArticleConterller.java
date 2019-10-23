package com.zfy.cms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zfy.cms.comon.ConstClass;
import com.zfy.cms.entity.Article;
import com.zfy.cms.entity.Cat;
import com.zfy.cms.entity.Channel;
import com.zfy.cms.entity.User;
import com.zfy.cms.service.ArticleService;
import com.zfy.cms.service.CatService;
import com.zfy.cms.service.ChannelService;

@Controller
@RequestMapping("article")
public class ArticleConterller {
	@Autowired
	ArticleService artSer;
	
	@Autowired
	ChannelService chSer;
	
	@Autowired
	CatService catSer;
	// 显示一篇具体的文章  srticleId 文章的ID
	@RequestMapping("show")
	public String show(HttpServletRequest request,Integer id) {
//		根据srticleId查询文章
		Article article  = artSer.findById(id);
		
		request.setAttribute("article", article);
		return "article/detail";
	}
	
	//跳转添加页面
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(HttpServletRequest request) {
		//查询所有的频道
		List<Channel> allChnls = chSer.getAllChnls();
		request.setAttribute("channels", allChnls);
		return "article/publish";
	}
	
	//添加文章 
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(HttpServletRequest request,Article artilce,MultipartFile file) throws IllegalStateException, IOException {
		//调用 处理上传文件 的方法
		procesFile(artilce, file);
		//获取发布文章的作者
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		artilce.setUserId(loginUser.getId());
		System.out.println("artilce is"+artilce+"loginUser  is"+loginUser);
		//添加文章
		artSer.add(artilce);
		return "article/publish";
	}
		
		//跳转修改页面 根据文章id修改文章
		@RequestMapping(value="update",method=RequestMethod.GET)
		public String update(HttpServletRequest request,Integer id) {
			//查询所有的频道
			List<Channel> allChnls = chSer.getAllChnls();
			//获取文章信息
			Article article = artSer.findById(id);
			request.setAttribute("article", article);
			request.setAttribute("channels", allChnls);
			return "my/update";
		}
		//处理 文章的 图片上传
		public void procesFile(Article artilce,MultipartFile file) throws IllegalStateException, IOException{
//			处理文章不带有图片的
			if(file.isEmpty() || "".equals(file.getOriginalFilename())||file.getOriginalFilename().lastIndexOf(".")<0){
				//如果没有图片 则以空值进行处理
				artilce.setPicture("");
				return;
			}
			//原文件的名称
			String originalName = file.getOriginalFilename();
			// 原文件的扩张名 找到.的位置
			String suffixName = originalName.substring(originalName.lastIndexOf('.'));
			//根据日期分成不同的级别
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//得到一个新的路径
			String path ="d:/pic/"+sdf.format(new Date());
			File pathFile = new File(path);
			//判断路径是否存在
			if(!pathFile.exists()){
				//如果文件不存在 创建一个新的路径
				pathFile.mkdir();
			}
			//定义destFileName  UUID去生成文件名  + 原文件的扩张名 放到path（d:/pic/）路径下
			String destFileName = path + UUID.randomUUID().toString() + suffixName;
			//把原文件保存到新文件的地址   destFileName文件存储的绝对路径
			File distFile =  new File(destFileName);
			//文件另存到这个目录下面  d:/pic/
			file.transferTo(distFile);
			//将图片位置存下来 使用字符分割得到图片的地址
			artilce.setPicture(destFileName.substring(7));
		}
		
		//修改文章
		@ResponseBody
		@RequestMapping(value="update",method=RequestMethod.POST)
		public boolean update(HttpServletRequest request,Article artilce,MultipartFile file) throws IllegalStateException, IOException {
			//调用 处理上传文件 的方法
			procesFile(artilce, file);
			//获取发布文章的作者
			User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
			artilce.setUserId(loginUser.getId());
			//修改文章
			int result = artSer.update(artilce);
			return result>0;
		}
	//根据频道获取相应的分类 用户发布文章或修改文章的下拉框  chnlId是频道ID
	@RequestMapping(value="listCatByChnl" ,method=RequestMethod.GET)
	@ResponseBody
	public List<Cat> getCatByChnl(int chnlId){
		List<Cat> chnlList = catSer.getListByChnlId(chnlId);
		return chnlList;
	}
	
	 
}
