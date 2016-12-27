package com.weixin.common.model.respone;

/*
 * 构造图文消息对象
 */
public class Article implements Cloneable{
	// 图文标题
	private String Title;
	// 图文描述
	private String Description;
	// 图片Url
	private String PicUrl;
	// Url连接
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return null == Description ? "" : Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return null == PicUrl ? "" : PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return null == Url ? "" : Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
	  public Object clone() {  
        Article o = null;  
        try {  
            o = (Article) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
	    }  
	  
}
