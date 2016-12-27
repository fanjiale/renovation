package com.weixin.common.core;

import com.weixin.common.model.request.UserRequest;


/**
 * 事件处理类
 */
public interface SimpleEventManage {

	/**
	 * 默认请求事件处理
	 * @param ur 用户请求内容
	 * @return
	 */
	String dealRequestDefaultMsg(UserRequest ur);

	/**
	 * 处理上报地理位置事件
	 * @param ur
	 * @return
	 */
	String dealLocationReq(UserRequest ur);

	/**
	 * Click事件处理
	 * @param ur 用户请求内容
	 * @return
	 */
	String dealClickEvent(UserRequest ur);
	
	/**
	 * 订阅事件处理
	 * @param ur 用户请求内容
	 * @return
	 */
	String dealSubscribeEvent(UserRequest ur);
	
	/**
	 * 退订事件处理
	 * @param ur 用户请求内容
	 * @return
	 */
	String dealUnSubscribeEvent(UserRequest ur);

	/**
	 * 处理文本消息
	 * @param ur
	 * @return
	 */
	String dealTextReq(UserRequest ur);
	
}
