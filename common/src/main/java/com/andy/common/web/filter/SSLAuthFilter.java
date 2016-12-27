package com.andy.common.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.cert.X509Certificate;

public class SSLAuthFilter implements Filter {

	//是否有效
	private boolean isEffective = false;

	private FilterConfig filterConfig = null;
	
	
	private static final String ATTR_CER = "javax.servlet.request.X509Certificate";
    private static final String CONTENT_TYPE = "text/plain;charset=UTF-8";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String SCHEME_HTTPS = "https";

//	//过滤的URL中的关键
//	private String urlFilterKey = "console";
//	//跳转的目标URL
//	private String loginPage = "login";

	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
				
		String requestType = request.getHeader("X-Requested-With");
		
		
		if (isEffective) {
			String reqUri = request.getRequestURI();
			//首先判断如果是CSS JS及图像相关文件则不走过滤噄1�7
			boolean isisNeedFilterFile = isNeedFilterFile(reqUri);
			if (!isisNeedFilterFile) {
				chain.doFilter(request, response);
				return;
			}
			
			X509Certificate[] certs = (X509Certificate[]) request.getAttribute(ATTR_CER);
			if (certs != null) {
	            int count = certs.length;
//	            System.out.println("共检测到[" + count + "]个客户端证书");
	            for (int i = 0; i < count; i++) {
	            	X509Certificate tmp = certs[i];
//	            	System.out.println(tmp);
//	            	System.out.println("name = " + new String(tmp.getSignature()));
	            	System.out.println(tmp.getSignature());
	            	
//	            	System.out.println("客户端证书 [" + (++i) + "]： ");
//	            	System. out.println("校验结果：" + verifyCertificate(certs[--i]));
	            	System.out.println("证书详细：\r" + certs[i].toString());
	            }
	        } else {
	            if (SCHEME_HTTPS.equalsIgnoreCase(request.getScheme())) {
	            	System.out.println("这是一个HTTPS请求，但是没有可用的客户端证书");
	            } else {
	            	System.out.println("这不是一个HTTPS请求，因此无法获得客户端证书列表 ");
	            }
	        }
		}
		chain.doFilter(request, response);
	}
	
	
	private boolean verifyCertificate(X509Certificate certificate) {
        boolean valid = true;
        try {
            certificate.checkValidity();
        } catch (Exception e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }

	/**
	 * reqUI 请求URL
	 * @return
	 */
	public boolean isNeedFilterFile(String reqUI) {
//		if (reqUI.endsWith(".css") || reqUI.endsWith(".js") || reqUI.endsWith(".png") || reqUI.endsWith(".gif")
//				|| reqUI.endsWith(".jpg") || reqUI.endsWith(".jpeg") || reqUI.endsWith(".json")) {
//			return false;
//		}
//		if(reqUI.contains("selectDis")){
//			return false;
//		}
		return true;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
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
	}

}
