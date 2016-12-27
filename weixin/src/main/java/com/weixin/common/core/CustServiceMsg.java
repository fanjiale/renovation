package com.weixin.common.core;

import java.util.Map;

/**
 * 客服接口 
 */
public interface CustServiceMsg {
	
	/**
	 * 发送客服消息 
	 * @param id：客户消息的key
	 * @param openId
	 */
	public Map<String,Object> sendCustServiceMsg(String id, String openId);
	
	
	/**
	 * 发送客服消息
	 * @param message：发送的消息内容
	 */
	public Map<String,Object> sendCustServiceMsg(String message);


	/**
	 * 发送客服消息
	 * @param map：发送的消息内容
	 */
	public Map<String,Object> sendCustServiceMsg(Map<String,Object> map);
}
