package com.youlanw.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.youlanw.cms.entity.mysql.ActTeacherInfo;
import com.youlanw.cms.entity.vo.ActTeacherInfoVo;
import com.youlanw.cms.repository.mysql.ActCourseInfoMapper;
import com.youlanw.cms.repository.mysql.ActTeacherInfoMapper;
import com.youlanw.cms.utils.RedisUtil;
import com.youlanw.common.utils.DateConvertUtils;
import com.youlanw.common.utils.RandomSeriNoUtils;
import com.youlanw.common.utils.redis.RedisPoolUtil;
import com.youlanw.common.utils.redis.key.RedisKeyAppConstants;

/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
@Component
@Transactional
public class ActTeacherInfoService {

	@Autowired
	private ActTeacherInfoMapper actTeacherInfoMapper;
	@Autowired
	private ActCourseInfoMapper actCourseInfoMapper;

	/**
	 * @param pageNumber
	 * @param pageSize
	 * @param name
	 *            老师名字
	 * @return
	 */
	public PageList<ActTeacherInfoVo> findList(int pageNumber, int pageSize, String name) {
		PageBounds pageBound = new PageBounds(pageNumber, pageSize);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if (name != null && name != "") {
			paramsMap.put("name", name);
		}
		return actTeacherInfoMapper.findList(paramsMap, pageBound);
	}

	/**
	 * @param name
	 *            老师名字
	 * @param school
	 *            所属学校
	 * @param specialty
	 *            老师专业
	 * @param introduce
	 *            老师介绍
	 * @param imgUrl
	 *            老师头像
	 * @param id
	 * @param operator
	 * @return
	 */
	public Integer addOrEdit(String name, String school, String specialty, String introduce, String imgUrl, Integer id,
			String operator) {
		ActTeacherInfo ati = new ActTeacherInfo();
		RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
		if (id == null) {// 新增
			ati.setName(name);
			ati.setSchool(school.trim());
			ati.setSpecialty(specialty);
			ati.setIntroduce(introduce);
			ati.setImgUrl(imgUrl);
			ati.setNumber(Integer.parseInt(RandomSeriNoUtils.generateNumString(6)));
			ati.setCreateTime(DateConvertUtils.getNow());
			ati.setUpdateTime(DateConvertUtils.getNow());
			ati.setOperator(operator == null ? "admin" : operator);
			ati.setStatus(1);
			actTeacherInfoMapper.insert(ati);
			// 存入redis
			Map<String, String> teacherInfoMap = new HashMap<String, String>();
			teacherInfoMap.put("id", ati.getId() + "");
			teacherInfoMap.put("name", name);
			teacherInfoMap.put("school", school);
			teacherInfoMap.put("specialty", specialty);
			teacherInfoMap.put("introduce", introduce);
			teacherInfoMap.put("imgUrl", imgUrl);
			teacherInfoMap.put("number", ati.getNumber() + "");
			teacherInfoMap.put("status", 1 + "");
			teacherInfoMap.put("updateTime", ati.getUpdateTime().getTime() + "");
			teacherInfoMap.put("createTime", ati.getCreateTime().getTime() + "");
			teacherInfoMap.put("voteCount", 0 + "");
			redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_TEACHERINFO + ati.getId(), teacherInfoMap);
			redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_TEACHER, 0, ati.getId() + "");
		} else {// 编辑
			ati = actTeacherInfoMapper.getById(id);
			ati.setName(name);
			ati.setSchool(school.trim());
			ati.setSpecialty(specialty);
			ati.setIntroduce(introduce);
			ati.setImgUrl(imgUrl);
			ati.setUpdateTime(DateConvertUtils.getNow());
			ati.setOperator(operator == null ? "admin" : operator);
			actTeacherInfoMapper.update(ati);
			// 存入redis
			Map<String, String> teacherInfoMap = redis.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_TEACHERINFO + id);
			if (teacherInfoMap != null) {
				teacherInfoMap.put("name", name);
				teacherInfoMap.put("school", school);
				teacherInfoMap.put("specialty", specialty);
				teacherInfoMap.put("introduce", introduce);
				teacherInfoMap.put("imgUrl", imgUrl);
				teacherInfoMap.put("updateTime", ati.getUpdateTime().getTime() + "");
				redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_TEACHERINFO + id, teacherInfoMap);
			}
			// 存入redis，修改老师信息时，老师下面所有的课程信息也要跟着更新
			try {
				List<Integer> courseIdList = actCourseInfoMapper.getIdListByTeacherInfoIdAndStatus(id, 1);
				if (courseIdList != null && courseIdList.size() > 0) {
					for (Integer courseInfoId : courseIdList) {
						Map<String, String> courseInfoMap = redis.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + courseInfoId);
						if (courseInfoMap != null) {
							courseInfoMap.put("teacherName", name);
							courseInfoMap.put("teacherImg", imgUrl);
							courseInfoMap.put("schoolName", school);
							courseInfoMap.put("teacherIntroduce", introduce);
							redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + courseInfoId,courseInfoMap);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String itemGet = redis.ItemGet(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME);
			if (itemGet != null && StringUtils.isNotBlank(itemGet)) {
				redis.delByKey(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME);
			}
		}
		return ati.getId();
	}

	public ActTeacherInfo getById(Integer id) {
		return actTeacherInfoMapper.getById(id);
	}

	public List<ActTeacherInfo> findAll() {
		return actTeacherInfoMapper.findAll();
	}

	public Integer getVoteCountById(Integer id) {
		return actTeacherInfoMapper.getVoteCountById(id);
	}

}
