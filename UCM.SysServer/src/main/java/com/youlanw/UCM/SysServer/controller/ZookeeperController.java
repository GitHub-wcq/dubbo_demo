package com.youlanw.UCM.SysServer.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlanw.UCM.Interfaces.ZookeeperService;
import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;

@Controller
@RequestMapping("zookeeper")
public class ZookeeperController {

	@Resource(name="zookeeperService")
	private ZookeeperService zookeeperService;
	
	@ResponseBody
	@RequestMapping(value="/saveZkSecretKey", method = {RequestMethod.GET,RequestMethod.POST})
	public String saveZkSecretKey(String newSecretKey,String oldSecretKey) {
		boolean flag = zookeeperService.updateZkSecretKey(newSecretKey, oldSecretKey);
		if(flag) {
			return new JsonResponseModel(ResponseCode.SUCCESS, "保存密钥成功").toJsonString();
		} else {
			return new JsonResponseModel(ResponseCode.FAILED, "保存密钥失败").toJsonString();
		}
	}
	@ResponseBody
	@RequestMapping(value="/getChilden", method = {RequestMethod.GET,RequestMethod.POST})
	public String getChilden(String path, String secretKey) {
		if(path != null) {
			if(!path.startsWith("/")) {
				return new JsonResponseModel(ResponseCode.FAILED, "路径请以'/'开头").toJsonString();
			}
			if(path.endsWith("/")) {
				return new JsonResponseModel(ResponseCode.FAILED, "路径不能以'/'结尾").toJsonString();
			}
		}
		List<String> list = zookeeperService.getChilden(path, secretKey);
		System.out.println(list.size());
		return new JsonResponseModel(ResponseCode.SUCCESS, "获取成功",list).toJsonString();
	}
}
