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
public class ActCourseInfo implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ActCourseInfo";
	public static final String ALIAS_ID = "课程信息表";
	public static final String ALIAS_TEACHER_INFO_ID = "老师信息表的ID";
	public static final String ALIAS_NAME = "课程名称";
	public static final String ALIAS_TYPE = "课程类型";
	public static final String ALIAS_VIDEO_URL = "课程播放链接";
	public static final String ALIAS_VIDEO_IMG = "课程视频首图";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_STATUS = "状态(1正常2冻结)";
	public static final String ALIAS_NUMBER = "课程编号";
		
	//columns START
	private java.lang.Integer id;
	private java.lang.Integer teacherInfoId;
	private java.lang.String name;
	private java.lang.String type;
	private java.lang.String videoUrl;
	private java.lang.String videoImg;
	private java.util.Date updateTime;
	private java.util.Date createTime;
	private java.lang.Integer status;
	private java.lang.Integer number;
	//columns END

	public ActCourseInfo(){
	}

	public ActCourseInfo(
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
	public void setTeacherInfoId(java.lang.Integer value) {
		this.teacherInfoId = value;
	}
	
	public java.lang.Integer getTeacherInfoId() {
		return this.teacherInfoId;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	public void setVideoUrl(java.lang.String value) {
		this.videoUrl = value;
	}
	
	public java.lang.String getVideoUrl() {
		return this.videoUrl;
	}
	public void setVideoImg(java.lang.String value) {
		this.videoImg = value;
	}
	
	public java.lang.String getVideoImg() {
		return this.videoImg;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
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
			.append("TeacherInfoId",getTeacherInfoId())
			.append("Name",getName())
			.append("Type",getType())
			.append("VideoUrl",getVideoUrl())
			.append("VideoImg",getVideoImg())
			.append("UpdateTime",getUpdateTime())
			.append("CreateTime",getCreateTime())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ActCourseInfo == false) return false;
		if(this == obj) return true;
		ActCourseInfo other = (ActCourseInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	public java.lang.Integer getNumber() {
		return number;
	}

	public void setNumber(java.lang.Integer number) {
		this.number = number;
	}
}

