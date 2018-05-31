package com.youlanw.SMS.Beans;

import java.util.Date;

/*
 * <p>Title: SmsBody</p>  
 * Description: <pre>短信内容结构定义</pre>  
 * @author Sean.Wang 
 * @date 2018-05-05
 * @param T : 不同的供应商要求的不同消息内容结构
 */
public class SmsBody<T> {

	// region 短信编号，为了通用，定义为 String
	String id;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
	// end

	// region 供应商代码，为了通用，定义为 String
	String channelCode;

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelCode() {
		return this.channelCode;
	}
	// end

	// region 业务逻辑分类（大类），为了通用，定义为 String
	String businessType;

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessType() {
		return this.businessType;
	}
	// end

	// region 业务逻辑分类（小类），为了通用，定义为 String
	String subBusinessType;

	public void setSubBusinessType(String subBusinessType) {
		this.subBusinessType = subBusinessType;
	}

	public String getSubBusinessType() {
		return this.subBusinessType;
	}
	// end

	// region 发送优先级。数字越大，优先级越高。默认值 0。
	byte priority = 0;

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public byte getPriority() {
		return this.priority;
	}
	// end
	
	// region 失败时，需要重新发送的次数。由业务层定义。 0:不需要重新发送，默认值为0。
	Byte mustReSend = 0;

	public void setMustReSend(Byte mustReSend) {
		this.mustReSend = mustReSend;
	}

	public Byte mustReSend() {
		return this.mustReSend;
	}
	// end

	// region 失败后，如果MustReSend设置为大于0，则此字段统计尝试重新发送的次数。默认值为0。
	Byte reTryCount = 0;

	public void setReTryCount() {
		this.reTryCount++;
	}

	public Byte getReTryCount() {
		return this.reTryCount;
	}
	// end
	
	// region 进入队列的时间
	Date enterQueueTime;

	public void setEnterQueueTime(Date enterQueueTime) {
		if (enterQueueTime == null)
			enterQueueTime = new Date();
		this.enterQueueTime = enterQueueTime;
	}

	public Date getEnterQueueTime() {
		return this.enterQueueTime;
	}
	// end

	// region 从队列中取出，准备处理的时间
	Date prepareTime;

	public void setPrepareTime(Date prepareTime) {
		if (prepareTime == null)
			prepareTime = new Date();
		this.prepareTime = prepareTime;
	}

	public Date getPrepareTime() {
		return this.prepareTime;
	}
	// end

	// region 完成发送的时间
	Date sentTime;

	public void setSentTime(Date sentTime) {
		if (sentTime == null)
			sentTime = new Date();
		this.sentTime = sentTime;
	}

	public Date getSentTime() {
		return this.sentTime;
	}
	// end

	// region 收到供应商回调响应的时间
	Date responseTime;

	public void setResponseTime(Date responseTime) {
		if (responseTime == null)
			responseTime = new Date();
		this.responseTime = responseTime;
	}

	public Date getResponseTime() {
		return this.responseTime;
	}
	// end

	// region 短信内容 T
	T body;

	public void setBody(T body) {
		this.body = body;
	}

	public T getBody() {
		return this.body;
	}
	// end

	// region 备用扩展字段1，为了通用，定义为 String
	String extendField1;

	public void setExtendField1(String extendField1) {
		this.extendField1 = extendField1;
	}

	public String getExtendField1() {
		return this.extendField1;
	}
	// end

	// region 备用扩展字段2，为了通用，定义为 String
	String extendField2;

	public void setExtendField2(String extendField2) {
		this.extendField2 = extendField2;
	}

	public String getExtendField2() {
		return this.extendField2;
	}
	// end
}
