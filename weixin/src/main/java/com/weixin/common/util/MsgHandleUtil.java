package com.weixin.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/** 
 * 消息处理器的工具类. 
 * @version 0.5
 * @author 景伯文 
 */

public class MsgHandleUtil {

	/**
	 * 获取指定长度的字符串
	 * 以字符为单位做截取，避免了截取的最后一个字符为汉字情况下（刚好截取到半个汉字）的乱码问题。
	 * @param src 源字符串
	 * @param length 长度
	 * @return
	 */
	public static String getSubStr(String src, int length) {
		if (src == null || src.equals("")) {
			return null;
		}
		if (src.getBytes().length > length) {
			int k = 0;
			StringBuffer temp = new StringBuffer();
			for (int i = 0; i < src.length(); i++) {
				byte[] b = (String.valueOf(src.charAt(i))).getBytes();
				k = k + b.length;
				if (k > length) {
					break;
				}
				temp.append(src.charAt(i));
			}
			return temp.toString();
		} else {
			return src;
		}
	}

	/**
	 * 获取异常信息内容
	 * @param e 异常对象
	 * @param length 指定长度
	 * @return 返回异常信息内容
	 */
	public static String getExceptionString(Exception e, int length) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		String msg = os.toString();
		if (msg.getBytes().length > length) {
			msg = getSubStr(msg, length);
		}
		return msg;
	}

	/**
	 * 获取异常信息内容
	 * @param e 异常对象
	 * @return 返回异常信息内容
	 */
	public static String getExceptionString(Exception e) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		String msg = os.toString();
		return msg;
	}

	/**
	 * 获取异常信息内容
	 * @param e 异常对象
	 * @return 返回异常信息内容
	 */
	public static String getExceptionString(Throwable e) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		String msg = os.toString();
		return msg;
	}
}
