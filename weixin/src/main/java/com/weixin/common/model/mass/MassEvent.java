package com.weixin.common.model.mass;

import java.util.Date;

public class MassEvent {

	private Long massId;
	private String mediaId;
	private String massDesc;
	private int massStyle;
	private String statusCd;
	private Date createDate;

	private int type;//类型
	private String typeDesc;//类型
	private String msgId;//消息发送后得到
	private String massStyleDesc;//群发方式描述
	private String statusCdDesc;//状态描述
	private Date statusDate;//状态时间
	private String statusDates; //状态时间
	
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public Long getMassId() {
		return massId;
	}
	public void setMassId(Long massId) {
		this.massId = massId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getMassDesc() {
		return massDesc == null? "":massDesc;
	}
	public void setMassDesc(String massDesc) {
		this.massDesc = massDesc;
	}
	public int getMassStyle() {
		return massStyle;
	}
	public void setMassStyle(int massStyle) {
		this.massStyle = massStyle;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getMassStyleDesc() {
		return massStyleDesc;
	}
	public void setMassStyleDesc(String massStyleDesc) {
		this.massStyleDesc = massStyleDesc;
	}
	public String getStatusCdDesc() {
		return statusCdDesc;
	}
	public void setStatusCdDesc(String statusCdDesc) {
		this.statusCdDesc = statusCdDesc;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public String getStatusDates() {
		return statusDates;
	}
	public void setStatusDates(String statusDates) {
		this.statusDates = statusDates;
	}
	
}
