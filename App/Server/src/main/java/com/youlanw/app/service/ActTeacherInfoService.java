package com.youlanw.app.service;

import com.youlanw.app.entity.mysql.ActTeacherInfo;
import com.youlanw.app.repository.mysql.ActTeacherInfoDao;
import com.youlanw.app.utils.RedisUtil;
import com.youlanw.app.utils.SerializeUtil;
import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;
import com.youlanw.common.utils.redis.RedisPoolUtil;
import com.youlanw.common.utils.redis.key.RedisKeyAppConstants;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 教师业务逻辑层
 * @method: 
 * @author: Mark
 * @date: 13:58 2018/5/10
 */
@Component
@Transactional
public class ActTeacherInfoService {
	@Autowired
	private ActTeacherInfoDao teacherInfoDao;

	/*temp 查询视频列表+条件查询mysql*/
	public List<ActTeacherInfo> getTeacherBySchool(String school){
		return teacherInfoDao.getTeacherBySchool(school);
	}

	/**
	 * @description: 获取所有老师姓名和学校姓名
	 * @method: getAllTeacherAndSchool
	 * @author: Mark
	 * @date: 22:28 2018/5/13
	 * @return: java.util.Map<java.lang.String,java.util.List<java.lang.String>>
	 */
	public String getAllTeacherAndSchool() {
		Map<String, List<String>> result = new HashMap<>();
		try {
			RedisPoolUtil redisPoolUtil = RedisUtil.getRedisPoolUtil();
			if(redisPoolUtil.ItemGet(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME) != null){
                //从redis缓存里获取数据
                result = (Map<String, List<String>>) SerializeUtil.unserialize(redisPoolUtil.ListGet(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME));
            }else{
                List<String> teachersName = teacherInfoDao.getAllTeacherName();
                List<String> schoolsName = teacherInfoDao.getAllSchoolName();
                result.put("teacher", teachersName);
                result.put("school", schoolsName);
                //保存到redis
				redisPoolUtil.ListSet(RedisKeyAppConstants.TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME, SerializeUtil.serialize(result));
            }
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "获取异常").toJsonString();
		}
		return new JsonResponseModel(ResponseCode.SUCCESS, "获取成功", JSONObject.fromObject(result)).toJsonString();
	}
}
