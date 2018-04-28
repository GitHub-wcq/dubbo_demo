package com.youlanw.UCM.Provider.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.youlanw.UCM.Interfaces.DemoService;
/**
 * 
 * <p>Title: DemoServiceImpl</p>  
 * Description: <pre>provider service demo</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
@Service(value="demoService")
public class DemoServiceImpl implements DemoService {
	
	@Override
	public String helloWorld(String str) {
		return "hello " + str;
	}

	@Override
	public List<String> getChilden(Long id) {
		List<String> demo = new ArrayList<String>();
		demo.add(String.format("Permission_%d", id - 1));
		demo.add(String.format("Permission_%d", id));
		demo.add(String.format("Permission_%d", id + 1));

		return demo;
	}
	
	

}
