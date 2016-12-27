package com.weixin.common.model.config;

import java.util.List;

import com.weixin.common.model.respone.Article;

/**
 * 消息回复配置
 * @author H
 *
 */
public class ReplyConfig {

	//事件类型
	private String eventType;
	//事件key
	private String key;
	//同步异步
	private String replyMode;
	//异步Url
	private String asyncUrl;
	//消息类型
	private String msgType;
	//消息内容
	private String msgContent;
	//图文消息
	private List<Article> articles;
	
	public String getAsyncUrl() {
		return asyncUrl;
	}

	public void setAsyncUrl(String asyncUrl) {
		this.asyncUrl = asyncUrl;
	}

	public String getReplyMode() {
		return replyMode;
	}

	public void setReplyMode(String replyMode) {
		this.replyMode = replyMode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
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

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
