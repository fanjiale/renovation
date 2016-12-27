package com.weixin.common.model.respone;

public class VideoOutputMessage extends OutputMessage{

	private static final long serialVersionUID = 1868067603682882740L;
	
	// 消息类型:视频消息
	private String MsgType = "video";
	
	private String MediaId;
	private String Title;
	private String Description;
		
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	@Override
	public String getMsgType() {
		return MsgType;
	}

}
