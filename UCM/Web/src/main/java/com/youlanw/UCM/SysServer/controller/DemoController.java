package com.youlanw.UCM.SysServer.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlanw.UCM.Interfaces.DemoService;

@Controller
@RequestMapping("demo")
public class DemoController {
	
	@Resource(name="demoService")
	private DemoService demoService;
	
	@ResponseBody
	@RequestMapping(value="/hello")
	public String hello(String str) {
		return demoService.helloWorld(str);
	}
}
