package com.zfy.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.zfy.cms.entity.Cat;
@Mapper
public interface CatMapper {
	@Select("select id,name,channel_id channelId from cms_category where channel_id=#{value}")
	List<Cat> getListByChnId(Integer id);

}
