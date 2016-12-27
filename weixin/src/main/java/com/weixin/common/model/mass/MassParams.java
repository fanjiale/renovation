package com.weixin.common.model.mass;

public class MassParams {
	private Long massId;
	private String massDesc;
	private int massStyle;
	private Long materialId;
	private int materialType;
	private String localMediaPath;
	private String htmlFilePath;
	private String author;
	private String contentSourceUrl;
	private String title;
	private String content;
	private String createFileDate;
	private String massIdsStr;
	private String accNbr;
	
	public String getAccNbr() {
		return accNbr;
	}
	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}
	public String getMassIdsStr() {
		return massIdsStr;
	}
	public void setMassIdsStr(String massIdsStr) {
		this.massIdsStr = massIdsStr;
	}
	public Long getMassId() {
		return massId;
	}
	public void setMassId(Long massId) {
		this.massId = massId;
	}
	public String getMassDesc() {
		return massDesc == null ? "":massDesc;
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
	public String getLocalMediaPath() {
		return localMediaPath == null ? "":localMediaPath;
	}
	public void setLocalMediaPath(String localMediaPath) {
		this.localMediaPath = localMediaPath;
	}
	public String getHtmlFilePath() {
		return htmlFilePath == null ? "":htmlFilePath;
	}
	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}
	public String getAuthor() {
		return author == null ? "":author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContentSourceUrl() {
		return contentSourceUrl == null ? "":contentSourceUrl;
	}
	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}
	public String getTitle() {
		return title == null ? "":title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content == null ? "":content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateFileDate() {
		return createFileDate;
	}
	public void setCreateFileDate(String createFileDate) {
		this.createFileDate = createFileDate;
	}
	
}
