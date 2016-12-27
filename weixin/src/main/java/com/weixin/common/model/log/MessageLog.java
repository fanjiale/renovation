package com.weixin.common.model.log;

/**
 * 异步消息日志对象 
 */
public class MessageLog {

	private String logId; //日志ID，主键
	private String objectId; //对象ID
	private int objectType; //对象类型
	private String reqContent; //请求内容
	private String rspContent; //响应内容
	private String resultCode; //结果编码
	private String resultDesc; //结果描述
	private int logType; //日志类型
	
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public int getObjectType() {
		return objectType;
	}
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
	public String getReqContent() {
		return reqContent;
	}
	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}
	public String getRspContent() {
		return rspContent;
	}
	public void setRspContent(String rspContent) {
		this.rspContent = rspContent;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public int getLogType() {
		return logType;
	}
	public void setLogType(int logType) {
		this.logType = logType;
	}

}
