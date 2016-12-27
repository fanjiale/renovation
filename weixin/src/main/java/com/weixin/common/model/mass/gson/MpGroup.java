package com.weixin.common.model.mass.gson;



import java.io.Serializable;

/**
 * 微信用户分组
 * @author chanjarster
 *
 */
public class MpGroup implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 8187500884751537745L;
private long id = -1;
  private String name;
  private long count;
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public long getCount() {
    return count;
  }
  public void setCount(long count) {
    this.count = count;
  }
  
  public static MpGroup fromJson(String json) {
    return MpGsonBuilder.create().fromJson(json, MpGroup.class);
  }
  
  public String toJson() {
    return MpGsonBuilder.create().toJson(this);
  }
  @Override
  public String toString() {
    return "WxMpGroup [id=" + id + ", name=" + name + ", count=" + count + "]";
  }
  
}
