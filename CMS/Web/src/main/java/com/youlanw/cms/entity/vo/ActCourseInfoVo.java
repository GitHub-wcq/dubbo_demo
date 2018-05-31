package com.youlanw.cms.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.youlanw.common.utils.DateConvertUtils;



/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
public class ActCourseInfoVo implements Serializable{

	private static final long serialVersionUID = -25455202202115482L;
	
	private Integer id;
	private String courseNumber;
	private String courseName;
	private String courseType;
	private String teacherName;
	private String school;
	private Date createTime;
	private String createTimeString;
	private Integer voteCount;
	private Integer status;
	
	public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_TIME);
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
