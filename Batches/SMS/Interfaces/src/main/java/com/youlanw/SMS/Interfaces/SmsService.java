package com.youlanw.SMS.Interfaces;

import java.util.List;

import com.youlanw.SMS.Beans.SmsBody;

/*
 * <p>Title: SmsService</p>  
 * Description: <pre>短信发送处理类的接口定义</pre>  
 * @author Sean.Wang 
 * @date 2018-05-05
 */
public interface SmsService {
	
	/*
	 * <p>Title: pushSmsIntoQueue</p> Description: <pre>向队列中添加短信数据</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-05
	 * 
	 * @param String... : 零条、一条或多条数据内容
	 * 
	 * @return Boolean : 添加成功返回 true
	 */
	 Boolean pushSmsIntoQueue(String... smsBody);
	
	/*
	 * <p>Title: getSmsListFromQueue</p> Description: <pre>从队列中取出的待发送的短信列表</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-05
	 * 
	 * @param int : 一次获取的条数
	 * 
	 * @return List<String> : 待发送的短信内容列表
	 */
	List<String> getSmsListFromQueue(int count);
	
	/*
	 * <p>Title: getSingleSmsFromQueue</p> Description: <pre>从队列中取出最新一条待发送的短信</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-05
	 * 
	 * @return String : 最新一条待发送的短信内容
	 */
	String getSingleSmsFromQueue();

	/*
	 * <p>Title: canSend</p> Description: <pre>判断当前手机号码是否允许被发送短信</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-05
	 * 
	 * @param String : 待判断的手机号码
	 * 
	 * @return Boolean : 允许发送返回 true
	 */
	Boolean canSend(String phoneNumber);

	/*
	 * <p>Title: sendSms</p> Description: <pre>发送短信</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-05
	 * 
	 * @param SmsBody<B> : 不同供应商定义的待发送的短信内容结构不同
	 *
	 * @return R : 不同供应商返回的响应内容
	 * 
	 * @remark : 此接口仅定义了一个发送方法，如果有多个方法，则在实现类中自己添加
	 */
	<R, B> R sendSms(SmsBody<B> smsBody);

	/*
	 * <p>Title: afterSuccess</p> Description: <pre>发送成功后的操作</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-05
	 * 
	 * @param SmsBody<B> : 成功发送的短信内容
	 *
	 * @return Boolean : 操作成功返回 true
	 */
	<B> Boolean afterSuccess(SmsBody<B> smsBody);
	
	/*
	 * <p>Title: afterFailure</p> Description: <pre>发送失败后的操作</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-05
	 * 
	 * @param SmsBody<B> : 发送失败的短信内容
	 *
	 * @return Boolean : 操作成功返回 true
	 */
	<B> Boolean afterFailure(SmsBody<B> smsBody);
	
}
