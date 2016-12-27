package com.andy.common.utils;

public class StringUtil {
	/**
	 * 判断字符串是否为null或者空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}

	/**
	 * 字符串替换
	 * @param text 原始字符串
	 * @param repl 想被替换的内容
	 * @param with 替换后的内容
	 * @return
	 */
	public static String replace(String text, String repl, String with) {
		if (text == null || repl == null || with == null || repl.length() == 0) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int searchFrom = 0;
		while (true) {
			int foundAt = text.indexOf(repl, searchFrom);
			if (foundAt == -1) {
				break;
			}

			buf.append(text.substring(searchFrom, foundAt)).append(with);
			searchFrom = foundAt + repl.length();
		}
		buf.append(text.substring(searchFrom));

		return buf.toString();
	}

	/**
	 * 获取指定长度(按字节长度获取)的字符串，中文算2个字节长度，兼容oracle的
	 * varchar2长度计算方式
	 * @param src 源字符串
	 * @param length 长度
	 * @return
	 */
	public static String getSubStr(String src, int length) {
		if (StringUtil.isEmpty(src)) {
			return null;
		}
		byte[] b = src.getBytes();
		if (b.length > length) {
			byte[] s = new byte[length];
			for (int i = 0; i < length; i++) {
				s[i] = b[i];
			}
			return new String(s);
		} else {
			return src;
		}
	}
}
