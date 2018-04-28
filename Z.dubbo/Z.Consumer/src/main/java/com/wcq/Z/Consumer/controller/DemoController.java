package com.wcq.Z.Consumer.controller;

import javax.annotation.Resource;

import org.Z.Interfaces.service.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;

@Controller
@RequestMapping("demo")
public class DemoController {
	
	@Resource
	private DemoService demoService;

	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}
	@ResponseBody
	@RequestMapping(value = "/hello")
	public String hello(String userName) {
		return new JsonResponseModel(ResponseCode.SUCCESS,"",demoService.hello(userName)).toJsonString();
	}
	
}
