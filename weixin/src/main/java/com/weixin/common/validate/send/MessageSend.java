package com.weixin.common.validate.send;

import java.text.MessageFormat;

/**
 * 验证短信发送(直接发短信平台)
 */
public class MessageSend {

	final static String sendContent = "业务公众号绑定号码验证码：{0}，5分钟内有效。如非本人操作，请忽略。";
	/**
	 * Webservice方式调用短信接口
	 * @param content 内容
	 * @param dest 目标号码
	 * @return
	 */
	public static String sendMsgWebservice(String vCode ,String dest,String url){
		//TODO 待实现
		String str = MessageFormat.format(sendContent,vCode);
		return "0";
	}
	
	/**
	 * Http方式发送调用短信接口
	 * @param content 内容
	 * @param desc 目标号码
	 * @return
	 */
	public static String sendMsgHttp(String vCode,String desc,String url){
		//TODO 待实现
		String str = MessageFormat.format(sendContent,vCode);
		return "0";
	}
}
