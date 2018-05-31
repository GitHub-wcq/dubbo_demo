package com.youlanw.app.controller.v1.vote;

import com.youlanw.app.service.ActTeacherInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vote")
public class TeacherController {

	private Logger logger = LoggerFactory.getLogger(TeacherController.class);

	@Autowired
	private ActTeacherInfoService teacherInfoService;


	/**
	 * @description: 获取所有老师姓名和学校姓名
	 * @method: getAllTeacherAndSchool
	 * @author: Mark
	 * @date: 22:36 2018/5/13
	 * @param
	 * @return: java.lang.String
	 */
	@RequestMapping(value = "/getAllTeacherAndSchool",method = RequestMethod.POST)
	public String getAllTeacherAndSchool() {
		String result = teacherInfoService.getAllTeacherAndSchool();
		return result;
	}
}
