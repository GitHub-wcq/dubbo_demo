package com.youlanw.app.controller.education;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youlanw.app.service.EduStarStudentService;
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
	
	@RequestMapping(value = "/getStarStudentList",method = {RequestMethod.GET,RequestMethod.POST})
	public String  getStarStudentList(String id, String name) {
		JsonResponseModel json = null;
		try {
			Map<String,Object> param = new HashMap<String, Object>();
			if(id != null) {
				param.put("id", id);
			}
			if(name != null) {
				param.put("name", name);
			}
			json = eduStarStudentService.findPage(param);
		} catch (Exception e) {
			e.printStackTrace();
			json = new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "系统异常");
		}
		return json.toJsonString();
	}
	
}
