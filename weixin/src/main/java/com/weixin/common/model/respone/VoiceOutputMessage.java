package com.weixin.common.model.respone;

public class VoiceOutputMessage extends OutputMessage{

	private static final long serialVersionUID = 2487504220348188804L;

	// 消息类型:语音消息
	private String MsgType = "voice";
	
	private String MediaId;
	
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	@Override
	public String getMsgType() {
		return MsgType;
	}
}
