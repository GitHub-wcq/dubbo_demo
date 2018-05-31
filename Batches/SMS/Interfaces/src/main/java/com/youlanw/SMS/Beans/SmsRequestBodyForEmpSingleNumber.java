package com.youlanw.SMS.Beans;

import java.io.UnsupportedEncodingException;

import com.youlanw.common.utils.Uint32;
import com.youlanw.common.utils.encrypt.EncryptUtils;

/**
 * 
 * @author 王晓宇
 * 梦网 EMP 标准版API接口(V5.3.2)
 * 适用于 单条发送接口 
 */
public class SmsRequestBodyForEmpSingleNumber {

	// region 用户账号

	// 长度最大6个字符，统一大写，如提交参数中包含apikey，则可以不用填写该参数及pwd，两种鉴权方式中只能选择一种方式来进行鉴权
	// 示例：J10003
	String userid;

	public void setUserId(String userid) {
		this.userid = userid;
	}

	public String getUserId() {
		return this.userid;
	}
	// end

	// region 用户密码
	// 定长小写32位字符，如提交参数中包含apikey，则可以不用填写该参数及userid，两种鉴权方式中只能选择一种方式来进行鉴权。密码规则详见“3.1鉴权规则”
	// 示例：
	// 密码明文模式：111111
	// 密码加密模式：
	// 账号：J10003
	// 密码：111111
	// 固定字符串：00000000
	// 时间戳：0803192020
	// MD5加密之前的对应字符串： J10003000000001111110803192020
	// MD5加密之后的密码字符串： 26dad7f364507df18f3841cc9c4ff94d
	String pwd;

	public void setPassword(String password) {
		this.pwd = password;
	}

	public String getPassword() {
		return this.pwd;
	}
	// end

	// region 短信接收的手机号：只能填一个手机号。
	String mobile;

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	// end

	// region 短信内容：
	// 最大支持1000个字(含签名)，发送时请预留至少10个字的签名长度，一个字母或一个汉字都视为一个字
	// 编码方式： urlencode（GBK明文）
	String content;

	public void setContent(String content) throws Exception {
		if (content.length() >= 990)
			throw new Exception("custom exception: too many words");
		this.content = EncryptUtils.urlEncode(content, "GBK");
	}

	// isEncoder: 是否返回 urlEncoder 的结果
	public String getContent(Boolean isEncoder) throws UnsupportedEncodingException {
		if (!isEncoder)
			return EncryptUtils.urlDecode(this.content, "GBK");
		return this.content;
	}
	// end

	// region 时间戳
	// 24小时制格式：MMDDHHMMSS,即月日时分秒，定长10位,月、日、时、分、秒每段不足2位时左补0
	// 密码选择MD5加密方式时必填该参数，密码选择明文方式时则不用填写
	// 示例：0803192020
	Long timestamp;
	
	public void setTimestamp(int month, int day, int hour, int minute, int second) {
		String monthStr = "0" + month;
		monthStr = monthStr.substring(monthStr.length() - 2);
		
		String dayStr = "0" + day;
		dayStr = dayStr.substring(dayStr.length() - 2);
		
		String hourStr = "0" + hour;
		hourStr = hourStr.substring(hourStr.length() - 2);
		
		String minuteStr = "0" + minute;
		minuteStr = minuteStr.substring(minuteStr.length() - 2);
		
		String secondStr = "0" + second;
		secondStr = secondStr.substring(secondStr.length() - 2);

		this.timestamp = Long.parseLong(monthStr + dayStr + hourStr + minuteStr + secondStr);
	}

	public Long getTimestamp() {
		return this.timestamp;
	}
	// end

	// region 业务类型
	// 自定义类型，可用来分类。最大可支持32个长度的英文字母、数字组合的字符串
	String svrtype;
	
	public void setSvrtype(String svrtype) {
		this.svrtype = svrtype;
	}

	public String getSvrtype() {
		return this.svrtype;
	}
	// end
	
	// region 扩展号
	// 长度不能超过6位，注意通道号+扩展号的总长度不能超过20位，若超出则exno无效，如不需要扩展号则不用提交此字段或填空
	String exno;
	
	public void setExno(String exno) {
		this.exno = exno;
	}

	public String getExno() {
		return this.exno;
	}
	// end
	
	// region 扩展号
	// 长度不能超过6位，注意通道号+扩展号的总长度不能超过20位，若超出则exno无效，如不需要扩展号则不用提交此字段或填空
	String custid;
	
	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustid() {
		return this.custid;
	}
	// end
	
	// region 自定义扩展数据
	// 额外提供的最大64个长度的ASCII字符串：字母、数字、下划线、减号。
	// 作为自定义扩展数据，填写后，状态报告返回时将会包含这部分数据,如不需要则不用提交此字段或填空
	String exdata;
	
	public void setExdata(String exdata) {
		this.exdata = exdata;
	}

	public String getExdata() {
		return this.exdata;
	}
	// end	
	
	// region 用户自定义参数：最大64个字节
	String param1;
	
	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam1() {
		return this.param1;
	}
	// end	
	
	// region 用户自定义参数：最大64个字节
	String param2;
	
	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam2() {
		return this.param2;
	}
	// end	
	
	// region 用户自定义参数：最大64个字节
	String param3;
	
	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam3() {
		return this.param3;
	}
	// end	
	
	// region 用户自定义参数：最大64个字节
	String param4;
	
	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam4() {
		return this.param4;
	}
	// end	
	
	// region 模块ID
	// 4字节整型正数（0~2^31-1），0视为无效值，请填写非0值
	Uint32 moduleid;
	
	public void setModuleid(Uint32 moduleid) {
		this.moduleid = moduleid;
	}

	public Uint32 getModuleid() {
		return this.moduleid;
	}
	// end	
	
	// region 短信定时发送时间
	// 此处格式为：yyyyMMddHHmmss，即年月日时分秒，14位，24小时制计时。
	// 例如2012年12月31日16时59分0秒，即为20121231165900。
	// 小于或等于0或无该字段时视为不需定时。
	// 定时时间不能小于当前时间
	String attime;
	
	public void setAttime(String attime) {
		this.attime = attime;
	}

	public String getAttime() {
		return this.attime;
	}
	// end
	
	// region 短信有效存活时间（可选）
	// 此处格式为：yyyyMMddHHmmss，即年月日时分秒，14位，24小时制计时。
	// 例如2012年12月31日16时59分0秒，即为20121231165900
	// 小于或等于0或无该字段时按系统默认有效期（48小时）执行。
	// 存活时间不能小于当前时间
	String validtime;
	
	public void setValidtime(String validtime) {
		this.validtime = validtime;
	}

	public String getValidtime() {
		return this.validtime;
	}
	// end
	
	// region 是否需要状态报告
	// 0:表示不需要，非0:表示需要。该值若不填，系统默认为需要状态报告
	int rptflag;
	
	public void setRptflag(int rptflag) {
		this.rptflag = rptflag;
	}

	public int getRptflag() {
		return this.rptflag;
	}
	// end
}
