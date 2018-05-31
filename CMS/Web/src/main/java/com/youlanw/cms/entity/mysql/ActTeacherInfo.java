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
public class ActTeacherInfo implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ActTeacherInfo";
	public static final String ALIAS_ID = "老师信息表";
	public static final String ALIAS_NAME = "老师名字";
	public static final String ALIAS_SCHOOL = "所属学校";
	public static final String ALIAS_OPERATOR = "操作人";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_NUMBER = "老师编号";
	public static final String ALIAS_SPECIALTY = "老师专业";
	public static final String ALIAS_INTRODUCE = "老师介绍";
	public static final String ALIAS_IMG_URL = "老师头像";
	public static final String ALIAS_STATUS = "状态(1正常2冻结)";
		
	//columns START
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String school;
	private java.lang.String operator;
	private java.util.Date updateTime;
	private java.util.Date createTime;
	private java.lang.Integer number;
	private java.lang.String specialty;
	private java.lang.String introduce;
	private java.lang.String imgUrl;
	private java.lang.Integer status;
	//columns END

	public ActTeacherInfo(){
	}

	public ActTeacherInfo(
		java.lang.Integer id,
	
		java.lang.Integer number
	){
		this.id = id;
		this.number = number;
	}

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
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setNumber(java.lang.Integer value) {
		this.number = value;
	}
	
	public java.lang.Integer getNumber() {
		return this.number;
	}
	public void setSpecialty(java.lang.String value) {
		this.specialty = value;
	}
	
	public java.lang.String getSpecialty() {
		return this.specialty;
	}
	public void setIntroduce(java.lang.String value) {
		this.introduce = value;
	}
	
	public java.lang.String getIntroduce() {
		return this.introduce;
	}
	public void setImgUrl(java.lang.String value) {
		this.imgUrl = value;
	}
	
	public java.lang.String getImgUrl() {
		return this.imgUrl;
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
			.append("Name",getName())
			.append("School",getSchool())
			.append("Operator",getOperator())
			.append("UpdateTime",getUpdateTime())
			.append("CreateTime",getCreateTime())
			.append("Number",getNumber())
			.append("Specialty",getSpecialty())
			.append("Introduce",getIntroduce())
			.append("ImgUrl",getImgUrl())
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
		if(obj instanceof ActTeacherInfo == false) return false;
		if(this == obj) return true;
		ActTeacherInfo other = (ActTeacherInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.append(getNumber(),other.getNumber())
			.isEquals();
	}
}

