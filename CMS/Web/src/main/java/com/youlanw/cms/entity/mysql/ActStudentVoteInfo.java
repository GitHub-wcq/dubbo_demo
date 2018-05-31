package com.youlanw.cms.entity.mysql;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
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
	private java.lang.Integer id;
	private java.lang.Integer ylUserId;
	private java.lang.Integer teacherInfoId;
	private java.lang.Integer courseInfoId;
	private java.lang.Integer voteCount;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	private java.lang.Integer status;
	//columns END

	public ActStudentVoteInfo(){
	}

	public ActStudentVoteInfo(
		java.lang.Integer id
	){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setYlUserId(java.lang.Integer value) {
		this.ylUserId = value;
	}
	
	public java.lang.Integer getYlUserId() {
		return this.ylUserId;
	}
	public void setTeacherInfoId(java.lang.Integer value) {
		this.teacherInfoId = value;
	}
	
	public java.lang.Integer getTeacherInfoId() {
		return this.teacherInfoId;
	}
	public void setCourseInfoId(java.lang.Integer value) {
		this.courseInfoId = value;
	}
	
	public java.lang.Integer getCourseInfoId() {
		return this.courseInfoId;
	}
	public void setVoteCount(java.lang.Integer value) {
		this.voteCount = value;
	}
	
	public java.lang.Integer getVoteCount() {
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
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
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

