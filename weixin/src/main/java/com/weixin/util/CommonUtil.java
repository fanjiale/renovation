package com.weixin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Node;

/**
 * 通用工具类
 * @author XMZ
 * @date：2015-6-18 
 */
public class CommonUtil {

	/**
	 * 节点解析 
	 * @param node
	 * @return String
	 */
	public static String getNodeValue(Node node) {
		if (node != null) {
			return node.getText();
		} else {
			return "";
		}
	}
	
	/**
	 * String非空判断 
	 * @param str
	 * @return boolean
	 */
	public static boolean getString(String str){
		if(str == null || str.equals("")){
			return false;
		}else{
			return true;
		}
	}
	
	
	/**
	 * 流水号组装，14位日期+8位序列，
	 * 日期格式：yyyyMMddHHmmss，
	 * 序列：00000001（8位，不足位补零）
	 * @param str
	 * @return String
	 */
	public static String getSequences(int num){
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return timeStamp + String.format("%08d", num);
	}
	
	/**
	 * 日期格式转换
	 * 原日期格式yyyyMMddHHmmss(String型)
	 * 新日期格式yyyy-MM-dd(String型)
	 * @param str
	 * @return String
	 */
	public static String changeStringDate(String str){
		String result = null;
		if(str != null && !str.equals("")){
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdfString = new SimpleDateFormat("yyyy-MM-dd");
			try {
				result = sdfString.format(sdfDate.parse(str.substring(0,8)));
			} catch (ParseException e) {
				e.printStackTrace();
				result = null;
			}
		}
		return result;
	}
	
}
