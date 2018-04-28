package com.youlanw.common.utils.json;

/**
 * 
 * <p>Title: ReqResultCode</p>  
 * Description: <pre>请求响应码</pre>   
 * @author wangchaoqun 
 * @date 2018年4月13日
 */
public enum ResponseCode {
	/**
	 * 成功响应码
	 */
	SUCCESS(0),
	/**
	 * 失败响应码
	 */
	FAILED(1),
	/**
	 * 异常响应码
	 */
	EXCEPTION(500);
	private int code;
	private ResponseCode(int code){
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
