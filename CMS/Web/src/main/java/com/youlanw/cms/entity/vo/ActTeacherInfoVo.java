package com.youlanw.cms.entity.vo;

import java.io.Serializable;
import java.util.Date;

import com.youlanw.common.utils.DateConvertUtils;



/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月9日
 */
public class ActTeacherInfoVo implements Serializable{
	private static final long serialVersionUID = 883446690909293426L;
	private Integer id;
	private String name;
	private String school;
	private String operator;
	private Date updateTime;
	private String updateTimeString;
	private Integer number;
	private Integer voteCount;
	
	public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setSchool(java.lang.String value) {
		this.school = value;
	}
	
	public java.lang.String getSchool() {
		return this.school;
	}
	public void setOperator(java.lang.String value) {
		this.operator = value;
	}
	
	public java.lang.String getOperator() {
		return this.operator;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public String getUpdateTimeString() {
		return DateConvertUtils.format(getUpdateTime(), FORMAT_TIME);
	}
	public void setNumber(java.lang.Integer value) {
		this.number = value;
	}
	
	public java.lang.Integer getNumber() {
		return this.number;
	}

	public java.lang.Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(java.lang.Integer voteCount) {
		this.voteCount = voteCount;
	}
	
}

