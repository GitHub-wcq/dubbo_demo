package com.youlanw.cms.repository.mysql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.youlanw.cms.entity.mysql.ActTeacherInfo;
import com.youlanw.cms.entity.vo.ActTeacherInfoVo;

/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
@Component
public interface ActTeacherInfoMapper{

	void insert(ActTeacherInfo entity);
	
	void update(ActTeacherInfo entity);
	
	void delete(java.lang.Integer id);
	
	ActTeacherInfo getById(java.lang.Integer id);

	int count();
	
	List<ActTeacherInfo> findPage(Map<String, Object> param);

	PageList<ActTeacherInfoVo> findList(Map<String, Object> paramsMap, PageBounds pageBound);

	List<ActTeacherInfo> findAll();

	Integer getVoteCountById(@Param("id")Integer id);

}
