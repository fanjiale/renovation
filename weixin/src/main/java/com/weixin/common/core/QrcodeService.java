package com.weixin.common.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 二维码获取接口类 
 */
public interface QrcodeService {

	/**
	 * 获取二维码 
	 */
	public String getQrTicket(String qrLimit, String sceneId);
	
	/**
	 * 下载二维码 
	 */
	public void download(HttpServletRequest req,HttpServletResponse res);
}
