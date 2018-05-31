package com.youlanw.common.utils.redis.key;

public class RedisKeyAppConstants {
	public static final String REDIS_KEY_PREFIX = "com.youlanw.app:";


	/**
	 * ======================================360教师评比======================================
	 */
	/**
	 * 老师信息key
	 */
	public static final String TEACHER_EVALUATION_TEACHERINFO = REDIS_KEY_PREFIX + "act:360:teacherInfo:";

	/**
	 * 课程信息key
	 */
	public static final String TEACHER_EVALUATION_COURSEINFO = REDIS_KEY_PREFIX + "act:360:courseInfo:";

	/**
	 * 老师投票信息key
	 */
	public static final String TEACHER_EVALUATION_VOTE_TEACHER = REDIS_KEY_PREFIX + "act:360:vote:teacher:";

	/**
	 * 课程投票信息key
	 */
	public static final String TEACHER_EVALUATION_VOTE_COURSE = REDIS_KEY_PREFIX + "act:360:vote:course:";

	/**
	 * 首页课程视频列表key
	 */
	public static final String TEACHER_EVALUATION_HOME_COURSELIST = REDIS_KEY_PREFIX + "act:360:vote:course:list:";

	/**
	 * 首页课程视频排序(更新时间)key
	 */
	public static final String TEACHER_EVALUATION_HOME_COURSELIST_ORDER_BY_UPDATETIME = REDIS_KEY_PREFIX + "act:360:vote:course:order:updateTime:";

	/**
	 * 课程视频列表搜索(动态)key
	 */
	public static final String TEACHER_EVALUATION_COURSELIST_SEARCH = REDIS_KEY_PREFIX + "act:360:vote:course:search:";

	/**
	 * 课程排名(动态)key
	 */
	public static final String TEACHER_EVALUATION_HOME_COURSE_RANK = REDIS_KEY_PREFIX + "act:360:vote:course:order:rank:";
	/**
	 * 教师评比投票用户key
	 */
	public static final String TEACHER_EVALUATION_VOTE_YL_USER = REDIS_KEY_PREFIX + "act:360:vote:yl_user:";
	/**
	 * 教师评比投票用户今日免费投票次数key
	 */
	public static final String TEACHER_EVALUATION_VOTE_YL_USER_FREE_VOTING_NUM = REDIS_KEY_PREFIX + "act:360:vote:yl_user:free:voting:num:";
	/**
	 * 教师评比投票用户今日分享次数key
	 */
	public static final String TEACHER_EVALUATION_VOTE_YL_USER_SHARE_NUM = REDIS_KEY_PREFIX + "act:360:vote:yl_user:share:num:";
	/**
	 * 所有老师和学校名称key
	 */
	public static final String TEACHER_EVALUATION_ALL_TEACHER_NAME_AND_SCHOOL_NAME = REDIS_KEY_PREFIX + "act:360:allTeacherAndSchoolName:";

}
