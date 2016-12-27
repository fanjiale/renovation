package com.weixin.common.core;

import javax.servlet.http.HttpServletRequest;

/**
 * 核心处理服务
 * 
 * @author fukai
 * @date 2015-06-01
 */
public interface CoreService {

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public  String processRequest(HttpServletRequest request);

}
