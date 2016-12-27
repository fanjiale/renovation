package com.weixin.common.model.config;
/**
 * 微信基础配置
 * @author H
 *
 */
public class BaseConfig {

	//微信公众号Taken
	private String taken;
	//微信公众号appid
	private String appid;
	//微信公众号secret
	private String secret;

	public String getTaken() {
		return taken;
	}

	public void setTaken(String taken) {
		this.taken = taken;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
