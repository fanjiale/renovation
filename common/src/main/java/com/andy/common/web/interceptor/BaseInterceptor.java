package com.andy.common.web.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器,增加
 * @author root
 * @createDt 2014-9-2 下午12:13:31
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
	
	
	/**
	 * 请求预处理
	 * 编码、安全控制等处理
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (request.getAttribute("ctx") == null) {
            request.setAttribute("ctx", request.getContextPath());
        }
		return true;
	}
	
	
	/**
	 * 在afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录。 
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

}
