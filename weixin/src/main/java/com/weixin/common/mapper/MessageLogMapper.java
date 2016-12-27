package com.weixin.common.mapper;

import com.weixin.common.model.log.MessageLog;

/**
 * 异步消息记录日志 
 */
public interface MessageLogMapper {
	
	/**
	 * 查询日志ID 
	 */
	public String getMessageLogId();

	/**
	 * 写入日志表
	 * @param asyncMessageLog ： 异步消息日志对象
	 * @author XMZ
	 */
	public void insertMessageLog(MessageLog messageLog);
}
