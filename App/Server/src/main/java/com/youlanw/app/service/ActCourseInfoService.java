package com.youlanw.app.service;

import com.youlanw.app.entity.mysql.ActStudentVoteInfo;
import com.youlanw.app.entity.mysql.User;
import com.youlanw.app.entity.mysql.UserProfile;
import com.youlanw.app.entity.mysql.dto.CourseVoteInfoVo;
import com.youlanw.app.repository.mysql.ActCourseInfoDao;
import com.youlanw.app.repository.mysql.ActStudentVoteInfoDao;
import com.youlanw.app.repository.mysql.UserIntegralRuleMapper;
import com.youlanw.app.repository.mysql.UserMapper;
import com.youlanw.app.repository.mysql.UserProfileMapper;
import com.youlanw.app.utils.RedisUtil;
import com.youlanw.app.utils.url.IntegralApi;
import com.youlanw.common.utils.Calendar.CalendarUtil;
import com.youlanw.common.utils.json.JsonResponseModel;
import com.youlanw.common.utils.json.ResponseCode;
import com.youlanw.common.utils.redis.RedisPoolUtil;
import com.youlanw.common.utils.redis.key.RedisKeyAppConstants;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description: 课程视频
 * @method: 
 * @author: Mark
 * @date: 13:58 2018/5/10
 */
