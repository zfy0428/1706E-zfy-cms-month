package com.zfy.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zfy.cms.entity.Special;

public interface SpecialMapper {
//获取专辑列表
	@Select("SELECT id,title,abstract as digest,created FROM cms_special ORDER BY id desc")
	List<Special> list();
	//添加专辑
	@Insert("INSERT INTO cms_special (title,abstract,created) VALUES(#{title},#{digest},now() ) ")
	int add(Special special);
	//根据专辑id查找专辑
	@Select("SELECT id,title,abstract as digest,created FROM cms_special WHERE id=#{value} ")
	Special findById(Integer id);
	//向专辑添加文章
	@Insert("INSERT INTO cms_special_article(sid,aid) VALUES(#{sid},#{aid})")
	int addArticle(@Param("sid") Integer specId, @Param("aid") Integer articleId);
	//从专辑及移除文章
	@Delete("DELETE FROM cms_special_article WHERE sid=#{sid} AND aid=#{aid}")
	int removeArticle(@Param("sid") Integer specId, 
			@Param("aid")  Integer articleId);
	
	
	@Select("SELECT id,title,abstract as digest,created FROM cms_special WHERE id=#{value} ")
	List<Special> getList(Integer aId);
	
	
	//修改专辑
	@Update("UPDATE cms_special SET title=#{title},abstract=#{digest} "
			+ " WHERE id=#{id} ")
	int update(Special special);

}
