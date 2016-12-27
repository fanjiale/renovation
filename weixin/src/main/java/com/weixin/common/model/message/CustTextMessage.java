package com.weixin.common.model.message;

/**
 * 客服接口文本消息 
 */
public class CustTextMessage {
	
	private String touser;
	private String msgtype;
	private TextContent text;
	
	public class TextContent{
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public TextContent getText() {
		return text;
	}

	public void setText(TextContent text) {
		this.text = text;
	}
}
