package com.youlanw.app.repository.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.youlanw.app.entity.mysql.UserIntegralRule;

@Component
public interface UserIntegralRuleMapper{

	void insert(UserIntegralRule entity);
	
	void update(UserIntegralRule entity);
	
	void delete(java.lang.Integer id);
	
	UserIntegralRule getById(java.lang.Integer id);

	int count();
	
	List<UserIntegralRule> findPage(Map<String, Object> param);
	
	String getCodeByBehavior(String behavior);

}
