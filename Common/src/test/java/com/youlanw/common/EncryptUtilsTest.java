package com.youlanw.common;

import org.junit.Test;

import com.youlanw.common.utils.encrypt.EncryptUtils;

public class EncryptUtilsTest {

	@Test
	public void toMD5Str() {
		String str1 = EncryptUtils.toMD5Str("tgadgsdfgadhadghs大祭司交给上帝就da");
		System.out.println(str1);
		String str2 = EncryptUtils.toSHAStr("tgadgsdfgadhadghs大祭司交给上帝就da");
		System.out.println(str2);
	}
	
}
