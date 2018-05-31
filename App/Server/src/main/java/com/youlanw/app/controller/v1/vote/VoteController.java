package com.youlanw.app.controller.v1.vote;

import com.youlanw.app.service.ActCourseInfoService;
import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/vote")
public class VoteController {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(VoteController.class);

	@Autowired
	private ActCourseInfoService courseInfoService;
	@Autowired
	private ActCourseInfoService actCourseInfoService;

	/**
	 * @description: 首页课程榜单
	 * @method: getStarStudent
	 * @author: Mark
	 * @date: 14:28 2018/5/12
	 * @return: java.lang.String
	 */
	@RequestMapping(value = "/getHomeCourseRankList",method = {RequestMethod.GET,RequestMethod.POST})
	public String getHomeCourseRankList() {
		return courseInfoService.getCourseRankList();
	}

	/**
	 * @description: 首页课程列表（分页搜索）
	 * @method: getStarStudent
	 * @author: Mark
	 * @date: 14:28 2018/5/12
	 * @param page
	 * @param size
	 * @param name
	 * @return: java.lang.String
	 */
	@RequestMapping(value = "/getCourseList",method = {RequestMethod.GET,RequestMethod.POST})
	public String getCourseList(@RequestParam(value = "name",required = false)String name,
								@RequestParam(value = "page",defaultValue = "1")Integer page,
								@RequestParam(value = "size",defaultValue = "10")Integer size) {
		return courseInfoService.getCoursePageList(page,size,name);
	}


	/**
	 * @description: 课程详情页
	 * @method: getCourseDetail
	 * @author: Mark
	 * @date: 14:28 2018/5/12
	 * @return: java.lang.String
	 */
	@RequestMapping(value = "/getCourseDetail",method = {RequestMethod.GET,RequestMethod.POST})
	public String getCourseDetail(@RequestParam(value = "courseId")String courseId) {
		if(courseId == null || courseId == "")
			return new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "课程id不能为空", null).toJsonString();
		return courseInfoService.getCourseDetail(courseId);
	}


	/**
	 *
	 * <p>Title: getSurplusVotes</p>
	 * Description: <pre>获取用户剩余免费票数和金币数</pre>
	 * @author wangchaoqun
	 * @date 2018年5月15日
	 * @param ylUserId
	 * @return
	 */
	@RequestMapping(value = "/getSurplusVotes",method = RequestMethod.GET)
	public String getSurplusVotes(Integer ylUserId) {
		JsonResponseModel result = null;
		try {
			if(ylUserId == null ) {
				result = new JsonResponseModel(ResponseCode.PARAMS_NOT_COMPLETE, "ylUserId用户id不能为空");
			}
			result = actCourseInfoService.getSurplusVotes(ylUserId);
		} catch (Exception e) {
			e.printStackTrace();
			result = new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "系统异常");
		}
		return result.toJsonString();
	}

	/**
	 *
	 * <p>Title: videoVote</p>
	 * Description: <pre>投票</pre>
	 * @author wangchaoqun
	 * @date 2018年5月14日
	 * @param teacherId
	 * @param courseId
	 * @param voteNum
	 * @return
	 */
	@RequestMapping(value = "/videoVote",method = RequestMethod.POST)
	public String videoVote(Integer ylUserId,Integer teacherId,Integer courseId,Integer voteNum) {
		JsonResponseModel result = null;
		try {
			if(ylUserId == null ) {
				result = new JsonResponseModel(ResponseCode.PARAMS_NOT_COMPLETE, "ylUserId用户id不能为空");
			} else if(teacherId == null ) {
				result = new JsonResponseModel(ResponseCode.PARAMS_NOT_COMPLETE, "teacherId教师id不能为空");
			} else if (courseId == null ) {
				result = new JsonResponseModel(ResponseCode.PARAMS_NOT_COMPLETE, "courseId课程id不能为空");
			} else {
				result = actCourseInfoService.videoVote(ylUserId, teacherId, courseId, voteNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "系统异常");
		}
		return result.toJsonString();
	}
	/**
	 * <p>Title: getAllVotes</p>
	 * Description: <pre>获取截止日期和总票数</pre>
	 * @author wangchaoqun
	 * @date 2018年5月15日
	 * @return
	 */
	@RequestMapping(value = "/getAllVotes",method = RequestMethod.GET)
	public String getAllVotes() {
		JsonResponseModel result = null;
		try {
			result = actCourseInfoService.getAllVotes();
		} catch (Exception e) {
			e.printStackTrace();
			result = new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "系统异常");
		}
		return result.toJsonString();
	}
	
	@RequestMapping(value = "/share",method = RequestMethod.GET)
	public String share(Integer ylUserId) {
		
		JsonResponseModel result = null;
		try {
			result = actCourseInfoService.addShareIntegral(ylUserId, 1);
		} catch (Exception e) {
			e.printStackTrace();
			result = new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "系统异常");
		}
		return result.toJsonString();
	}

}
