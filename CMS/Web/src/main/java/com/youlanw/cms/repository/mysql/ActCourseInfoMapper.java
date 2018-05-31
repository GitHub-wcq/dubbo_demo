package com.youlanw.cms.repository.mysql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.youlanw.cms.entity.mysql.ActCourseInfo;
import com.youlanw.cms.entity.vo.ActCourseInfoVo;

/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
@Component
public interface ActCourseInfoMapper{

	void insert(ActCourseInfo entity);
	
	void update(ActCourseInfo entity);
	
	void delete(java.lang.Integer id);
	
	ActCourseInfo getById(java.lang.Integer id);

	int count();
	
	List<ActCourseInfo> findPage(Map<String, Object> param);

	PageList<ActCourseInfoVo> findList(Map<String, Object> paramsMap, PageBounds pageBound);

	List<Integer> getIdListByTeacherInfoIdAndStatus(@Param("teacherInfoId")Integer teacherInfoId, @Param("status")Integer status);

	List<ActCourseInfo> findAll();

	List<ActCourseInfo> getByStatus(@Param("status")Integer status);

	Integer getVoteCountById(@Param("id")Integer id);

	List<ActCourseInfo> getByTeacherInfoId(@Param("teacherInfoId")Integer teacherInfoId);

}
