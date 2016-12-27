/**
 * 
 */
package com.andy.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 编码转换工具类
 * @author root
 * @createDt 2014-9-4 下午2:15:36
 */
public class CodeUtils {
	public static String decodeFromUtf8(String str) {
		try {
			str = java.net.URLDecoder.decode(str, "UTF-8");
			str = new String(str.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 如有中文，必须转换成utf-8
	 * @param oriStr
	 * @return
	 */
	public static String encodeToUtf8(String oriStr){
		try {
			oriStr = URLEncoder.encode(oriStr,  "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return oriStr;
	}
}
