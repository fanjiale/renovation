package com.weixin.common.model.request;

import java.util.Map;

public class UserRequest {
	// 发送方帐号（open_id）
	private String fromUserName;
	// 微信公众帐号
	private String toUserName;
	// 消息类型
	private String msgType;
	// 请求地址
	private String requestUrl;
	//消息ID
	private String msgId;
	// 消息时间
	private String createTime;
	//请求内容
	private Map<String, String> requestMap;
	//号码
	private String accNbr;
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getAccNbr() {
		return accNbr;
	}
	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}
	public Map<String, String> getRequestMap() {
		return requestMap;
	}
	public void setRequestMap(Map<String, String> requestMap) {
		this.requestMap = requestMap;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

}
