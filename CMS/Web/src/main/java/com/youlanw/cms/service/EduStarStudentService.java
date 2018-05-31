package com.youlanw.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlanw.cms.entity.mysql.EduStarStudent;
import com.youlanw.cms.repository.mysql.EduStarStudentDao;
import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;

@Service("eduStarStudentService")
public class EduStarStudentService {
	
	@Autowired
	private EduStarStudentDao eduStarStudentDao;
	
	
	public JsonResponseModel getStarStudent(Integer id) {
		EduStarStudent model = eduStarStudentDao.getById(id);
		JsonResponseModel json = null;
		if(model != null) {
			json = new JsonResponseModel(ResponseCode.SUCCESS, "success",model);
		} else {
			json = new JsonResponseModel(ResponseCode.DATA_NOT_FOUND, "学员不存在");
		}
		return json;
	}

}
