package com.weixin.common.model.config;

/**
 * 验证码参数配置
 * @author H
 *
 */
public class ValidateMessageConfig {

	private int type;
	private int ExpTime;
	private int limit;
	private int limitTime;
	private String sendMode;
	private String url;
	
	public String getSendMode() {
		return sendMode;
	}
	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getExpTime() {
		return ExpTime;
	}
	public void setExpTime(int expTime) {
		ExpTime = expTime;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
