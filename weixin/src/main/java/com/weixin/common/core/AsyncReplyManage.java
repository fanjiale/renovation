package com.weixin.common.core;

import com.weixin.common.model.request.UserRequest;

/**
 * 异步消息回复处理接口类
 * @author XMZ
 */
public interface AsyncReplyManage {

	/**
	 * 处理异步消息
	 * @param userRequest：用户请求消息对象
	 */
	public void dealAsyncMessage(UserRequest userRequest);
	
	/**
	 * 从队列中读取消息处理并发送
	 * @param userRequest：用户请求消息对象
	 */
	public void sendAsyncMessage(UserRequest userRequest);
}
