package com.youlanw.cms.repository.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.youlanw.cms.entity.mysql.ActSchoolInfo;

/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月17日
 */
@Component
public interface ActSchoolInfoMapper{

	void insert(ActSchoolInfo entity);
	
	void update(ActSchoolInfo entity);
	
	void delete(java.lang.Integer id);
	
	ActSchoolInfo getById(java.lang.Integer id);

	int count();
	
	List<ActSchoolInfo> findPage(Map<String, Object> param);

}
