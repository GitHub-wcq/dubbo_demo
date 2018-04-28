package org.Z.Provider.service.impl;

import org.Z.Interfaces.service.DemoService;
import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

	@Override
	public String hello(String str) {
		return "hello " + str;
	}

}
