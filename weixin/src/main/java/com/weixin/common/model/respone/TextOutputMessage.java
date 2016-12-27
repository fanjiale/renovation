package com.weixin.common.model.respone;

public class TextOutputMessage extends OutputMessage {

	private static final long serialVersionUID = 7191176352844291435L;
	// 消息类型：文本消息
	private String MsgType = "text";
	// 消息类容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String getMsgType() {
		// TODO Auto-generated method stub
		return MsgType;
	}
}