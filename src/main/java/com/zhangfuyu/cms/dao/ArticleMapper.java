package com.zhangfuyu.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhangfuyu.cms.entity.Article;
import com.zhangfuyu.cms.entity.Comment;
import com.zhangfuyu.cms.entity.Term;

//文章管理
public interface ArticleMapper {
	// 根据文章的分类和频道获取文章
	List<Article> list(@Param("chnId") Integer chnId,
			@Param("catId") Integer catId);

	// 获取热门文章
	List<Article> listHot();

	// 获取最新文章 sum是指获取条数
	List<Article> lastList(int sum);

	// 显示一篇具体的文章 srticleId 文章的ID
	// 根据文章的主键 获取文章的内容
	Article findById(Integer id);

	// 添加文章
	int add(Article artilce);

	// 根据用户id去查找文章列表 id用户id
	List<Article> listByUserId(Integer userId);

	// 根据文章的ID删除文章
	@Update("UPDATE cms_article set deleted=1 where id=#{value}")
	int deleteById(Integer id);

	// 修改文章
	@Update("UPDATE cms_article set title=#{title},content=#{content},picture=#{picture},channel_id=#{channelId},"
			+ "category_id=#{categoryId},updated=now() WHERE id=#{id}")
	int update(Article artilce);

	// 获取需要管理的文章
	List<Article> listAdmin(@Param("status") Integer status);

	// 修改文章的状态
	@Update("UPDATE cms_article set status=#{status},updated=now() WHERE id=#{articleId}")
	int updateStatus(@Param("articleId") Integer articleId,
			@Param("status") int status);

	// 修改热门的状态
	@Update("UPDATE cms_article set hot=#{status},updated=now() WHERE id=#{articleId}")
	int updateHot(@Param("articleId") Integer articleId,
			@Param("status") int status);

	// 根据标签名称获取标签对象 limit 1 是防止数据库中返回多个同名的标签 返回成数组
	@Select("SELECT * FROM cms_term where display_name=#{value} limit 1")
	Term findTagByName(String tag);

	// 添加tag实体标签
	int addTag(Term tagBean);

	// 添加数据到文章标签的中间表
	@Insert("INSERT INTO cms_article_term_middle values(#{articleId},#{tagId}) ")
	void addArticleTag(@Param("articleId") Integer articleId,
			@Param("tagId") Integer tagId);

	// 根据文章id删除中间表
	@Delete("delete from cms_article_term_middle where aid=#{value}")
	int delTagsByArticleId(Integer articleId);

	// 根据文章id和用户id进行发布评论
	@Insert("insert into cms_comment (userId,articleId,content,created)"
			+ "values (#{userId},#{articleId},#{content},now())")
	void addComment(Comment comment);

	@Update("update cms_article set commentCnt=commentCnt+1 where id=#{value} order by id desc")
	void increaseCommentCnt(Integer articleId);
	//增加评论	
	@Insert("INSERT INTO cms_comment(userId,articleId,content,created) "
			+ "VALUES(#{userId},#{articleId},#{content},#{created} )")
	void addComment1(Comment comment);
	
	// 根据articleId查询评论
	@Select("select c.*,u.username as userName from cms_comment c left join cms_user u on u.id=c.userId"
			+ " where c.articleId=#{value}")
	List<Comment> getCommentByArticleId(Integer articleId);

	// 根据专辑id获取文章列表
	@Select("SELECT a.id,a.title,a.created FROM cms_special_article  "
			+ " sa JOIN cms_article  a ON sa.aid=a.id "
			+ " WHERE sa.sid=#{value}")
	List<Article> findBySepecailId(Integer id);

	
	// 获取文章的数量
	@Select("SELECT count(1) FROM cms_special_article  "
			+ " sa JOIN cms_article  a ON sa.aid=a.id "
			+ " WHERE sa.sid=#{value}")
	Integer getArticleNum(Integer id);
//	增加文章点击次数
	@Update("UPDATE cms_article SET hits = hits + 1 WHERE id =  #{id} ")
	int increaseHits(Integer id);

}
