package com.youlanw.cms.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.youlanw.cms.entity.mysql.ActTeacherInfo;
import com.youlanw.cms.entity.vo.ActTeacherInfoVo;
import com.youlanw.cms.service.ActTeacherInfoService;
import com.youlanw.cms.utils.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
@Controller
@RequestMapping("act/actTeacherInfo")
public class ActTeacherInfoController {
	private Logger logger = LoggerFactory.getLogger(ActTeacherInfoController.class);
	@Autowired
	private ActTeacherInfoService actTeacherInfoService;

	/**
	 * 查询老师列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param name
	 *            老师名字
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list")
	public Map<String, Object> list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			@RequestParam(value = "name", required = false) String name,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			PageList<ActTeacherInfoVo> page = actTeacherInfoService.findList(pageNumber, pageSize, name);
			resultMap.put("data", page);
			resultMap.put("status", Constants.RETURN_STATUS_SUCCESS);
			resultMap.put("paginator",page.getPaginator());
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			resultMap.put("status", Constants.RETURN_STATUS_ERROR);
		}
		return resultMap;
	}

	/**
	 * 新增or编辑老师
	 * 
	 * @param name
	 *            老师名字
	 * @param school
	 *            所属学校
	 * @param specialty
	 *            老师专业
	 * @param introduce
	 *            老师介绍
	 * @param imgUrl
	 *            老师头像
	 * @param operator
	 *            操作人
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addOrEdit")
	public Map<String, Object> addOrEdit(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "school", required = true) String school,
			@RequestParam(value = "specialty", required = true) String specialty,
			@RequestParam(value = "introduce", required = true) String introduce,
			@RequestParam(value = "imgUrl", required = true) String imgUrl,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "operator", required = false) String operator) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			actTeacherInfoService.addOrEdit(name, school, specialty, introduce, imgUrl, id, operator);
			resultMap.put("status", Constants.RETURN_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			resultMap.put("status", Constants.RETURN_STATUS_ERROR);
		}
		return resultMap;
	}

	/**
	 * 跳转编辑页面
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "toEditPage")
	public Map<String, Object> toEditPage(@RequestParam(value = "id", required = true) Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ActTeacherInfo ati = actTeacherInfoService.getById(id);
			resultMap.put("data", ati);
			resultMap.put("status", Constants.RETURN_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			resultMap.put("status", Constants.RETURN_STATUS_ERROR);
		}
		return resultMap;
	}

}
