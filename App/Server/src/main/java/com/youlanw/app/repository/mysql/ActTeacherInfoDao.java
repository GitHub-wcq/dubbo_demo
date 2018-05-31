package com.youlanw.app.repository.mysql;

/**
 * @description: 教师信息mapper
 * @method:
 * @author: Mark
 * @date: 18:19 2018/5/9
 */

import com.youlanw.app.entity.mysql.ActTeacherInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ActTeacherInfoDao {

	void insert(ActTeacherInfo entity);
	
	void update(ActTeacherInfo entity);
	
	void delete(Integer id);
	
	ActTeacherInfo getById(Integer id);

	int count();

	ActTeacherInfo select(Map<String, Object> param);

	List<ActTeacherInfo> findPage(Map<String, Object> param);

	List<ActTeacherInfo> getTeacherBySchool(String school);

	List<String> getAllTeacherName();

	List<String> getAllSchoolName();
}
