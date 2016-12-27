package com.weixin.common.model.respone;

public class ImageOutputMessage extends OutputMessage {

	private static final long serialVersionUID = -6524312831484069468L;

	// 消息类型:音乐消息
	private String MsgType = "image";
	// 图片消息
	private Image image;
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String getMsgType() {
		return MsgType;
	}
}
