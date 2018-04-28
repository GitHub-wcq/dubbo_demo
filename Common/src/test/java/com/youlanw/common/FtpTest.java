package com.youlanw.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.youlanw.common.utils.ftp.FtpUtils;

public class FtpTest {
	
//	@Test
//	public void test001() {
//		FtpUtils ftp = new FtpUtils("192.168.80.133", 21, "ftpuser", "ftpuser123", null);
//		boolean flag = ftp.makeDirectory("/home/ftpuser/test");
//		System.out.println(flag);
//	}
//	@Test
//	public void test002() {
//		File file = new File("C:/Users/wcq/Desktop/hash.txt");
//		InputStream in = null;
//		try {
//			in = new FileInputStream(file);
//			System.out.println("读取到流");
//			FtpUtils ftp = new FtpUtils("192.168.80.133", 21, "ftpuser", "ftpuser123", null);
//			System.out.println("连接成功");
//			boolean flag = ftp.uploadFile("/home/ftpuser/test", "hash.txt", in);
//			System.out.println(flag);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(in != null) {
//			try {
//				in.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
