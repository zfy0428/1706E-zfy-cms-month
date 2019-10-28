package com.zfy.cms.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zfy.cms.comon.ArticleType;
import com.zfy.cms.comon.ConstClass;
import com.zfy.cms.comon.ResultMsg;
import com.zfy.cms.entity.Article;
import com.zfy.cms.entity.Cat;
import com.zfy.cms.entity.Channel;
import com.zfy.cms.entity.Comment;
import com.zfy.cms.entity.ImageBean;
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
		//判断文章的类型  如果文章类型为 HTML
		if(article.getArticleType()==ArticleType.HTML){
			return "article/detail";
		}else{
			Gson gson = new Gson();
			//将图片列表转换成文本内容
			article.setImgList(gson.fromJson(article.getContent(), List.class));
			request.setAttribute("article", article);
			return "article/slieimgarticle";
		}
	}
	
	//跳转添加页面
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String add(HttpServletRequest request) {
		//查询所有的频道
		List<Channel> allChnls = chSer.getAllChnls();
		request.setAttribute("channels", allChnls);
		return "article/publish";
	}
	//添加图片
	@RequestMapping(value = "addimg",method=RequestMethod.GET)
	public String addimg(HttpServletRequest request) {
		List<Channel> allChnls = chSer.getAllChnls();
		request.setAttribute("channels", allChnls);
		return "article/publishimg";
	}
		
	//添加图片文章 
	@RequestMapping(value = "addimg",method=RequestMethod.POST)
	public String addimg(HttpServletRequest request,Article article, 
			@RequestParam("file") MultipartFile file,//标题图片
			@RequestParam("imgs") MultipartFile[] imgs,// 文章中图片
			@RequestParam("imgsdesc") String[]  imgsdesc// 文章中图片的描述
			) throws IllegalStateException, IOException {
		
		//固定成只能发布图片文章
		article.setArticleType(ArticleType.IMAGE);
		
		processFile(article,file);
		//将图片描述存入到 imgBeans 中
		List<ImageBean> imgBeans =  new ArrayList<ImageBean>();
		//针对文件进行处理
		for (int i = 0; i < imgs.length; i++) {
			String picUrl = processFile(imgs[i]);//使用processFile放大进行处理每一张图片
			if(!"".equals(picUrl)) {//判断picUrl 如果不等null 则表示图片另存
				//将图片的描述 地址 存入到imageBean中
				ImageBean imageBean = new ImageBean(imgsdesc[i],picUrl);
				//存放的具体图片
				imgBeans.add(imageBean);
			}
		}
		//将图片列表转换成josn的文本内容
		Gson gson = new Gson();
		String json = gson.toJson(imgBeans);// 文章的内容
		article.setContent(json);
		
		
		//获取作者
		User loginUser = (User)request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		article.setUserId(loginUser.getId());
		
		artSer.add(article);
		
		return "article/publish";
		
	}
	//添加文章 
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(HttpServletRequest request,Article artilce,MultipartFile file) throws IllegalStateException, IOException {
		//调用 处理上传文件 的方法
		processFile(artilce, file);
		//获取发布文章的作者
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		artilce.setUserId(loginUser.getId());
		
		artilce.setArticleType(ArticleType.HTML);
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
		//将图片保存到集合中
		private String processFile(MultipartFile file) throws IllegalStateException, IOException {

			// 原来的文件名称
			System.out.println("file.isEmpty() :" + file.isEmpty()  );
			System.out.println("file.name :" + file.getOriginalFilename());
			
			if(file.isEmpty()||"".equals(file.getOriginalFilename()) || file.getOriginalFilename().lastIndexOf('.')<0 ) {
				return "";
			}
				
			String originName = file.getOriginalFilename();
			String suffixName = originName.substring(originName.lastIndexOf('.'));
			//根据日期分成不同的级别
			SimpleDateFormat sdf=  new SimpleDateFormat("yyyyMMdd");
			//得到一个新的路径
			String path = "d:/pic/" + sdf.format(new Date());
			File pathFile = new File(path);
			//判断路径是否存在
			if(!pathFile.exists()) {
				//如果文件不存在 创建一个新的路径
				pathFile.mkdir();
			}
			//定义destFileName  UUID去生成文件名  + 原文件的扩张名 放到path（d:/pic/）路径下
			String destFileName = 		path + "/" +  UUID.randomUUID().toString() + suffixName;
			//把原文件保存到新文件的地址   destFileName文件存储的绝对路径
			File distFile = new File( destFileName);
			file.transferTo(distFile);//文件另存到这个目录下边
			//将图片位置存下来 使用字符分割得到图片的地址
			return destFileName.substring(7);
			
			
		}
		//处理 文章的 图片上传
		public void processFile(Article artilce,MultipartFile file) throws IllegalStateException, IOException{
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
		public boolean update(HttpServletRequest request,Article articleId,MultipartFile file) throws IllegalStateException, IOException {
			//调用 处理上传文件 的方法
			processFile(articleId, file);
			//获取发布文章的作者
			User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
			articleId.setUserId(loginUser.getId());
			//修改文章
			int result = artSer.update(articleId);
			return result>0;
		}
	//根据频道获取相应的分类 用户发布文章或修改文章的下拉框  chnlId是频道ID
	@RequestMapping(value="listCatByChnl" ,method=RequestMethod.GET)
	@ResponseBody
	public List<Cat> getCatByChnl(int chnlId){
		List<Cat> chnlList = catSer.getListByChnlId(chnlId);
		return chnlList;
	}
	//发布评论
	@RequestMapping("comment")
	@ResponseBody
	public ResultMsg comment(HttpServletRequest request,int articleId,String content){
		//判断用户是否已登录
		User loginUser = (User) request.getSession().getAttribute(ConstClass.SESSION_USER_KEY);
		if(loginUser==null){
			return new ResultMsg(2,"用户未登录","");
		}
		//根据文章id和用户id进行发布评论
		artSer.comment(loginUser.getId(),articleId,content);
		return new ResultMsg(1,"发布成功","");
	}
	//查询评论
	@RequestMapping("getclist")
	public String getComment(HttpServletRequest request,int articleId,@RequestParam(defaultValue="1")Integer page){
		PageInfo<Comment> comments = artSer.getCommentByArticleId(articleId,page);
		request.setAttribute("comments",comments);
		return "my/clist";
	}
	
	 
}
