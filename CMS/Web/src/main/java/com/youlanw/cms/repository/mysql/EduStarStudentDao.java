package com.youlanw.cms.repository.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.youlanw.cms.entity.mysql.EduStarStudent;


@Component
public interface EduStarStudentDao{

	void insert(EduStarStudent entity);
	
	void update(EduStarStudent entity);
	
	void delete(java.lang.Integer id);
	
	EduStarStudent getById(java.lang.Integer id);

	int count();
	
	List<EduStarStudent> findPage(Map<String, Object> param);

}
