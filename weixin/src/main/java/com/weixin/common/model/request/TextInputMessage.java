package com.weixin.common.model.request;

//文本消息
public class TextInputMessage extends InputMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
