package com.andy.common.exception;

import com.alibaba.fastjson.JSON;
import com.andy.common.domains.RspResult;
import com.andy.common.domains.RspResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 系统基础异常处理
 */
public class SysMappingExceptionResolver extends SimpleMappingExceptionResolver {

	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (viewName != null) {// JSP格式返回
			if (!(request.getHeader("accept").contains("application/json") || (request
					.getHeader("X-Requested-With") != null && request
					.getHeader("X-Requested-With").contains("XMLHttpRequest")))) {
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {
				// 如果是ajax调用的话
				try {
					PrintWriter writer = response.getWriter();
					writer.write(JSON.toJSONString(RspResult
							.getFailtResult(ex.getMessage())));
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		} else {
			return null;
		}
	}
}