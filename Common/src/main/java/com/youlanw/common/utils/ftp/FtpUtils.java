package com.youlanw.common.utils.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);
	
	/**
	 * 服务器地址
	 */
	public static String FTP_HOST_SERVER;
	/**
	 * 服务器端口号
	 */
	public static Integer FTP_HOST_PORT;
	/**
	 * 登录帐号
	 */
	public static String FTP_LOGIN_NAME;
	/**
	 * 登录密码
	 */
	public static String FTP_LOGIN_PWD;
	
	public static Integer DATA_TIMEOUT = 10 * 1000;
	
	private FTPClient ftp;
	
	public FtpUtils(String hostServer,Integer hostPort, String loginName, String loginPwd, Integer dataTimeout) {
		FTP_HOST_SERVER = hostServer;
		FTP_HOST_PORT = hostPort;
		FTP_LOGIN_NAME = loginName;
		FTP_LOGIN_PWD = loginPwd;
		if(dataTimeout != null) {
			DATA_TIMEOUT = dataTimeout;
		}
		connectServer();
	}
	/**
	 * 
	 * <p>Title: connectServer</p>  
	 * Description: <pre>连接到FTP服务器</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @return true 连接服务器成功，false 连接服务器失败  
	 */
	private boolean connectServer() {
		boolean flag = true;
		ftp = new FTPClient();
		int reply;
		try {
			ftp.connect(FtpUtils.FTP_HOST_SERVER, FtpUtils.FTP_HOST_PORT);
			ftp.login(FtpUtils.FTP_LOGIN_NAME, FtpUtils.FTP_LOGIN_PWD);
			//返回最后一个FTP应答的响应码。(reply >= 200 && reply < 300)所有以2开头的都表示成功
			reply = ftp.getReplyCode();
			ftp.setDataTimeout(FtpUtils.DATA_TIMEOUT);
			//判断是否连接成功
			if(!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.debug("FTP 服务拒绝连接");
				flag = false;
			}
		} catch (SocketException e) {
			flag = false;
			e.printStackTrace();
			logger.debug("登录ftp服务器 " + FtpUtils.FTP_HOST_SERVER + " 失败,连接超时！");  
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
			logger.debug("登录ftp服务器 " + FtpUtils.FTP_HOST_SERVER + " 失败，FTP服务器无法打开！"); 
		}
		return flag;
	}
	
	/**
	 * 
	 * <p>Title: uploadFile</p>  
	 * Description: <pre>FTP二进制流方式上传文件（如果文件目录不存在则自动创建目录）</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param savePath 远程服务器上保存文件的路径
	 * @param fileName 保存文件的名称
	 * @param input 文件输入流对象
	 * @return boolean true成功|false失败
	 */
	public boolean uploadFile(String savePath, String fileName, InputStream input) {
		boolean flag = false;
		try {
			if(!ftp.makeDirectory(savePath)) {
				logger.debug("FTP 创建目录" + savePath + "失败");
			}
			if(!ftp.changeWorkingDirectory(savePath)) {
				logger.debug("FTP 切换到目录" + savePath + "失败");
			}
			//设置要传输的文件类型(BINARY_FILE_TYPE表示二进制文件类型)
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			flag = ftp.storeFile(fileName, input);
			ftp.logout();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	/**
	 * 
	 * <p>Title: changeWorkingDirectory</p>  
	 * Description: <pre>切换到指定目录下</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param directory
	 * @return
	 */
	public boolean changeWorkingDirectory(String directory) {
		boolean flag = true; 
		try {
			flag = ftp.changeWorkingDirectory(directory);
			if(flag) {
				logger.info("FTP 切换到" + directory + "成功");
			} else {
				logger.error("FTP 切换到" + directory + "失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 
	 * <p>Title: makeDirectory</p>  
	 * Description: <pre>在服务器上创建一个文件夹</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
	 * @return
	 */
	public boolean makeDirectory(String dir) {
		boolean flag = false;
		try {
			flag = ftp.makeDirectory(dir);
			if(flag) {
				logger.info("FTP 创建文件夹" + dir + "成功");
			} else {
				logger.error("FTP 创建文件夹" + dir + "失败");
			}
		} catch (IOException e) {
			logger.error("FTP 创建文件夹" + dir + "异常");
			e.printStackTrace();
		} 
		return flag;
	}
	/**
	 * 
	 * <p>Title: createDirecroty</p>  
	 * Description: <pre>创建多级目录</pre>  
	 * @author wangchaoqun 
	 * @date 2018年4月13日  
	 * @param remote
	 * @return
	 */
	public boolean createDirecroty(String remote) {
		boolean flag = true;
		String dir = remote.substring(0, remote.lastIndexOf("/") + 1);
		//如果远程目录不存在，则递归创建目录
		if(!dir.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(dir))) {
			int start = 0, end = 0;
			if(dir.startsWith("/")) {
				start = 1;
			}
			end = dir.indexOf("/",start);
			while(true) {
				//String subDirectory = new String(remote.substring(start, end).getBytes("GBK"),"iso-8859-1");  
				String subDirectory = new String(remote.substring(start, end));
				if(changeWorkingDirectory(subDirectory)) {
					if(makeDirectory(subDirectory)) {
						changeWorkingDirectory(subDirectory);
					} else {
						logger.error("创建目录["+subDirectory+"]失败");
						flag = false;  
                        return flag; 
					}
				}
				start = end + 1;
				end = dir.indexOf("/",start);
				// 检查所有目录是否创建完毕 
				if(end <= start) {
					break;
				}
			}
		}
		return flag;
	}
	
	

}
