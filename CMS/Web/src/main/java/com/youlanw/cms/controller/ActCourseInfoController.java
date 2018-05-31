package com.youlanw.cms.controller;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.youlanw.cms.entity.mysql.ActCourseInfo;
import com.youlanw.cms.entity.vo.ActCourseInfoVo;
import com.youlanw.cms.service.ActCourseInfoService;
import com.youlanw.cms.service.ActTeacherInfoService;
import com.youlanw.cms.service.ImageService;
import com.youlanw.cms.utils.Constants;
import com.youlanw.cms.utils.ImageUtil;

/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
@Controller
@RequestMapping("act/actCourseInfo")
public class ActCourseInfoController {
	private Logger logger = LoggerFactory.getLogger(ActCourseInfoController.class);
	@Autowired
	private ActCourseInfoService actCourseInfoService;
	@Autowired
	private ActTeacherInfoService actTeacherInfoService;
	@Autowired
	private ImageService imageService;

	/**
	 * 查询课程列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list")
	public Map<String, Object> list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			@RequestParam(value = "teacherInfoId", required = false) Integer teacherInfoId,
			@RequestParam(value = "teacherName", required = false) String teacherName,
			@RequestParam(value = "courseNumber", required = false) Integer courseNumber) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			PageList<ActCourseInfoVo> page = actCourseInfoService.findList(pageNumber, pageSize, teacherInfoId, teacherName, courseNumber);
			if (teacherInfoId != null) {
				resultMap.put("teacherInfo", actTeacherInfoService.getById(teacherInfoId));
			}
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
	 * 新增or编辑课程
	 * 
	 * @param id
	 * @param name				课程名称
	 * @param type				课程类型
	 * @param videoUrl			课程播放链接
	 * @param videoImg			课程视频首图
	 * @param teacherInfoId		老师信息表的ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addOrEdit")
	public Map<String, Object> addOrEdit(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "name", required = true) String name, @RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "videoUrl", required = true) String videoUrl, @RequestParam(value = "videoImg", required = true) String videoImg,
			@RequestParam(value = "teacherInfoId", required = true) Integer teacherInfoId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			actCourseInfoService.addOrEdit(id, name, type, videoUrl, videoImg, teacherInfoId);
			resultMap.put("status", Constants.RETURN_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			resultMap.put("status", Constants.RETURN_STATUS_ERROR);
		}
		return resultMap;
	}

	/**
	 * 展示or隐藏课程
	 * 
	 * @param id
	 * @param status		1展示2隐藏
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hideOrShow")
	public Map<String, Object> hideOrShow(@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "status", required = true) Integer status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			actCourseInfoService.hideOrShow(id, status);
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
			ActCourseInfo aci = actCourseInfoService.getById(id);
			resultMap.put("data", aci);
			resultMap.put("status", Constants.RETURN_STATUS_SUCCESS);
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			resultMap.put("status", Constants.RETURN_STATUS_ERROR);
		}
		return resultMap;
	}

	/**
	 * 上传图片
	 * 
	 * @param fileData
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadImage")
	public Map<String, Object> uploadImage(@RequestParam(value="file",required=false)MultipartFile fileData) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String imgSuffix = ImageUtil.getExtention(fileData.getOriginalFilename());
			if (imgSuffix.equals("jpg") || imgSuffix.equals("png") || imgSuffix.equals("jpeg")) {
				String filePath = imageService.uploadImage(Constants.HEAD_FILE_FTP_PATH, fileData);
				System.out.println(filePath);
				resultMap.put("data", Constants.IMAGE_FILE_URL + filePath);
				resultMap.put("status", Constants.RETURN_STATUS_SUCCESS);
			}else {
				System.out.println("不支持的图片格式：" + imgSuffix);
				resultMap.put("status", Constants.RETURN_STATUS_ERROR);
			}
		} catch (Exception e) {
			logger.info("异常信息为：" + e);
			resultMap.put("status", Constants.RETURN_STATUS_ERROR);
		}
		return resultMap;
	}
}
