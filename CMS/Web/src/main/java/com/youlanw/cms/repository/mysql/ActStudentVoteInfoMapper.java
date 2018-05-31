package com.youlanw.cms.repository.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.youlanw.cms.entity.mysql.ActStudentVoteInfo;

/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
@Component
public interface ActStudentVoteInfoMapper{

	void insert(ActStudentVoteInfo entity);
	
	void update(ActStudentVoteInfo entity);
	
	void delete(java.lang.Integer id);
	
	ActStudentVoteInfo getById(java.lang.Integer id);

	int count();
	
	List<ActStudentVoteInfo> findPage(Map<String, Object> param);

}
