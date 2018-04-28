package com.youlanw.CMS.Provider.service;

import org.springframework.stereotype.Service;

import com.youlanw.CMS.Interfaces.DemoService;

/**
 * 
 * <p>Title: DemoServiceImpl</p>  
 * Description: <pre>provider service demo</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {

	@Override
	public String helloWorld(String str) {
		return "hello " + str;
	}

}
