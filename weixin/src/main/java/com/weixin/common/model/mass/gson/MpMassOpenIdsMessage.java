package com.weixin.common.model.mass.gson;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * OpenId列表群发的消息
 * 
 * @author chanjarster
 */
public class MpMassOpenIdsMessage implements Serializable {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = -3721846505940465745L;
private List<String> toUsers = new ArrayList<String>();
  private String msgType;
  private String content;
  private String mediaId;

  public MpMassOpenIdsMessage() {
    super();
  }
  
  public String getMsgType() {
    return msgType;
  }

  /**
   * <pre>
   * 请使用
   * {@link me.chanjar.weixin.common.api.WxConsts#MASS_MSG_IMAGE}
   * {@link me.chanjar.weixin.common.api.WxConsts#MASS_MSG_NEWS}
   * {@link me.chanjar.weixin.common.api.WxConsts#MASS_MSG_TEXT}
   * {@link me.chanjar.weixin.common.api.WxConsts#MASS_MSG_VIDEO}
   * {@link me.chanjar.weixin.common.api.WxConsts#MASS_MSG_VOICE}
   * 如果msgtype和media_id不匹配的话，会返回系统繁忙的错误
   * </pre>
   * @param msgType
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String toJson() {
    return MpGsonBuilder.INSTANCE.create().toJson(this);
  }

  /**
   * OpenId列表，最多支持10,000个
   * @return
   */
  public List<String> getToUsers() {
    return toUsers;
  }

  public void setToUsers(List<String> toUsers) {
	this.toUsers = toUsers;
}

/**
   * 添加OpenId，最多支持10,000个
   * @param openId
   */
  public void addUser(String openId) {
    this.toUsers.add(openId);
  }
}
