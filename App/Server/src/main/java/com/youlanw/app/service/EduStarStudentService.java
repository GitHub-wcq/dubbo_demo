package com.youlanw.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlanw.app.entity.mysql.EduStarStudent;
import com.youlanw.app.repository.mysql.EduStarStudentDao;
import com.youlanw.app.utils.RedisUtil;
import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;
import com.youlanw.common.utils.redis.RedisPoolUtil;

@Service("eduStarStudentService")
public class EduStarStudentService {
	
	@Autowired
	private EduStarStudentDao eduStarStudentDao;
	
	
	public JsonResponseModel getStarStudent(Integer id) {
		EduStarStudent model = eduStarStudentDao.getById(id);
		RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
		System.out.println(redis.hashCode());
		System.out.println(redis.ItemGet("test001"));
		JsonResponseModel json = null;
		if(model != null) {
			json = new JsonResponseModel(ResponseCode.SUCCESS, "success",model);
		} else {
			json = new JsonResponseModel(ResponseCode.DATA_NOT_FOUND, "学员不存在");
		}
		return json;
	}
	
	public JsonResponseModel findPage(Map<String, Object> param) {
		RedisPoolUtil redis = RedisUtil.getRedisPoolUtil();
		System.out.println(redis.hashCode());
		List<EduStarStudent> list = eduStarStudentDao.findPage(param);
		return new JsonResponseModel(ResponseCode.SUCCESS, "success",list);
	}

}
