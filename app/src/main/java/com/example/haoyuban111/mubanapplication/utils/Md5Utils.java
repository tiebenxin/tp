package com.example.haoyuban111.mubanapplication.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	public static String encode(String val){
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(val.getBytes("UTF-8"));

			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] result = digest.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			if (Integer.toHexString(0xFF & result[i]).length() == 1)
				sb.append("0").append(Integer.toHexString(0xFF & result[i]));
			else
				sb.append(Integer.toHexString(0xFF & result[i]));
		}
		//16位加密，从第9位到25位
		return sb.toString().toLowerCase();
	}

}
