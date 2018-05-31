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
import com.youlanw.cms.entity.mysql.ActCourseInfo;
import com.youlanw.cms.entity.mysql.ActTeacherInfo;
import com.youlanw.cms.entity.vo.ActCourseInfoVo;
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
public class ActCourseInfoService {

	@Autowired
	private ActCourseInfoMapper actCourseInfoMapper;
	@Autowired
	private ActTeacherInfoMapper actTeacherInfoMapper;

	public PageList<ActCourseInfoVo> findList(int pageNumber, int pageSize, Integer teacherInfoId, String teacherName,
			Integer courseNumber) {
		PageBounds pageBound = new PageBounds(pageNumber, pageSize);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if (teacherInfoId != null) {
			paramsMap.put("teacherInfoId", teacherInfoId);
		}
		if (teacherName != null) {
			paramsMap.put("teacherName", teacherName);
		}
		if (courseNumber != null) {
			paramsMap.put("courseNumber", courseNumber);
		}
		return actCourseInfoMapper.findList(paramsMap, pageBound);
	}

	public Integer addOrEdit(Integer id, String name, String type, String videoUrl, String videoImg,
			Integer teacherInfoId) {
		ActCourseInfo aci = new ActCourseInfo();
		RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
		if (teacherInfoId == null) {
			new RuntimeException("teacherInfoId 为空！");
		}
		if (id == null) {// 新增
			aci.setName(name);
			aci.setType(type.trim());
			aci.setVideoUrl(videoUrl);
			aci.setVideoImg(videoImg);
			aci.setTeacherInfoId(teacherInfoId);
			aci.setNumber(Integer.parseInt(RandomSeriNoUtils.generateNumString(6)));
			aci.setCreateTime(DateConvertUtils.getNow());
			aci.setUpdateTime(DateConvertUtils.getNow());
			aci.setStatus(1);
			actCourseInfoMapper.insert(aci);
			// 存入redis
			Map<String, String> courseInfoMap = new HashMap<String, String>();
			ActTeacherInfo ati = actTeacherInfoMapper.getById(teacherInfoId);
			courseInfoMap.put("id", aci.getId() + "");
			courseInfoMap.put("name", name);
			courseInfoMap.put("type", type);
			courseInfoMap.put("videoUrl", videoUrl);
			courseInfoMap.put("videoImg", videoImg);
			courseInfoMap.put("number", aci.getNumber() + "");
			courseInfoMap.put("teacherInfoId", teacherInfoId + "");
			courseInfoMap.put("status", 1 + "");
			courseInfoMap.put("updateTime", aci.getUpdateTime().getTime() + "");
			courseInfoMap.put("createTime", aci.getCreateTime().getTime() + "");
			courseInfoMap.put("teacherName", ati.getName());
			courseInfoMap.put("teacherImg", ati.getImgUrl());
			courseInfoMap.put("schoolName", ati.getSchool());
			courseInfoMap.put("teacherIntroduce", ati.getIntroduce());
			courseInfoMap.put("voteCount", 0+"");
			redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + aci.getId(), courseInfoMap);
			redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE, 0, aci.getId() + "");
			redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME, aci.getUpdateTime().getTime(), aci.getId()+"");
		} else {// 编辑
			aci = actCourseInfoMapper.getById(id);
			aci.setName(name);
			aci.setType(type.trim());
			aci.setVideoUrl(videoUrl);
			aci.setVideoImg(videoImg);
			aci.setTeacherInfoId(teacherInfoId);
			aci.setUpdateTime(DateConvertUtils.getNow());
			actCourseInfoMapper.update(aci);
			// 存入redis
			Map<String, String> courseInfoMap = redis.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + id);
			if (courseInfoMap != null) {
				courseInfoMap.put("id", id + "");
				courseInfoMap.put("name", name);
				courseInfoMap.put("type", type);
				courseInfoMap.put("videoUrl", videoUrl);
				courseInfoMap.put("videoImg", videoImg);
				courseInfoMap.put("teacherInfoId", teacherInfoId + "");
				courseInfoMap.put("updateTime", aci.getUpdateTime().getTime() + "");
				redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + id, courseInfoMap);
				redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME, aci.getUpdateTime().getTime(), id+"");
			}
			String itemGet = redis.ItemGet(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME);
			if (itemGet != null && StringUtils.isNotBlank(itemGet)) {
				redis.delByKey(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME);
			}
		}
		return aci.getId();
	}

	/**
	 * @param id
	 *            课程ID
	 * @param status
	 *            状态1展示2隐藏
	 */
	public void hideOrShow(Integer id, Integer status) {
		ActCourseInfo aci = actCourseInfoMapper.getById(id);
		aci.setStatus(status);
		aci.setUpdateTime(DateConvertUtils.getNow());
		actCourseInfoMapper.update(aci);
		if (status == 2) {// 1展示2隐藏,如果隐藏，直接从redis中删除
			RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
			redis.delByKey(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + id);
			String itemGet = redis.ItemGet(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME);
			if (itemGet != null && StringUtils.isNotBlank(itemGet)) {
				redis.delByKey(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME);
			}
			redis.zrem(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME, id + "");
			redis.zrem(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE, id + "");
		}else {
			RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
			Map<String, String> courseInfoMap = new HashMap<String, String>();
			ActTeacherInfo ati = actTeacherInfoMapper.getById(aci.getTeacherInfoId());
			Integer voteCount = actCourseInfoMapper.getVoteCountById(id);
			courseInfoMap.put("id", aci.getId() + "");
			courseInfoMap.put("name", aci.getName());
			courseInfoMap.put("type", aci.getType());
			courseInfoMap.put("videoUrl", aci.getVideoUrl());
			courseInfoMap.put("videoImg", aci.getVideoImg());
			courseInfoMap.put("number", aci.getNumber() + "");
			courseInfoMap.put("teacherInfoId", aci.getTeacherInfoId() + "");
			courseInfoMap.put("status", 1 + "");
			courseInfoMap.put("updateTime", aci.getUpdateTime().getTime() + "");
			courseInfoMap.put("createTime", aci.getCreateTime().getTime() + "");
			courseInfoMap.put("teacherName", ati.getName());
			courseInfoMap.put("teacherImg", ati.getImgUrl());
			courseInfoMap.put("schoolName", ati.getSchool());
			courseInfoMap.put("teacherIntroduce", ati.getIntroduce());
			courseInfoMap.put("voteCount", voteCount + "");
			redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + id, courseInfoMap);
			redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME, aci.getUpdateTime().getTime(), aci.getId()+"");
			redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE, voteCount, id + "");
		}
	}

	public ActCourseInfo getById(Integer id) {
		return actCourseInfoMapper.getById(id);
	}

	public List<ActCourseInfo> findAll() {
		return actCourseInfoMapper.findAll();
	}

	public List<Integer> getByTeacherInfoIdAndStatus(Integer teacherInfoId, Integer status) {
		return actCourseInfoMapper.getIdListByTeacherInfoIdAndStatus(teacherInfoId, status);
	}

	public List<ActCourseInfo> getByStatus(Integer status) {
		return actCourseInfoMapper.getByStatus(status);
	}

	public Integer getVoteCountById(Integer id) {
		return actCourseInfoMapper.getVoteCountById(id);
	}
}
