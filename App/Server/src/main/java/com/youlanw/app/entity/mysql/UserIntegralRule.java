package com.youlanw.app.entity.mysql;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class UserIntegralRule implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserIntegralRule";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_CODE = "code";
	public static final String ALIAS_BEHAVIOR = "行为描述";
	public static final String ALIAS_INTEGRAL = "加减积分";
	public static final String ALIAS_GROWTH = "加减成长值";
	public static final String ALIAS_INTEGRAL_UPPER_LIMIT_DAY = "积分日上限";
	public static final String ALIAS_INTEGRAL_UPPER_LIMIT_ALL = "积分总上限";
	public static final String ALIAS_EFFECTIVE_START_TIME = "开始时间";
	public static final String ALIAS_EFFECTIVE_END_TIME = "结束时间";
	public static final String ALIAS_RELATION_CODE = "关联触发的规则code";
	public static final String ALIAS_INVITE_RELATION_CODE = "邀请人关联触发规则code";
	public static final String ALIAS_STATUS = "状态0:未启用1:启用";
	public static final String ALIAS_TYPE = "类型1:新手任务2:每日任务";
		
	//columns START
	private java.lang.Integer id;
	private java.lang.String code;
	private java.lang.String behavior;
	private java.lang.Integer integral;
	private java.lang.Integer growth;
	private java.lang.Integer integralUpperLimitDay;
	private java.lang.Integer integralUpperLimitAll;
	private java.util.Date effectiveStartTime;
	private java.util.Date effectiveEndTime;
	private java.lang.String relationCode;
	private java.lang.String inviteRelationCode;
	private Integer status;
	private java.lang.Integer type;
	//columns END

	public UserIntegralRule(){
	}

	public UserIntegralRule(
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
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	public void setBehavior(java.lang.String value) {
		this.behavior = value;
	}
	
	public java.lang.String getBehavior() {
		return this.behavior;
	}
	public void setIntegral(java.lang.Integer value) {
		this.integral = value;
	}
	
	public java.lang.Integer getIntegral() {
		return this.integral;
	}
	public void setGrowth(java.lang.Integer value) {
		this.growth = value;
	}
	
	public java.lang.Integer getGrowth() {
		return this.growth;
	}
	public void setIntegralUpperLimitDay(java.lang.Integer value) {
		this.integralUpperLimitDay = value;
	}
	
	public java.lang.Integer getIntegralUpperLimitDay() {
		return this.integralUpperLimitDay;
	}
	public void setIntegralUpperLimitAll(java.lang.Integer value) {
		this.integralUpperLimitAll = value;
	}
	
	public java.lang.Integer getIntegralUpperLimitAll() {
		return this.integralUpperLimitAll;
	}
	public void setEffectiveStartTime(java.util.Date value) {
		this.effectiveStartTime = value;
	}
	
	public java.util.Date getEffectiveStartTime() {
		return this.effectiveStartTime;
	}
	public void setEffectiveEndTime(java.util.Date value) {
		this.effectiveEndTime = value;
	}
	
	public java.util.Date getEffectiveEndTime() {
		return this.effectiveEndTime;
	}
	public void setRelationCode(java.lang.String value) {
		this.relationCode = value;
	}
	
	public java.lang.String getRelationCode() {
		return this.relationCode;
	}
	public void setInviteRelationCode(java.lang.String value) {
		this.inviteRelationCode = value;
	}
	
	public java.lang.String getInviteRelationCode() {
		return this.inviteRelationCode;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Code",getCode())
			.append("Behavior",getBehavior())
			.append("Integral",getIntegral())
			.append("Growth",getGrowth())
			.append("IntegralUpperLimitDay",getIntegralUpperLimitDay())
			.append("IntegralUpperLimitAll",getIntegralUpperLimitAll())
			.append("EffectiveStartTime",getEffectiveStartTime())
			.append("EffectiveEndTime",getEffectiveEndTime())
			.append("RelationCode",getRelationCode())
			.append("InviteRelationCode",getInviteRelationCode())
			.append("Status",getStatus())
			.append("Type",getType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserIntegralRule == false) return false;
		if(this == obj) return true;
		UserIntegralRule other = (UserIntegralRule)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

