package com.zhangfuyu.cms.entity;

import java.util.Date;
import java.util.List;

//专题
public class Special {

	private Integer id; //主键
	private String  title;//主题标题
	private String  digest;//abstract;  摘要
	private Date created; //创建时间
	
	private Integer articleNum;
	//文章列表
	List<Article> artilceList;
	
	
	public Integer getArticleNum() {
		return articleNum;
	}
	public void setArticleNum(Integer articleNum) {
		this.articleNum = articleNum;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public List<Article> getArtilceList() {
		return artilceList;
	}
	public void setArtilceList(List<Article> artilceList) {
		this.artilceList = artilceList;
	}
	@Override
	public String toString() {
		return "Special [id=" + id + ", title=" + title + ", digest=" + digest + ", created=" + created
				+ ", articleNum=" + articleNum + ", artilceList=" + artilceList + "]";
	}
	
	
	
}
