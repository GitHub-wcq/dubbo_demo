package com.youlanw.app.repository.mysql;

/**
 * @description: 课程视频mapper
 * @method:
 * @author: Mark
 * @date: 18:22 2018/5/9
 */

import com.youlanw.app.entity.mysql.ActCourseInfo;
import com.youlanw.app.entity.mysql.dto.CourseVoteInfoVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ActCourseInfoDao {

	void insert(ActCourseInfo entity);
	
	void update(ActCourseInfo entity);
	
	void delete(Integer id);
	
	ActCourseInfo getById(Integer id);

	int count();

	ActCourseInfo select(Map<String, Object> param);

	List<ActCourseInfo> findPage(Map<String, Object> param);

	List<CourseVoteInfoVo> getHomeCourses(Map<String, Object> param);

}
