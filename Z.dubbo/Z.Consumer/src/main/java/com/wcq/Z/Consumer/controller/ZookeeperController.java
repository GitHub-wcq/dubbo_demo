package com.wcq.Z.Consumer.controller;

import java.util.List;

import javax.annotation.Resource;

import org.Z.Interfaces.service.ZookeeperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;

/**
 * 
 * <p>Title: ZookeeperController</p>  
 * Description: <pre></pre>   
 * @author wangchaoqun 
 * @date 2018年4月18日
 */
@Controller
@RequestMapping("zookeeper")
public class ZookeeperController {
	
	@Resource(name = "zookeeperService")
	private ZookeeperService zookeeperService;
	/**
	 * 
	 * <p>Title: getChilden</p>  
	 * Description: <pre>获取path目录下的所有节点名称</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月18日  
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getChilden", method = {RequestMethod.POST,RequestMethod.GET})
	public String getChilden(String path) {
		List<String> list = zookeeperService.getChilden(path);
		JsonResponseModel json = new JsonResponseModel(ResponseCode.SUCCESS, "success",list);
		return json.toJsonString();
	}
	/**
	 * 
	 * <p>Title: getZnodeDate</p>  
	 * Description: <pre>获取path节点下的数据</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月18日  
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getZnodeDate", method = {RequestMethod.POST,RequestMethod.GET})
	public String getZnodeDate(String path) {
		JsonResponseModel json = null;
		try {
			String znodeData = zookeeperService.getZnodeDate(path);
			json = new JsonResponseModel(ResponseCode.SUCCESS, "success",znodeData);
		} catch (Exception e) {
			e.printStackTrace();
			json = new JsonResponseModel(ResponseCode.EXCEPTION, "请求异常");
		}
		return json.toJsonString();
	}
	/**
	 * 
	 * <p>Title: addPersistentZnode</p>  
	 * Description: <pre>添加持久节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月18日  
	 * @param znode 节点名称
	 * @param data 节点内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersistentZnode", method = {RequestMethod.POST,RequestMethod.GET})
	public String addPersistentZnode(String znode,String data) {
		JsonResponseModel json = null;
		try {
			boolean flag = zookeeperService.addPersistentZnode(znode, data);
			if(flag) {
				json = new JsonResponseModel(ResponseCode.SUCCESS, "成功");
			}else {
				json = new JsonResponseModel(ResponseCode.FAILED, "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = new JsonResponseModel(ResponseCode.EXCEPTION, "请求异常");
		}
		return json.toJsonString();
	}
	/**
	 * 
	 * <p>Title: addEphemeralZnode</p>  
	 * Description: <pre>添加临时节点</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月18日  
	 * @param znode
	 * @param data
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addEphemeralZnode", method = {RequestMethod.POST,RequestMethod.GET})
	public String addEphemeralZnode(String znode,String data) {
		JsonResponseModel json = null;
		try {
			boolean flag = zookeeperService.addEphemeralZnode(znode, data);
			if(flag) {
				json = new JsonResponseModel(ResponseCode.SUCCESS, "添加临时节点成功");
			}else {
				json = new JsonResponseModel(ResponseCode.FAILED, "添加临时节点失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = new JsonResponseModel(ResponseCode.EXCEPTION, "请求异常");
		}
		return json.toJsonString();
	}
}
