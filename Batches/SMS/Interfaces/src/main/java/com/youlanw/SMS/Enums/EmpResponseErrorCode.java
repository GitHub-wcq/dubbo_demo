package com.youlanw.SMS.Enums;

/**
 * 
 * @author 王晓宇 梦网接口返回码表
 */
public enum EmpResponseErrorCode {
	SUCCESS(0, "成功"),

	ERROR_100001(-100001, "鉴权不通过,请检查账号,密码,时间戳,固定串,以及MD5算法是否按照文档要求进行设置"), ERROR_100002(-100002,
			"用户多次鉴权不通过,请检查帐号,密码,时间戳,固定串,以及MD5算法是否按照文档要求进行设置"), ERROR_100003(-100003,
					"用户欠费"), ERROR_100004(-100004, "custid或者exdata字段填写不合法"),

	ERROR_100011(-100011, "短信内容超长"), ERROR_100012(-100012, "手机号码不合法"), ERROR_100014(-100014, "手机号码超过最大支持数量（1000）"),

	ERROR_100029(-100029, "端口绑定失败"),

	ERROR_100056(-100056, "用户账号登录的连接数超限"), ERROR_100057(-100057, "用户账号登录的IP错误"), ERROR_100058(-100058,
			"模版不存在"), ERROR_100059(-100059, "模板参数个数不匹配"),

	ERROR_100126(-100126, "短信有效存活时间无效"),

	ERROR_100252(-100252, "业务类型不合法(超长或包含非字母数字字符)"), ERROR_100253(-100253, "自定义参数超长"), ERROR_100254(-100254, "序列号认证失败"),

	ERROR_100999(-100999, "平台数据库内部错误"),;

	EmpResponseErrorCode(int errCode, String errMessage) {
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	private int errCode;
	private String errMessage;

	public int getErrCode() {
		return errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public static EmpResponseErrorCode valueOf(int code) {
		for(EmpResponseErrorCode item : EmpResponseErrorCode.values()) {
			if(item.errCode == code) return item;
		}
		
		return null;
	}
}
