package com.zfy.cms.service;




import java.util.List;

import com.zfy.cms.entity.Article4Vote;
import com.zfy.cms.entity.VoteStatic;

/**
 * 
 * @author Zhang旭涛
 *
 */
public interface Article4VoteService {
	
	int publish(Article4Vote av);
	
	List<Article4Vote>  list();
	//根据id 
	Article4Vote  findById(Integer id);
	//添加
	int vote(Integer articleId,Character option);
	//int vote(Integer userId, Integer articleId,Character option);
	
	List<VoteStatic> getVoteStatics(Integer articleId);
	
	
	

}
