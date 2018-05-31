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

public class User implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "User";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_LOGINNAME = "登录名";
	public static final String ALIAS_USERNAME = "用户名";
	public static final String ALIAS_PASSWORD = "加密的密码";
	public static final String ALIAS_SALT = "加密串";
	public static final String ALIAS_REGISTERTIME = "注册时间";
	public static final String ALIAS_REGISTERTYPE = "注册类型（1：正常注册2：快捷注册）";
	public static final String ALIAS_REGISTER_BRANCH_ID = "注册时的分站";
	public static final String ALIAS_REGISTER_SOURCE = "注册来源:1:pc/2:wap/3:android/4:IOS/5:签到/6:百日大战/7:微信/8:OMS/9:SEM/10:支付宝/101:第三方同步简历用户";
	public static final String ALIAS_REGISTER_INVITE_CODE = "注册时用邀请码";
	public static final String ALIAS_INVITE_CODE = "推广时用邀请码";
	public static final String ALIAS_SOURCE_ACTIVITY_ID = "注册来源-活动ID";
	public static final String ALIAS_FIRST_LOGIN_TIME = "首次登录时间";
	public static final String ALIAS_LAST_LOGIN_TIME = "最后登录时间";
	public static final String ALIAS_LAST_LOGIN_IP = "最后登录IP";
	public static final String ALIAS_STATUS = "状态（-1：冻结、1：正常）";
	public static final String ALIAS_IS_SYN_HR = "是否已同步到HR助手[1:未同步； 2:同步中；3:已同步；4:同步异常]";
	public static final String ALIAS_LAST_SYN_HR_TIME = "最后一次同步到hr助手的时间";
	public static final String ALIAS_HAS_DOWNLOAD_APP = "是否下载APP(1：已下载；2：未下载)";
	public static final String ALIAS_FROM_KEY = "加盟线AppKey";
	public static final String ALIAS_APP_CHANNEL_CODE = "加盟线渠道code";
	public static final String ALIAS_BEHAVIOR = "用户行为";
	public static final String ALIAS_APP_SOURCE_CODE = "来源code";
	public static final String ALIAS_IS_VALIDATION = "手机号是否验证（0，未验证；1，已验证）";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
		
	//columns START
	private java.lang.Integer id;
	private java.lang.String loginname;
	private java.lang.String username;
	private java.lang.String password;
	private java.lang.String salt;
	private java.util.Date registertime;
	private java.lang.Integer registertype;
	private java.lang.Integer registerBranchId;
	private Integer registerSource;
	private java.lang.String registerInviteCode;
	private java.lang.String inviteCode;
	private java.lang.Integer sourceActivityId;
	private java.util.Date firstLoginTime;
	private java.util.Date lastLoginTime;
	private java.lang.String lastLoginIp;
	private java.lang.Integer status;
	private Integer isSynHr;
	private java.util.Date lastSynHrTime;
	private Integer hasDownloadApp;
	private java.lang.String fromKey;
	private java.lang.String appChannelCode;
	private java.lang.String behavior;
	private java.lang.String appSourceCode;
	private Integer isValidation;
	private java.util.Date updateTime;
	//columns END

	public User(){
	}

	public User(
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
	public void setLoginname(java.lang.String value) {
		this.loginname = value;
	}
	
	public java.lang.String getLoginname() {
		return this.loginname;
	}
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setSalt(java.lang.String value) {
		this.salt = value;
	}
	
	public java.lang.String getSalt() {
		return this.salt;
	}
	public void setRegistertime(java.util.Date value) {
		this.registertime = value;
	}
	
	public java.util.Date getRegistertime() {
		return this.registertime;
	}
	public void setRegistertype(java.lang.Integer value) {
		this.registertype = value;
	}
	
	public java.lang.Integer getRegistertype() {
		return this.registertype;
	}
	public void setRegisterBranchId(java.lang.Integer value) {
		this.registerBranchId = value;
	}
	
	public java.lang.Integer getRegisterBranchId() {
		return this.registerBranchId;
	}
	public void setRegisterSource(Integer value) {
		this.registerSource = value;
	}
	
	public Integer getRegisterSource() {
		return this.registerSource;
	}
	public void setRegisterInviteCode(java.lang.String value) {
		this.registerInviteCode = value;
	}
	
	public java.lang.String getRegisterInviteCode() {
		return this.registerInviteCode;
	}
	public void setInviteCode(java.lang.String value) {
		this.inviteCode = value;
	}
	
	public java.lang.String getInviteCode() {
		return this.inviteCode;
	}
	public void setSourceActivityId(java.lang.Integer value) {
		this.sourceActivityId = value;
	}
	
	public java.lang.Integer getSourceActivityId() {
		return this.sourceActivityId;
	}
	public void setFirstLoginTime(java.util.Date value) {
		this.firstLoginTime = value;
	}
	
	public java.util.Date getFirstLoginTime() {
		return this.firstLoginTime;
	}
	public void setLastLoginTime(java.util.Date value) {
		this.lastLoginTime = value;
	}
	
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	public void setLastLoginIp(java.lang.String value) {
		this.lastLoginIp = value;
	}
	
	public java.lang.String getLastLoginIp() {
		return this.lastLoginIp;
	}
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	public void setIsSynHr(Integer value) {
		this.isSynHr = value;
	}
	
	public Integer getIsSynHr() {
		return this.isSynHr;
	}
	public void setLastSynHrTime(java.util.Date value) {
		this.lastSynHrTime = value;
	}
	
	public java.util.Date getLastSynHrTime() {
		return this.lastSynHrTime;
	}
	public void setHasDownloadApp(Integer value) {
		this.hasDownloadApp = value;
	}
	
	public Integer getHasDownloadApp() {
		return this.hasDownloadApp;
	}
	public void setFromKey(java.lang.String value) {
		this.fromKey = value;
	}
	
	public java.lang.String getFromKey() {
		return this.fromKey;
	}
	public void setAppChannelCode(java.lang.String value) {
		this.appChannelCode = value;
	}
	
	public java.lang.String getAppChannelCode() {
		return this.appChannelCode;
	}
	public void setBehavior(java.lang.String value) {
		this.behavior = value;
	}
	
	public java.lang.String getBehavior() {
		return this.behavior;
	}
	public void setAppSourceCode(java.lang.String value) {
		this.appSourceCode = value;
	}
	
	public java.lang.String getAppSourceCode() {
		return this.appSourceCode;
	}
	public void setIsValidation(Integer value) {
		this.isValidation = value;
	}
	
	public Integer getIsValidation() {
		return this.isValidation;
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
			.append("Loginname",getLoginname())
			.append("Username",getUsername())
			.append("Password",getPassword())
			.append("Salt",getSalt())
			.append("Registertime",getRegistertime())
			.append("Registertype",getRegistertype())
			.append("RegisterBranchId",getRegisterBranchId())
			.append("RegisterSource",getRegisterSource())
			.append("RegisterInviteCode",getRegisterInviteCode())
			.append("InviteCode",getInviteCode())
			.append("SourceActivityId",getSourceActivityId())
			.append("FirstLoginTime",getFirstLoginTime())
			.append("LastLoginTime",getLastLoginTime())
			.append("LastLoginIp",getLastLoginIp())
			.append("Status",getStatus())
			.append("IsSynHr",getIsSynHr())
			.append("LastSynHrTime",getLastSynHrTime())
			.append("HasDownloadApp",getHasDownloadApp())
			.append("FromKey",getFromKey())
			.append("AppChannelCode",getAppChannelCode())
			.append("Behavior",getBehavior())
			.append("AppSourceCode",getAppSourceCode())
			.append("IsValidation",getIsValidation())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof User == false) return false;
		if(this == obj) return true;
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

