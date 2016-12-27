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
 * 用户号码参数过滤器
 * @author H
 *
 */
public class UserAccNbrFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("ACCNBR过滤器启动成功!");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession hs = request.getSession();
		if(hs.getAttribute(CommonConstants.SESSION_ATTR_ACCNBR) != null){
			chain.doFilter(req, res);
		}else{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.sendRedirect(request.getContextPath() + "/common/jsp/Error_Code.jsp?errcode=" + CommonConstants.Error_Param_Code
					+"&errmsg=" + CommonConstants.Error_Param_Msg);
		}
		
    } 
    
	@Override
	public void destroy() {

	}
}