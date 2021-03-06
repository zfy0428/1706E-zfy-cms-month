package com.zhangfuyu.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.jsp.tagext.TagAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangfuyu.cms.dao.ArticleMapper;
import com.zhangfuyu.cms.entity.Article;
import com.zhangfuyu.cms.entity.Comment;
import com.zhangfuyu.cms.entity.Term;
import com.zhangfuyu.cms.service.ArticleService;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper mapper;
	//根据文章的分类和频道获取文章 
	@Override
	public PageInfo<Article> list(Integer chnId, Integer catId, Integer page) {
		//设置页码
		PageHelper.startPage(page, 10);
		//查询指定页码 并返回页面信息
		return new PageInfo(mapper.list(chnId,catId));
	}
	@Override
	public PageInfo<Article> hostList(Integer page) {
		// TODO Auto-generated method stub
		//设置页码
		PageHelper.startPage(page, 10);
		//查询指定页码 并返回页面信息
		return new PageInfo(mapper.listHot());
	}
	
	//最新文章
	@Override
	public List<Article> last(int sum) {
		return mapper.lastList(sum);
	}
	// 显示一篇具体的文章  srticleId 文章的ID
	//根据文章的主键 获取文章的内容
	@Override
	public Article findById(Integer id) {
		// TODO Auto-generated method stub
		return mapper.findById(id);
	}
	@Override
	public int add(Article artilce) {
		// TODO Auto-generated method stub
		//添加文章
		int result =  mapper.add(artilce);
		//调用处理标签方法
		processTags(artilce);
		
		 return result;
	}
	//根据用户id去查找文章列表  id用户id 
	@Override
	public PageInfo<Article> listArticleByUserId(Integer userId, Integer page) {
		PageHelper.startPage(page, 10);
		// TODO Auto-generated method stub
		return new PageInfo<Article>(mapper.listByUserId(userId));
	}
	//根据id删除自己的文章
	@Override
	public int remove(Integer id) {
		
		
		int result = mapper.deleteById(id);
		//删除中间表
		mapper.delTagsByArticleId(id);
		return result;
	}
	//处理tags标签的方法
	private void processTags(Article artilce){
		if(artilce.getTags()==null){
			return;
		}
		 //添加文章中的标签 使用字符串分割
		 String[] tags = artilce.getTags().split(",");
		 for (String tag : tags) {
			 //判断这个tag在数据库中是否存在			
			 Term tagBean = mapper.findTagByName(tag);
			 //如果不存在 则插入新的标签
			 if(tagBean==null){
				 tagBean = new Term(tag, tag);
				 mapper.addTag(tagBean);
				 System.out.println("添加");
			 }
			 //插入中间表
			 try {
				 mapper.addArticleTag(artilce.getId(),tagBean.getId());
			} catch (Exception e) {
				System.out.println("抱歉,你的标签有冲突");
			}
		 }
	}
	
	//修改文章
	@Override
	public int update(Article artilce) {
		//修改文章
		int result = mapper.update(artilce);
		// 删除中间表
		mapper.delTagsByArticleId(artilce.getId());
		//调用处理标签方法
		processTags(artilce);
		
		 return result;
	}
	//获取需要管理的文章
	@Override
	public PageInfo<Article> getAdminArticles(Integer page,Integer status) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, 10);
		return new PageInfo<Article>(mapper.listAdmin(status));
	}
	/**
	 * 审核文章
	 * @param articleId 文章ID
	 * @param status 审核后的状态 
	 * @return
	 */
	@Override
	public int updateStatus(Integer articleId, int status) {
		// TODO Auto-generated method stub
		return mapper.updateStatus(articleId,status);
	}
	
	//修改热门的状态
	@Override
	public int updateHot(Integer articleId, int status) {
		// TODO Auto-generated method stub
		return mapper.updateHot(articleId,status);
	}
	//根据文章id和用户id进行发布评论
	@Override
	public void comment(Integer userId, int articleId, String content) {
		// TODO Auto-generated method stub
		Comment comment = new Comment(articleId,userId,content);
		mapper.addComment(comment);
		mapper.increaseCommentCnt(articleId);
		
	}
	
	@Override
	public void comment(Integer id, Integer articleId, String content, Date date) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				//创建一个评论对象
				Comment comment = new Comment(articleId,id,content);
				comment.setCreated(date);
				//增加评论
				mapper.addComment1(comment);
				//评论数量自加
				mapper.increaseCommentCnt(articleId);
				
	}
	//获取评论
	@Override
		public List<Comment> getCommentByArticleId(Integer articleId) {
		return mapper.getCommentByArticleId(articleId);
	}
//	增加文章点击次数
	@Override
	public int addHits(Integer id) {
		// TODO Auto-generated method stub
		return mapper.increaseHits(id);
	}
	@Override
	public int addTag(String tag) {
		Term tagBean = new Term(tag);
		return mapper.addTag(tagBean);
	}

}
