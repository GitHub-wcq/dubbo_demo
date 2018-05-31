package com.youlanw.app.entity.mysql;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class EduStarStudent implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "EduStarStudent";
	public static final String ALIAS_ID = "明星学员主键id";
	public static final String ALIAS_NAME = "明星学员名称";
	public static final String ALIAS_HEAD_IMG = "明星学员头像地址";
	public static final String ALIAS_TRAIN_SCHOOL = "明星学员培训学校";
	public static final String ALIAS_TRAIN_SCHOOL_ID = "明星学员培训学校id";
	public static final String ALIAS_TRAIN_SPECIALTY = "明星学员培训专业";
	public static final String ALIAS_TRAIN_SPECIALTY_ID = "培训专业id";
	public static final String ALIAS_COMPANY = "明星学员就职公司";
	public static final String ALIAS_COMPANY_ID = "明星学员就职公司id";
	public static final String ALIAS_CURRENT_POSITION = "明星学员当前职位";
	public static final String ALIAS_SALARY = "明星学员工资";
	public static final String ALIAS_SALARY_UNIT = "工资单位";
	public static final String ALIAS_DESCRIPTION = "个人介绍";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
		
	//columns START
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.String headImg;
	private java.lang.String trainSchool;
	private java.lang.Integer trainSchoolId;
	private java.lang.String trainSpecialty;
	private java.lang.Integer trainSpecialtyId;
	private java.lang.String company;
	private java.lang.Integer companyId;
	private java.lang.String currentPosition;
	private java.lang.Long salary;
	private java.lang.String salaryUnit;
	private java.lang.String description;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	//columns END

	public EduStarStudent(){
	}

	public EduStarStudent(
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
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	public void setHeadImg(java.lang.String value) {
		this.headImg = value;
	}
	
	public java.lang.String getHeadImg() {
		return this.headImg;
	}
	public void setTrainSchool(java.lang.String value) {
		this.trainSchool = value;
	}
	
	public java.lang.String getTrainSchool() {
		return this.trainSchool;
	}
	public void setTrainSchoolId(java.lang.Integer value) {
		this.trainSchoolId = value;
	}
	
	public java.lang.Integer getTrainSchoolId() {
		return this.trainSchoolId;
	}
	public void setTrainSpecialty(java.lang.String value) {
		this.trainSpecialty = value;
	}
	
	public java.lang.String getTrainSpecialty() {
		return this.trainSpecialty;
	}
	public void setTrainSpecialtyId(java.lang.Integer value) {
		this.trainSpecialtyId = value;
	}
	
	public java.lang.Integer getTrainSpecialtyId() {
		return this.trainSpecialtyId;
	}
	public void setCompany(java.lang.String value) {
		this.company = value;
	}
	
	public java.lang.String getCompany() {
		return this.company;
	}
	public void setCompanyId(java.lang.Integer value) {
		this.companyId = value;
	}
	
	public java.lang.Integer getCompanyId() {
		return this.companyId;
	}
	public void setCurrentPosition(java.lang.String value) {
		this.currentPosition = value;
	}
	
	public java.lang.String getCurrentPosition() {
		return this.currentPosition;
	}
	public void setSalary(java.lang.Long value) {
		this.salary = value;
	}
	
	public java.lang.Long getSalary() {
		return this.salary;
	}
	public void setSalaryUnit(java.lang.String value) {
		this.salaryUnit = value;
	}
	
	public java.lang.String getSalaryUnit() {
		return this.salaryUnit;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("HeadImg",getHeadImg())
			.append("TrainSchool",getTrainSchool())
			.append("TrainSchoolId",getTrainSchoolId())
			.append("TrainSpecialty",getTrainSpecialty())
			.append("TrainSpecialtyId",getTrainSpecialtyId())
			.append("Company",getCompany())
			.append("CompanyId",getCompanyId())
			.append("CurrentPosition",getCurrentPosition())
			.append("Salary",getSalary())
			.append("SalaryUnit",getSalaryUnit())
			.append("Description",getDescription())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof EduStarStudent == false) return false;
		if(this == obj) return true;
		EduStarStudent other = (EduStarStudent)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

