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

public class ActCourseInfo implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ActCourseInfo";
	public static final String ALIAS_ID = "课程信息表";
	public static final String ALIAS_TEACHER_INFO_ID = "老师信息表的ID";
	public static final String ALIAS_NAME = "课程名称";
	public static final String ALIAS_TYPE = "课程类型";
	public static final String ALIAS_NUMBER = "课程编号";
	public static final String ALIAS_VIDEO_URL = "课程播放链接";
	public static final String ALIAS_VIDEO_IMG = "课程视频首图";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_STATUS = "状态(1展示2隐藏)";
		
	//columns START
	private Integer id;
	private Integer teacherInfoId;
	private String name;
	private String type;
	private Integer number;
	private String videoUrl;
	private String videoImg;
	private java.util.Date updateTime;
	private java.util.Date createTime;
	private Integer status;
	//columns END

	public ActCourseInfo(){
	}

	public ActCourseInfo(
		Integer id,
	
		Integer number
	){
		this.id = id;
		this.number = number;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setTeacherInfoId(Integer value) {
		this.teacherInfoId = value;
	}
	
	public Integer getTeacherInfoId() {
		return this.teacherInfoId;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setType(String value) {
		this.type = value;
	}
	
	public String getType() {
		return this.type;
	}
	public void setNumber(Integer value) {
		this.number = value;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	public void setVideoUrl(String value) {
		this.videoUrl = value;
	}
	
	public String getVideoUrl() {
		return this.videoUrl;
	}
	public void setVideoImg(String value) {
		this.videoImg = value;
	}
	
	public String getVideoImg() {
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
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TeacherInfoId",getTeacherInfoId())
			.append("Name",getName())
			.append("Type",getType())
			.append("Number",getNumber())
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
			.append(getNumber())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ActCourseInfo == false) return false;
		if(this == obj) return true;
		ActCourseInfo other = (ActCourseInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.append(getNumber(),other.getNumber())
			.isEquals();
	}
}

