package com.weixin.common.core.interceptor;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.weixin.common.core.WxMessageDuplicateChecker;
import com.weixin.common.model.request.UserRequest;
import com.weixin.common.util.AppServerUtil;
import com.weixin.common.util.MessageUtil;

/**
 * 重复消息过滤器
 * @author H
 *
 */
public class DuplicateMsgInterceptor implements HandlerInterceptor {

	@Autowired
	WxMessageDuplicateChecker wxMessageDuplicateChecker;
	
	
	public WxMessageDuplicateChecker getWxMessageDuplicateChecker() {
		return wxMessageDuplicateChecker;
	}

	public void setWxMessageDuplicateChecker(
			WxMessageDuplicateChecker wxMessageDuplicateChecker) {
		this.wxMessageDuplicateChecker = wxMessageDuplicateChecker;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception err)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView mav) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		if(request.getMethod().equalsIgnoreCase("GET")){
			return true;
		}
		Thread.sleep(6000L);

		UserRequest ur = null;
		try {
			 ur = MessageUtil.parseRequest(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String msgId = "";
		if(ur.getMsgId() == null){
			msgId = ur.getFromUserName() + ur.getCreateTime();
		}else {
			msgId = ur.getMsgId();
		}
		boolean isd = wxMessageDuplicateChecker.isDuplicate(msgId);
		if(!isd){
			request.setAttribute("UR", ur);
			return true;
		}else{
			PrintWriter out = response.getWriter();
			out.print("success");
			out.close();
			return false;
		}
		
	}
	
}