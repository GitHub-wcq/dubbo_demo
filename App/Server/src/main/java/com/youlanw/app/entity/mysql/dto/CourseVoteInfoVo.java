package com.youlanw.app.entity.mysql.dto;

import java.io.Serializable;

/**
 * @description: 课程投票基本信息DTO
 * @method:
 * @author: Mark
 * @date: 13:59 2018/5/12
 */
public class CourseVoteInfoVo implements Serializable{

	private Integer courseId;
	private String courseName;
	private String videoImg;
	private String updateTime;
	private String createTime;
	private Integer status;
	private Integer teacherId;
	private String teacherName;
	private String school;
	private Integer votes;
	
	public CourseVoteInfoVo() {
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getVideoImg() {
		return videoImg;
	}

	public void setVideoImg(String videoImg) {
		this.videoImg = videoImg;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
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

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}
}
