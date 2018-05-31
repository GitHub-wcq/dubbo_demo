package com.youlanw.cms.utils;


import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * ftp文件上传.
 * @author Nicolas.Cai
 * @since 2015年11月12日 上午11:14:05
 */
public class FtpUtil {
	
	private static Logger logger =  LoggerFactory.getLogger(FtpUtil.class);
	/**
	 * ftp上传
	 * @param url ftp服务器hostname
	 * @param port ftp服务器端口
	 * @param username ftp服务器帐号
	 * @param password ftp服务器密码
	 * @param path ftp服务器保存目录
	 * @param filename 保存到ftp服务器文件名
	 * @param input 输入流
	 * @return 成功返回true,否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.setDefaultPort(port);
			ftp.setControlEncoding("UTF-8");
			ftp.connect(url, port);// 连接FTP服务器
			ftp.login(username, password);// 登录
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			isDirExist(ftp,  "/"+path,username);
			ftp.changeWorkingDirectory(path);
			ftp.enterLocalPassiveMode();
 			boolean ts = ftp.storeFile(filename, input);
			if(ts){
				input.close();
				ftp.logout();
			}
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return success;
	}
	/** 判断Ftp目录是否存在,如果不存在则创建目录 */
	public static void isDirExist(FTPClient ftpClient, String dir,String username) {
		try {
//			ftpClient.makeDirectory(new String(dir.getBytes("UTF-8"),"iso-8859-1"));
			dir = dir.substring(1);
			String[] dirChar = dir.split("/");
			if(StringUtils.isNotBlank(username)){
				StringBuffer sbDir = null;
				if(username.equals("app_user")){
//					sbDir = new StringBuffer(Constants.APP_FTP_USER_HOME_DIR);
					sbDir = new StringBuffer("/");
				}else{
					sbDir = new StringBuffer(Constants.YL_FTP_USER_HOME_DIR);
				}
				for (String dirC : dirChar) {
					sbDir = sbDir.append(dirC).append("/");
					ftpClient.makeDirectory(sbDir.toString());
				}
			}
		} catch (IOException e1) {
			logger.info("创建ftp目录异常"+dir+"目录已存在！");
		}
	}
	
}
