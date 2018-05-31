package com.youlanw.cms.utils;

import com.youlanw.util.cms.CmsConstants;

/**
 * 常量
 * 
 * @author Nicolas.Cai @since 2015 下午5:08:12
 */
public class Constants extends CmsConstants{
	/**
	 * 上传路径
	 */
	public static final String FTP_URL = "121.40.134.113";
	/**
	 * 上传端口
	 */
	public static final int FTP_PORT = 2121;
	/**
	 * 上传用户名
	 */
	public static final String FTP_USERNAME = "app_user";
	/**
	 * 上传用户密码
	 */
	public static final String FTP_PASSWORD = "app_user_20151231";

	/***
	 * 水印图片[已被项目注释，目前不加水印]
	 * TODO 目前不加水印
	 */
    public static final String IMAGE_ICON_PATH = Constants.class.getClassLoader().getResource("").getPath() + "ylicon.png";

//	public static final String BAIDU_MAP_API_IP = "http://api.map.baidu.com/location/ip?ak=afw0IrSaTC3IseepG7RxeyGz";
	
}
