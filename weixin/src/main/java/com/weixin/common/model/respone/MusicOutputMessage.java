package com.weixin.common.model.respone;

public class MusicOutputMessage extends OutputMessage {

	private static final long serialVersionUID = -6524312831484069468L;

	// 消息类型:音乐消息
	private String MsgType = "music";
	// 音乐消息
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}

	@Override
	public String getMsgType() {
		// TODO Auto-generated method stub
		return MsgType;
	}
}
