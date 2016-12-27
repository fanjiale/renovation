package com.weixin.common.model.mass.gson;



import java.io.Serializable;

/**
 * 群发时用到的视频素材
 * 
 * @author chanjarster
 */
public class MpMassVideo implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = -8450775046016801262L;
private String mediaId;
  private String title;
  private String description;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
}
