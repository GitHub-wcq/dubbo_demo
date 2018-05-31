package com.youlanw.app.repository.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.youlanw.app.entity.mysql.User;


@Component
public interface UserMapper{

	void insert(User entity);
	
	void update(User entity);
	
	void delete(java.lang.Integer id);
	
	User getById(java.lang.Integer id);

	int count();
	
	List<User> findPage(Map<String, Object> param);

}
