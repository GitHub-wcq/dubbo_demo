package com.youlanw.SMS.Beans;

import com.youlanw.SMS.Enums.EmpResponseErrorCode;

/**
 * 
 * @author 王晓宇
 * 梦网 EMP 标准版API接口(V5.3.2)
 * 适用于 单条发送接口 
 * 如果有自定义参数需要接收，参考 SmsRequestBodyForEmpSingleNumber 中定义的说明增加字段
 */
public class SmsResponseBodyForEmpSingleNumber {
	
	// region 短信发送请求处理结果：
	// 0：成功；非0：失败
	int result = 0;
	
	public void setResult(int result) {
		this.result = result;
	}

	public EmpResponseErrorCode getResult() {
		return EmpResponseErrorCode.valueOf(this.result);
	}
	// end
	
	// region 平台流水号
	// 非0，64位整型，对应Java和C#的long，不可用int解析。
	// result非0时，msgid为0。
	// 注意：msgid允许出现负数
	long msgid = 0;
	
	public void setMsgid(long msgid) {
		this.msgid = msgid;
	}

	public long getMsgid() {
		return this.msgid;
	}
	// end
	
	// region 用户自定义流水号
	// 默认与请求报文中的custid保持一致，若请求报文中没有custid参数或值为空，则返回由梦网生成的代表本批短信的唯一编号
	// result非0时，custid为空
	String custid;
	
	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustid() {
		return this.custid;
	}
	// end


}
