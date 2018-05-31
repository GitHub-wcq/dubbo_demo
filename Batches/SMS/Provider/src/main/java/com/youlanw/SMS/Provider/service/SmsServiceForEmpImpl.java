package com.youlanw.SMS.Provider.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.youlanw.SMS.Beans.SmsBody;
import com.youlanw.SMS.Interfaces.SmsService;


/**
 * 
 * <p>Title: SmsServiceImpl</p>  
 * Description: <pre>梦网短信发送的实现类</pre>   
 * @author Sean.Wang 
 * @date 2018年5月7日
 */
@Service("SmsService")
public class SmsServiceForEmpImpl implements SmsService {

	public SmsServiceForEmpImpl( ) {}
	
	/*
	 * <p>Title: getSingleSmsFromQueue</p> Description: <pre>从队列中取出最新一条待发送的短信</pre>
	 * 
	 * @author Sean.Wang
	 * 
	 * @date 2018-05-05
	 * 
	 * @return String : 最新一条待发送的短信内容
	 */
	@Override
	public Boolean pushSmsIntoQueue(String... smsBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getSmsListFromQueue(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSingleSmsFromQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean canSend(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <R, B> R sendSms(SmsBody<B> smsBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <B> Boolean afterSuccess(SmsBody<B> smsBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <B> Boolean afterFailure(SmsBody<B> smsBody) {
		// TODO Auto-generated method stub
		return null;
	}



}
