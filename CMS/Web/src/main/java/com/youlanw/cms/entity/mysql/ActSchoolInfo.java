package com.youlanw.cms.entity.mysql;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author younglee
 * @version 1.0
 * @date 2018年5月17日
 */
public class ActSchoolInfo implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ActSchoolInfo";
	public static final String ALIAS_ID = "学校信息表";
	public static final String ALIAS_NUMBER = "学校编号";
	public static final String ALIAS_NAME = "学校名称";
	public static final String ALIAS_ADDRESS = "学校地址";
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_STATUS = "状态(1正常2不正常)";
		
	//columns START
	private java.lang.Integer id;
	private java.lang.Integer number;
	private java.lang.String name;
	private java.lang.String address;
	private java.util.Date updateTime;
	private java.util.Date createTime;
	private java.lang.Integer status;
	//columns END

	public ActSchoolInfo(){
	}

	public ActSchoolInfo(
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
	public void setNumber(java.lang.Integer value) {
		this.number = value;
	}
	
	public java.lang.Integer getNumber() {
		return this.number;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	public java.lang.String getAddress() {
		return this.address;
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
			.append("Number",getNumber())
			.append("Name",getName())
			.append("Address",getAddress())
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
		if(obj instanceof ActSchoolInfo == false) return false;
		if(this == obj) return true;
		ActSchoolInfo other = (ActSchoolInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

