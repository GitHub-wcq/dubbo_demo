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
	private Integer id;
	private String name;
	private String school;
	private String operator;
	private java.util.Date updateTime;
	private java.util.Date createTime;
	private Integer number;
	private String specialty;
	private String introduce;
	private String imgUrl;
	private Integer status;
	//columns END

	public ActTeacherInfo(){
	}

	public ActTeacherInfo(
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
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setSchool(String value) {
		this.school = value;
	}
	
	public String getSchool() {
		return this.school;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
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
	public void setNumber(Integer value) {
		this.number = value;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	public void setSpecialty(String value) {
		this.specialty = value;
	}
	
	public String getSpecialty() {
		return this.specialty;
	}
	public void setIntroduce(String value) {
		this.introduce = value;
	}
	
	public String getIntroduce() {
		return this.introduce;
	}
	public void setImgUrl(String value) {
		this.imgUrl = value;
	}
	
	public String getImgUrl() {
		return this.imgUrl;
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

