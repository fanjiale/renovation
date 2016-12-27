package com.andy.common.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

	//是否有效
	private boolean isEffective = false;

	private FilterConfig filterConfig = null;
	private ServletContext servletContext = null;

	//过滤的URL中的关键
	private String urlFilterKey = "console";
	//跳转的目标URL
	private String loginPage = "login";

	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
				
		String requestType = request.getHeader("X-Requested-With");
		Boolean isAjax = "XMLHttpRequest".equals(requestType);
		
		
		if (isEffective) {
			String reqUri = request.getRequestURI();
			//首先判断如果是CSS JS及图像相关文件则不走过滤噄1�7
			boolean isisNeedFilterFile = isNeedFilterFile(reqUri);
			if (!isisNeedFilterFile) {
				chain.doFilter(request, response);
				return;
			}
			
			String userId = (String) request.getSession().getAttribute("userId");

			if (reqUri.indexOf("login") != -1) {
				chain.doFilter(request, response);
				return;
			}
			//如果用户没登录且符合过滤器, 则重定向到登录页
			if (userId == null) {
				if(isAjax) {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				} else {
					response.sendRedirect(request.getContextPath() + loginPage);
				}
				return;
			}
			
		}
		chain.doFilter(request, response);
	}

	/**
	 * reqUI 请求URL
	 * @return
	 */
	public boolean isNeedFilterFile(String reqUI) {
		if (reqUI.endsWith(".css") || reqUI.endsWith(".js") || reqUI.endsWith(".png") || reqUI.endsWith(".gif")
				|| reqUI.endsWith(".jpg") || reqUI.endsWith(".jpeg") || reqUI.endsWith(".json")) {
			return false;
		}
		if(reqUI.contains("selectDis")){
			return false;
		}
		return true;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.servletContext = this.filterConfig.getServletContext();
		String strEffective = this.filterConfig.getInitParameter("isEffective");
		if (strEffective == null)
			this.isEffective = false;
		else if (strEffective.equalsIgnoreCase("true"))
			this.isEffective = true;
		else if (strEffective.equalsIgnoreCase("yes"))
			this.isEffective = true;
		else {
			this.isEffective = false;
		}
		String urlFilterKeyConfig = this.filterConfig.getInitParameter("urlFilterKey");
		if (urlFilterKeyConfig != null)
			this.urlFilterKey = urlFilterKeyConfig;

		String loginPageConffig = this.filterConfig.getInitParameter("loginPage");
		if (loginPageConffig != null)
			this.loginPage = loginPageConffig;
	}

}
