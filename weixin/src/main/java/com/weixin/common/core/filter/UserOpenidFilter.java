package com.weixin.common.core.filter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.weixin.common.util.CommonConstants;

/**
 * openid参数过滤器
 * @author H
 *
 */
public class UserOpenidFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("OPENID过滤器启动成功!");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession hs = request.getSession();
		if(hs.getAttribute(CommonConstants.SESSION_ATTR_OPENID) != null
				&& hs.getAttribute(CommonConstants.SESSION_ATTR_OPENID).toString().length() > 0){
			chain.doFilter(req, res);
		}else{
			response.sendRedirect(request.getContextPath() + "/common/jsp/Error_Code.jsp?errcode=" + CommonConstants.Error_Param_Code);
		}
		
    } 
    
	@Override
	public void destroy() {

	}
}