package com.zhangfuyu.cms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zhangfuyu.cms.comon.ArticleType;

/**
 * @author zhangfuyu
 * @time 2019年10月17日
 */
public class Article implements Serializable{

	//版本号id
		private static final long serialVersionUID = 178742213295392002L;
		
		//文章主键id
		private Integer id;
		//标题
		private String title;
		
		//文章内容
		private String content;
		//图片
		private String picture;
		//频道id
		private Integer channelId;
		private Channel channel;
		//分类id
		private Integer categoryId;
		private Cat cat;
		
		private ArticleType ArticleType;
		
		//是否已经逻辑删除
		private Integer deleted;
		//发表时间
		private Date created;
		//修改时间
		private Date updated;
		//评论数量
		private Integer commentCnt;
		
		// 标签 用逗号分隔
		private String tags;
		
		//用户id
		private Integer userId;
		//点击量
		private Integer hits;
		//是否热门
		private Integer hot;
		//状态
		private Integer status;
		
		
		private Integer locked;
		
		private List<ImageBean> imgList;
		
		
		public List<ImageBean> getImgList() {
			return imgList;
		}
		public void setImgList(List<ImageBean> imgList) {
			this.imgList = imgList;
		}
		private ArticleType articleType=ArticleType.HTML;
		
		
		
		public ArticleType getArticleType() {
			return ArticleType;
		}
		public void setArticleType(ArticleType articleType) {
			ArticleType = articleType;
		}
		public Channel getChannel() {
			return channel;
		}
		public void setChannel(Channel channel) {
			this.channel = channel;
		}
		public Cat getCat() {
			return cat;
		}
		public void setCat(Cat cat) {
			this.cat = cat;
		}
		
		public Integer getLocked() {
			return locked;
		}
		public void setLocked(Integer locked) {
			this.locked = locked;
		}
	
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		private User user;
		
		
		public String getTags() {
			return tags;
		}
		public void setTags(String tags) {
			this.tags = tags;
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
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getPicture() {
			return picture;
		}
		public void setPicture(String picture) {
			this.picture = picture;
		}
		public Integer getChannelId() {
			return channelId;
		}
		public void setChannelId(Integer channelId) {
			this.channelId = channelId;
		}
		public Integer getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public Integer getHits() {
			return hits;
		}
		public void setHits(Integer hits) {
			this.hits = hits;
		}
		public Integer getHot() {
			return hot;
		}
		public void setHot(Integer hot) {
			this.hot = hot;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getDeleted() {
			return deleted;
		}
		public void setDeleted(Integer deleted) {
			this.deleted = deleted;
		}
		public Date getCreated() {
			return created;
		}
		public void setCreated(Date created) {
			this.created = created;
		}
		public Date getUpdated() {
			return updated;
		}
		public void setUpdated(Date updated) {
			this.updated = updated;
		}
		public Integer getCommentCnt() {
			return commentCnt;
		}
		public void setCommentCnt(Integer commentCnt) {
			this.commentCnt = commentCnt;
		}
		
		
		@Override
		public String toString() {
			return "Article [id=" + id + ", title=" + title + ", content="
					+ content + ", picture=" + picture + ", channelId="
					+ channelId + ", channel=" + channel + ", categoryId="
					+ categoryId + ", cat=" + cat + ", ArticleType="
					+ ArticleType + ", deleted=" + deleted + ", created="
					+ created + ", updated=" + updated + ", commentCnt="
					+ commentCnt + ", tags=" + tags + ", userId=" + userId
					+ ", hits=" + hits + ", hot=" + hot + ", status=" + status
					+ ", locked=" + locked + ", imgList=" + imgList
					+ ", articleType=" + articleType + ", user=" + user + "]";
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Article other = (Article) obj;
			if (categoryId == null) {
				if (other.categoryId != null)
					return false;
			} else if (!categoryId.equals(other.categoryId))
				return false;
			if (channelId == null) {
				if (other.channelId != null)
					return false;
			} else if (!channelId.equals(other.channelId))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (userId == null) {
				if (other.userId != null)
					return false;
			} else if (!userId.equals(other.userId))
				return false;
			return true;
		}
		
		
}

