package com.youlanw.cms.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlanw.cms.entity.mysql.ActCourseInfo;
import com.youlanw.cms.entity.mysql.ActTeacherInfo;
import com.youlanw.cms.service.ActCourseInfoService;
import com.youlanw.cms.service.ActTeacherInfoService;
import com.youlanw.cms.utils.Constants;
import com.youlanw.cms.utils.RedisUtil;
import com.youlanw.common.utils.redis.RedisPoolUtil;
import com.youlanw.common.utils.redis.key.RedisKeyAppConstants;

/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
@Controller
@RequestMapping("api/updateRedis")
public class UpdateRedisController {
	private Logger logger = LoggerFactory.getLogger(UpdateRedisController.class);
	@Autowired
	private ActCourseInfoService actCourseInfoService;
	@Autowired
	private ActTeacherInfoService actTeacherInfoService;

	/**
	 * 根据课程id更新redis数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "courseInfo/updateById")
	public String updateCourseInfoById(@RequestParam(value = "id", required = true) Integer id) {
		String message = null;
		try {
			ActCourseInfo aci = actCourseInfoService.getById(id);
			RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
			Map<String, String> courseInfoMap = new HashMap<String, String>();
			ActTeacherInfo ati = actTeacherInfoService.getById(aci.getTeacherInfoId());
			Integer voteCount = actCourseInfoService.getVoteCountById(id);
			courseInfoMap.put("id", id + "");
			courseInfoMap.put("name", aci.getName());
			courseInfoMap.put("type", aci.getType());
			courseInfoMap.put("videoUrl", aci.getVideoUrl());
			courseInfoMap.put("videoImg", aci.getVideoImg());
			courseInfoMap.put("number", aci.getNumber() + "");
			courseInfoMap.put("teacherInfoId", aci.getTeacherInfoId() + "");
			courseInfoMap.put("status", aci.getStatus() + "");
			courseInfoMap.put("updateTime", aci.getUpdateTime().getTime() + "");
			courseInfoMap.put("createTime", aci.getCreateTime().getTime() + "");
			courseInfoMap.put("teacherName", ati.getName());
			courseInfoMap.put("teacherImg", ati.getImgUrl());
			courseInfoMap.put("schoolName", ati.getSchool());
			courseInfoMap.put("voteCount", voteCount + "");
			courseInfoMap.put("teacherIntroduce", ati.getIntroduce());
			redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + id, courseInfoMap);
			redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE, voteCount, aci.getId() + "");
			redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME, aci.getUpdateTime().getTime(), aci.getId()+"");
			message = Constants.RETURN_STATUS_SUCCESS;
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			message = Constants.RETURN_STATUS_ERROR;
		}
		return message;
	}

	/**
	 * 更新redis所有课程数据
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "courseInfo/updateAll")
	public String updateAllCourseInfo() {
		String message = null;
		try {
			List<ActCourseInfo> aciList = actCourseInfoService.getByStatus(1);
			if (aciList != null && aciList.size() > 0) {
				for (ActCourseInfo aci : aciList) {
					RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
					Map<String, String> courseInfoMap = new HashMap<String, String>();
					ActTeacherInfo ati = actTeacherInfoService.getById(aci.getTeacherInfoId());
					Integer voteCount = actCourseInfoService.getVoteCountById(aci.getId());
					courseInfoMap.put("id", aci.getId() + "");
					courseInfoMap.put("name", aci.getName());
					courseInfoMap.put("type", aci.getType());
					courseInfoMap.put("videoUrl", aci.getVideoUrl());
					courseInfoMap.put("videoImg", aci.getVideoImg());
					courseInfoMap.put("number", aci.getNumber() + "");
					courseInfoMap.put("teacherInfoId", aci.getTeacherInfoId() + "");
					courseInfoMap.put("status", aci.getStatus() + "");
					courseInfoMap.put("updateTime", aci.getUpdateTime().getTime() + "");
					courseInfoMap.put("createTime", aci.getCreateTime().getTime() + "");
					courseInfoMap.put("teacherName", ati.getName());
					courseInfoMap.put("teacherImg", ati.getImgUrl());
					courseInfoMap.put("schoolName", ati.getSchool());
					courseInfoMap.put("voteCount", voteCount+"");
					courseInfoMap.put("teacherIntroduce", ati.getIntroduce());
					redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + aci.getId(), courseInfoMap);
					redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE, voteCount, aci.getId() + "");
					redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME, aci.getUpdateTime().getTime(), aci.getId()+"");
					message = Constants.RETURN_STATUS_SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			message = Constants.RETURN_STATUS_ERROR;
		}
		return message;
	}

	/**
	 * 根据老师id更新redis数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "teacherInfo/updateById")
	public String updateTeacherInfoById(@RequestParam(value = "id", required = true) Integer id) {
		String message = null;
		try {
			ActTeacherInfo ati = actTeacherInfoService.getById(id);
			RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
			Map<String, String> teacherInfoMap = new HashMap<String, String>();
			Integer voteCount = actTeacherInfoService.getVoteCountById(id);
			teacherInfoMap.put("id", id + "");
			teacherInfoMap.put("name", ati.getName());
			teacherInfoMap.put("school", ati.getSchool());
			teacherInfoMap.put("specialty", ati.getSpecialty());
			teacherInfoMap.put("introduce", ati.getIntroduce());
			teacherInfoMap.put("imgUrl", ati.getImgUrl());
			teacherInfoMap.put("number", ati.getNumber() + "");
			teacherInfoMap.put("status", ati.getStatus() + "");
			teacherInfoMap.put("updateTime", ati.getUpdateTime().getTime() + "");
			teacherInfoMap.put("createTime", ati.getCreateTime().getTime() + "");
			teacherInfoMap.put("voteCount", voteCount+"");
			redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_TEACHERINFO + id, teacherInfoMap);
			redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_TEACHER, voteCount, ati.getId() + "");
			message = Constants.RETURN_STATUS_SUCCESS;
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			message = Constants.RETURN_STATUS_ERROR;
		}
		return message;
	}

	/**
	 * 更新redis所有老师数据
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "teacherInfo/updateAll")
	public String updateAllTeacherInfo() {
		String message = null;
		try {
			List<ActTeacherInfo> atiList = actTeacherInfoService.findAll();
			if (atiList != null && atiList.size() > 0) {
				for (ActTeacherInfo ati : atiList) {
					RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
					Map<String, String> teacherInfoMap = new HashMap<String, String>();
					Integer voteCount = actTeacherInfoService.getVoteCountById(ati.getId());
					teacherInfoMap.put("id", ati.getId() + "");
					teacherInfoMap.put("name", ati.getName());
					teacherInfoMap.put("school", ati.getSchool());
					teacherInfoMap.put("specialty", ati.getSpecialty());
					teacherInfoMap.put("introduce", ati.getIntroduce());
					teacherInfoMap.put("imgUrl", ati.getImgUrl());
					teacherInfoMap.put("number", ati.getNumber() + "");
					teacherInfoMap.put("status", ati.getStatus() + "");
					teacherInfoMap.put("voteCount", voteCount + "");
					teacherInfoMap.put("updateTime", ati.getUpdateTime().getTime() + "");
					teacherInfoMap.put("createTime", ati.getCreateTime().getTime() + "");
					redis.hashMset(RedisKeyAppConstants.TEACHER_EVALUATION_TEACHERINFO + ati.getId(), teacherInfoMap);
					redis.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_TEACHER, voteCount, ati.getId() + "");
					message = Constants.RETURN_STATUS_SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			message = Constants.RETURN_STATUS_ERROR;
		}
		return message;
	}

}
