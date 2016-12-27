package com.weixin.common.model.token;

import java.util.Date;

public class AccessToken {

	private Long id;
	// 获取到的凭证
	private String access_token;

	private Date last_date;

	// 凭证有效时间，单位：秒
	private double period;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Date getLast_date() {
		return last_date;
	}

	public void setLast_date(Date last_date) {
		this.last_date = last_date;
	}

	public double getPeriod() {
		return period;
	}

	public void setPeriod(double period) {
		this.period = period;
	}
}
