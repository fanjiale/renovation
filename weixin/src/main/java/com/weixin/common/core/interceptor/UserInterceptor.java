package com.weixin.common.core.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.weixin.common.user.smo.UserBoundSMO;

/**
 * 用户拦截器 
 */
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	UserBoundSMO userBoundSMO;
	public UserBoundSMO getUserBoundSMO() {
		return userBoundSMO;
	}

	public void setUserBoundSMO(UserBoundSMO userBoundSMO) {
		this.userBoundSMO = userBoundSMO;
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
		return true;
	}
}
