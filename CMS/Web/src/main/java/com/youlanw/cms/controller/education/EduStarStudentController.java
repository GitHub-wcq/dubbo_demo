package com.youlanw.cms.controller.education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youlanw.cms.service.EduStarStudentService;
import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;

@RestController
@RequestMapping("/eduStarStudent")
public class EduStarStudentController {

	@Autowired
	private EduStarStudentService eduStarStudentService;
	
	@RequestMapping(value = "/getStarStudent",method = RequestMethod.GET)
	public String  getStarStudent(Integer id) {
		System.out.println("id : " + id);
		JsonResponseModel json = null;
		try {
			if(id == null) {
				json = new JsonResponseModel(ResponseCode.PARAMS_IS_NULL, "id不能为空");
			} else {
				json = eduStarStudentService.getStarStudent(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "系统异常");
		}
		return json.toJsonString();
	}
	
}
