package com.weixin.common.model.mass;

import java.util.Date;

public class MaterialContent {

	private Long materialId;
	private int materialType;
	private String materialTypeDesc;
	private String mediaId;
	private String localMediaPath;
	private String htmlFilePath;
	private String author;
	private String contentSourceUrl;
	private String title;
	private String content;
	private Date createDate;
	private Date updateDate;
	private String createFileDate;//用于文件夹名
	
	
	public String getMaterialTypeDesc() {
		return materialTypeDesc;
	}
	public void setMaterialTypeDesc(String materialTypeDesc) {
		this.materialTypeDesc = materialTypeDesc;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public int getMaterialType() {
		return materialType;
	}
	public void setMaterialType(int materialType) {
		this.materialType = materialType;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getLocalMediaPath() {
		return localMediaPath == null?"":localMediaPath;
	}
	public void setLocalMediaPath(String localMediaPath) {
		this.localMediaPath = localMediaPath;
	}
	public String getHtmlFilePath() {
		return htmlFilePath == null?"":htmlFilePath;
	}
	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}
	public String getAuthor() {
		return author == null?"":author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContentSourceUrl() {
		return contentSourceUrl == null?"":contentSourceUrl;
	}
	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}
	public String getTitle() {
		return title == null?"":title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content == null?"":content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateFileDate() {
		return createFileDate;
	}
	public void setCreateFileDate(String createFileDate) {
		this.createFileDate = createFileDate;
	}
	
}
