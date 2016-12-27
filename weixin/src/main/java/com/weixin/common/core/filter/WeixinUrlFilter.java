package com.weixin.common.core.filter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class WeixinUrlFilter implements Filter {


	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("WeixinUrlFilter启动成功!");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
    } 
    
	@Override
	public void destroy() {
	}
}