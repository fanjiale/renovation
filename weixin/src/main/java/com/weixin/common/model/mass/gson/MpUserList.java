package com.weixin.common.model.mass.gson;

import java.util.List;

public class MpUserList {
	private Object total;
	private Object count;
	private Object nextOpenId;
	private List<Object> openIds;
	
	public List<Object> getOpenIds() {
		return openIds;
	}
	public void setOpenIds(List<Object> openIds) {
		this.openIds = openIds;
	}
	public Object getTotal() {
		return total;
	}
	public void setTotal(Object total) {
		this.total = total;
	}
	public Object getCount() {
		return count;
	}
	public void setCount(Object count) {
		this.count = count;
	}
	public Object getNextOpenId() {
		return nextOpenId;
	}
	public void setNextOpenId(Object nextOpenId) {
		this.nextOpenId = nextOpenId;
	}
	
}
