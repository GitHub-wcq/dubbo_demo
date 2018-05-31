package com.youlanw.app.repository.mysql;

/**
 * @description: 学生投票mapper
 * @method:
 * @author: Mark
 * @date: 18:19 2018/5/9
 */

import com.youlanw.app.entity.mysql.ActStudentVoteInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ActStudentVoteInfoDao {

	void insert(ActStudentVoteInfo entity);
	
	void update(ActStudentVoteInfo entity);
	
	void delete(Integer id);
	
	ActStudentVoteInfo getById(Integer id);

	int count();

	ActStudentVoteInfo select();

	List<ActStudentVoteInfo> findPage(Map<String, Object> param);

	Long findAllVoteCount();

}
