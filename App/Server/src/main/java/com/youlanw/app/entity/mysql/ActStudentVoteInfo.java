package com.youlanw.app.entity.mysql;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Jack
 * @version 1.0.0
 * @since 1.0.0
 * @date 2015-7-16 19:56:28 
 */

public class ActStudentVoteInfo implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ActStudentVoteInfo";
	public static final String ALIAS_ID = "学生投票信息";
	public static final String ALIAS_YL_USER_ID = "优蓝用户id";
	public static final String ALIAS_TEACHER_INFO_ID = "老师信息表id";
	public static final String ALIAS_COURSE_INFO_ID = "课程信息表id";
	public static final String ALIAS_VOTE_COUNT = "投票数";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_STATUS = "状态(1有效2无效)";
		
	//columns START
	private Integer id;
	private Integer ylUserId;
	private Integer teacherInfoId;
	private Integer courseInfoId;
	private Integer voteCount;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	private Integer status;
	//columns END

	public ActStudentVoteInfo(){
	}

	public ActStudentVoteInfo(
		Integer id
	){
		this.id = id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setYlUserId(Integer value) {
		this.ylUserId = value;
	}
	
	public Integer getYlUserId() {
		return this.ylUserId;
	}
	public void setTeacherInfoId(Integer value) {
		this.teacherInfoId = value;
	}
	
	public Integer getTeacherInfoId() {
		return this.teacherInfoId;
	}
	public void setCourseInfoId(Integer value) {
		this.courseInfoId = value;
	}
	
	public Integer getCourseInfoId() {
		return this.courseInfoId;
	}
	public void setVoteCount(Integer value) {
		this.voteCount = value;
	}
	
	public Integer getVoteCount() {
		return this.voteCount;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("YlUserId",getYlUserId())
			.append("TeacherInfoId",getTeacherInfoId())
			.append("CourseInfoId",getCourseInfoId())
			.append("VoteCount",getVoteCount())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ActStudentVoteInfo == false) return false;
		if(this == obj) return true;
		ActStudentVoteInfo other = (ActStudentVoteInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

