package com.weixin.common.model.config;

import java.util.List;

import com.weixin.common.model.message.CustArticle;

/**
 * 客服接口 
 */
public class CustServiceConfig {

	//事件key
	private String key;
	//消息类型
	private String msgType;
	//消息内容
	private String msgContent;
	//图文消息
	private List<CustArticle> articles;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public List<CustArticle> getArticles() {
		return articles;
	}
	public void setArticles(List<CustArticle> articles) {
		this.articles = articles;
	}
		
}