@Component
@Transactional
public class ActCourseInfoService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(ActCourseInfoService.class);

	@Autowired
	private ActCourseInfoDao courseInfoDao;
	@Autowired
	private ActStudentVoteInfoDao actStudentVoteInfoDao;
	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private UserIntegralRuleMapper userIntegralRuleMapper;
	@Autowired
	private UserMapper userMapper;

    private RedisPoolUtil redisPoolUtil = RedisUtil.getRedisPoolUtil();

	/**
	 * @description: 首页课程列表(排序:更新时间倒序)
	 * @method: getCoursePageList
	 * @author: Mark
	 * @date: 13:23 2018/5/15
	 * @param page
	 * @param size
	 * @return: java.lang.String
	 */
    public String getCoursePageList(Integer page, Integer size,String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("size", size);
        Set<String> CourseIds = null;
        List<Map<String, String>> result = new ArrayList<>();
		try {
			if(name != null && name != "") {
				paramMap.put("name", name);
				logger.info("==========搜索课程列表==========");
				logger.info("===请求参数："+paramMap.toString());
				if(redisPoolUtil.isExists(RedisKeyAppConstants.TEACHER_EVALUATION_COURSELIST_SEARCH))
					redisPoolUtil.delByKey(RedisKeyAppConstants.TEACHER_EVALUATION_COURSELIST_SEARCH);
				if (!this.refreshData(paramMap)) {
					logger.info("===Redis-重建搜索课程分页索引失败(更新时间)");
					return new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "获取失败", new HashMap<String,Object>()).toJsonString();
				}
				logger.info("===Redis-重建搜索课程分页索引成功(更新时间)");
				CourseIds = redisPoolUtil.zrevrange(RedisKeyAppConstants.TEACHER_EVALUATION_COURSELIST_SEARCH, (page-1)*size, page*size-1);
			}else {
				logger.info("==========获取课程列表==========");
				logger.info("===请求参数："+paramMap.toString());
				if(!redisPoolUtil.isExists(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME)){
					if (!this.refreshData(paramMap)) {
						logger.info("===Redis-重建课程分页索引失败(更新时间)");
						return new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "获取失败", new HashMap<String,Object>()).toJsonString();
					}
					logger.info("===Redis-重建课程分页索引成功(更新时间)");
				}
				CourseIds = redisPoolUtil.zrevrange(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME, (page-1)*size, page*size-1);
			}
			//返回课程信息
			Map<String, String> courseMap = null;
			for (String id : CourseIds) {
				courseMap = redisPoolUtil.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO+id);
				if(courseMap.size()>0)
					result.add(courseMap);
            }
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "获取失败", new HashMap<String,Object>()).toJsonString();
		}
		return new JsonResponseModel(ResponseCode.SUCCESS, "获取成功", JSONArray.fromObject(result)).toJsonString();
    }



    /**
     * @description: 首页课程榜单
     * @method: getCourseListInHome
     * @author: Mark
     * @date: 15:17 2018/5/12
     * @return: com.github.miemiedev.mybatis.paginator.domain.PageList<com.youlanw.app.entity.mysql.dto.CourseVoteInfo>
     */
    public String getCourseRankList() {
    	logger.info("==========获取课程榜单==========");
        List<Map<String, String>> result = new ArrayList<>();
		try {
			if(!redisPoolUtil.isExists(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE)){
				logger.info("===Redis-课程投票Key："+RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE+" 数据为空");
				return new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "获取失败", new HashMap<String,Object>()).toJsonString();
			}
			Set<String> zrevrange = redisPoolUtil.zrevrange(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE, 0, 19);
			for (String s : zrevrange) {
                Map<String, String> stringStringMap = redisPoolUtil.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO+s);
                if(stringStringMap.size()>0)
                	result.add(stringStringMap);
            }
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "获取失败", new HashMap<String,Object>()).toJsonString();
		}
		return new JsonResponseModel(ResponseCode.SUCCESS, "获取成功", JSONArray.fromObject(result)).toJsonString();
    }


	/**
	 * @description: 课程详情
	 * @method: getCourseDetail
	 * @author: Mark
	 * @date: 13:23 2018/5/15
	 * @param courseId
	 * @return: java.lang.String
	 */
	public String getCourseDetail(String courseId) {
		logger.info("==========获取课程详情==========");
		logger.info("===请求参数：courseId："+courseId);
		Map<String, String> resultMap = null;
		try {
			//计算课程票数排名
			Set<String> zrevrange = redisPoolUtil.zrevrange(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE, 0, redisPoolUtil.zcard(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE));
			int i = 1;
			for (String id:zrevrange){
				if(redisPoolUtil.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO+id).size() > 0){
					redisPoolUtil.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSE_RANK, i, id);
					i++;
				}
			}
			resultMap = redisPoolUtil.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO+courseId);
			if(resultMap.size() > 0){
				//计算课程排名
				Double zscore = redisPoolUtil.zscore(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSE_RANK, courseId);
				resultMap.put("rank", zscore!=null?((int) Math.ceil(zscore))+"":"");	//返回课程排名字段
				//获取老师专业
				String teacherInfoId = resultMap.get("teacherInfoId");
				Map<String, String> teacherInfo = redisPoolUtil.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_TEACHERINFO + teacherInfoId);
				resultMap.put("major", teacherInfo.size()>0?teacherInfo.get("specialty"):"");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResponseModel(ResponseCode.SYSTEM_INNER_ERROR, "获取失败", new HashMap<String,Object>()).toJsonString();
		}
		return new JsonResponseModel(ResponseCode.SUCCESS, "获取成功", resultMap).toJsonString();
	}


	/**
	 * @description: 从mysql重新导入数据，创建分页索引
	 * @method: refreshData
	 * @author: Mark
	 * @date: 10:49 2018/5/15
	 * @param paramMap
	 * @return: boolean
	 */
	public boolean refreshData(Map<String, Object> paramMap) {
		try {
			List<CourseVoteInfoVo> homeCourses = courseInfoDao.getHomeCourses(paramMap);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (CourseVoteInfoVo homeCours : homeCourses) {
                Map<String, String> courseMap = redisPoolUtil.hashGetAll(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + homeCours.getCourseId());
                if(courseMap.size() > 0){
					Date date = simpleDateFormat.parse(homeCours.getUpdateTime());
					long time = date.getTime();
					if(paramMap.size() > 2)
						redisPoolUtil.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_COURSELIST_SEARCH,time,homeCours.getCourseId().toString());
					else
						redisPoolUtil.zadd(RedisKeyAppConstants.TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME,time,homeCours.getCourseId().toString());
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 *
	 * <p>Title: getSurplusVotes</p>
	 * Description: <pre>360教师评比查询用户剩余免费票数和金币数</pre>
	 * @author wangchaoqun
	 * @date 2018年5月15日
	 * @param ylUserId
	 * @return
	 */
	public JsonResponseModel getSurplusVotes(Integer ylUserId) {
		Integer freeVotes = 3;
		Integer surplusCoin = 0;
		if(!redisPoolUtil.isExists(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId)) {
			redisPoolUtil.ItemIncrBy(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId, Long.valueOf(freeVotes));
			//设置过期时间,第二天0点
			redisPoolUtil.ExpireAt(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId, CalendarUtil.getTomorrowZero());
		} else {
			freeVotes = Integer.valueOf(redisPoolUtil.ItemGet(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId.toString()));
		}
		//获取金币
		UserProfile userProfile = userProfileMapper.getByUserId(ylUserId);
		if(userProfile == null) {
			return new JsonResponseModel(ResponseCode.USER_NOT_EXIST, "用户不存在");
		}
		if(userProfile.getIntegral() != null) {
			surplusCoin = userProfile.getIntegral();
		}
		Map<String,Object> map = new HashMap<>();
		map.put("allFreeVotes", 3);
		map.put("freeVotes", freeVotes);
		map.put("surplusCoin", surplusCoin);
		map.put("coinToVote", 1);
		return new JsonResponseModel(ResponseCode.SUCCESS, "success",map);
	}
	/**
	 *
	 * <p>Title: videoVote</p>
	 * Description: <pre>360教师评比投票</pre>
	 * @author wangchaoqun
	 * @date 2018年5月15日
	 * @param ylUserId
	 * @param teacherId
	 * @param courseId
	 * @param voteNum
	 * @return
	 */
	public JsonResponseModel videoVote(Integer ylUserId,Integer teacherId,Integer courseId,Integer voteNum) {
		if(voteNum == null) {
			voteNum = 1;
		}
		UserProfile userProfile = userProfileMapper.getByUserId(ylUserId);
		if(userProfile == null) {
			User user = userMapper.getById(ylUserId);
			if(user == null) {
				return new JsonResponseModel(ResponseCode.USER_NOT_EXIST, "用户不存在");
			} else {
				userProfile = new UserProfile();
				userProfile.setUserId(user.getId());
				userProfile.setMobile(user.getLoginname());
				userProfileMapper.insert(userProfile);
			}
		}
		//判断ylUserId用户今日免费投票次数key在redis中是否存在
		if(!redisPoolUtil.isExists(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId)) {
			//不存在，设置剩余免费投票字段初始值为3
			redisPoolUtil.ItemSet(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId, "3");
			//设置过期时间,第二天0点
			redisPoolUtil.ExpireAt(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId, CalendarUtil.getTomorrowZero());
		}
		//获取剩余免费票数
		String userFreeVoting = redisPoolUtil.ItemGet(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId);
		Integer userFreeVotingNum = Integer.valueOf(userFreeVoting);
		String behavior = userIntegralRuleMapper.getCodeByBehavior("360教师评比投票");
		if(userFreeVotingNum > 0 && userFreeVotingNum >= voteNum) {
			//如果剩余票数大于零且大于本次要投票的数量，直接投票,-用户免费投票数
			redisPoolUtil.ItemDecrBy(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId, voteNum);
		} else if(userFreeVotingNum > 0 && userFreeVotingNum < voteNum) {
			//如果剩余票数大于零且小于本次要投票的个数，免费票数全部扣除，再扣除金币
			redisPoolUtil.ItemDecrBy(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId, userFreeVotingNum);
			Integer ii = voteNum - userFreeVotingNum;
			//获取积分金币
			Integer integral = userProfile.getIntegral();
			if(integral < ii) {
				return new JsonResponseModel(ResponseCode.BUSINESS_ERROR, "用户剩余金币不足");
			} else {
				IntegralApi.postIntegralApi(behavior, ylUserId, ii);
			}
		} else if(userFreeVotingNum == 0) {
			//如果=0，将免费票数-1变为负数
			redisPoolUtil.ItemDecr(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM + ylUserId);
			Integer integral = userProfile.getIntegral();
			if(integral - voteNum < 0) {
				return new JsonResponseModel(ResponseCode.BUSINESS_ERROR, "用户剩余金币不足");
			} else {
				IntegralApi.postIntegralApi(behavior, ylUserId, voteNum);
			}
		} else if(userFreeVotingNum < 0) {
			//免费次数为负数，如果金币够，直接扣除金币，不够给提示。
			Integer integral = userProfile.getIntegral();
			if(integral < voteNum) {
				return new JsonResponseModel(ResponseCode.BUSINESS_ERROR, "用户剩余金币不足");
			} else {
				IntegralApi.postIntegralApi(behavior, ylUserId, voteNum);
			}
		}
		//增加老师投票数
		redisPoolUtil.zincrby(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_TEACHER, voteNum, teacherId.toString());
		//增加课程投票数
		redisPoolUtil.zincrby(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_COURSE, voteNum, courseId.toString());

		if(redisPoolUtil.isExists(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + courseId)) {
			redisPoolUtil.hashIncrBy(RedisKeyAppConstants.TEACHER_EVALUATION_COURSEINFO + courseId, "voteCount", voteNum);
		} else {
			return new JsonResponseModel(ResponseCode.DATA_NOT_FOUND, "courseId:"+courseId+" 课程id未查到");
		}
		
		if(redisPoolUtil.isExists(RedisKeyAppConstants.TEACHER_EVALUATION_TEACHERINFO + teacherId)) {
			redisPoolUtil.hashIncrBy(RedisKeyAppConstants.TEACHER_EVALUATION_TEACHERINFO + teacherId, "voteCount", voteNum);
		} else {
			return new JsonResponseModel(ResponseCode.DATA_NOT_FOUND, "teacherId:"+teacherId+" 教师id未查到");
		}
		VideoVoteThread videoVoteThread = new VideoVoteThread(ylUserId, teacherId, courseId, voteNum);
		Thread thread = new Thread(videoVoteThread);
		thread.start();
		return new JsonResponseModel(ResponseCode.SUCCESS, "投票成功");
	}
	/**
	 *
	 * <p>Title: VideoVoteThread</p>
	 * Description: <pre>开设线程用来在MySQL中添加用户投票信息</pre>
	 * @author wangchaoqun
	 * @date 2018年5月15日
	 */
	class VideoVoteThread implements Runnable{
		private Integer ylUserId;
		private Integer teacherId;
		private Integer courseId;
		private Integer voteNum;
		public VideoVoteThread(Integer ylUserId,Integer teacherId,Integer courseId,Integer voteNum) {
			this.ylUserId = ylUserId;
			this.teacherId = teacherId;
			this.courseId = courseId;
			this.voteNum = voteNum;
		}
		@Override
		public void run() {
			ActStudentVoteInfo entity = new ActStudentVoteInfo();
			entity.setTeacherInfoId(teacherId);
			entity.setCourseInfoId(courseId);
			entity.setVoteCount(voteNum);
			entity.setYlUserId(ylUserId);
			entity.setCreateTime(new Date());
			entity.setStatus(1);
			actStudentVoteInfoDao.insert(entity);
		}
		
	}
	/**
	 * <p>Title: getAllVotes</p>
	 * Description: <pre>获取截止日期和总票数</pre>
	 * @author wangchaoqun
	 * @date 2018年5月15日
	 * @return
	 */
	public JsonResponseModel getAllVotes() {
		String endDate = "2018-06-17";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long dayDiff = null;
		try {
			dayDiff = CalendarUtil.getdayDiff(sdf.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Long allVoteCount = actStudentVoteInfoDao.findAllVoteCount();
		Map<String, Object> map = new HashMap<>();
		map.put("endDate", "6月17日");
		map.put("surplusDays", dayDiff);
		map.put("allVoteCount", allVoteCount);
		return new JsonResponseModel(ResponseCode.SUCCESS, "查询成功",map);
	}
	
	/**
	 * 
	 * <p>Title: addShareIn</p>  
	 * Description: <pre>分享页添加积分</pre>  
	 * @author wangchaoqun 
	 * @date 2018年5月15日  
	 * @param userId
	 * @param num
	 */
	public JsonResponseModel addShareIntegral(Integer userId,Integer num) {
		//判断ylUserId用户今日分享次数key在redis中是否存在
		if(!redisPoolUtil.isExists(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_SHARE_NUM + userId)) {
			//不存在，设置剩余今日分享字段初始值为3
			redisPoolUtil.ItemSet(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_SHARE_NUM + userId, "3");
			//设置过期时间,第二天0点
			redisPoolUtil.ExpireAt(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_SHARE_NUM + userId, CalendarUtil.getTomorrowZero());
		}
		String shareStr = redisPoolUtil.ItemGet(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_SHARE_NUM + userId);
		Integer shareNum = Integer.valueOf(shareStr);
		if(shareNum>0) {
			String behavior = userIntegralRuleMapper.getCodeByBehavior("360教师评比分享");
			//添加积分
			IntegralApi.postIntegralApi(behavior, userId, num);
			//分享次数-1
			redisPoolUtil.ItemDecr(RedisKeyAppConstants.TEACHER_EVALUATION_VOTE_YL_USER_SHARE_NUM + userId);
			return new JsonResponseModel(ResponseCode.SUCCESS, "分享成功",1);
		} else {
			return new JsonResponseModel(ResponseCode.SUCCESS, "分享成功",0);
		}
		
	}
	
}


