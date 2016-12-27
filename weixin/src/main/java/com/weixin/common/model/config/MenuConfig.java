package com.weixin.common.model.config;

import java.util.List;

/**
 * 菜单配置
 * @author H
 *
 */
public class MenuConfig {

	//key
	private String key;
	//名字
	private String name;
	//类型
	private String type;
	//地址url
	private String url;
	
	private String isOAuthPage;
	
	private List<MenuConfig> sub;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsOAuthPage() {
		return isOAuthPage;
	}

	public void setIsOAuthPage(String isOAuthPage) {
		this.isOAuthPage = isOAuthPage;
	}

	public List<MenuConfig> getSub() {
		return sub;
	}

	public void setSub(List<MenuConfig> sub) {
		this.sub = sub;
	}
}
