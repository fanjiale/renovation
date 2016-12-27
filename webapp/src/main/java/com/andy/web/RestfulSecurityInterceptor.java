package com.andy.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fanjl on 2016/9/27.
 */
public class RestfulSecurityInterceptor implements HandlerInterceptor {

    private String[] whiteList;
    private String login_url;
    private String logout_url;

    private static final String BEST_MATCH_PATTERN = "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern";
    private static final String LOGIN_ACCOUNT_SESSIONKEY = "AuthenticatedAccount";

    private static final Logger logger = LoggerFactory.getLogger(RestfulSecurityInterceptor.class);


    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        logger.debug(httpServletRequest.getRequestURI());
        String servletPath = httpServletRequest.getServletPath().replaceFirst("/", "");

        httpServletResponse.addHeader("Cache-Control", "no-cache");
        httpServletResponse.addHeader("Pragma", "no-cache");
        httpServletResponse.addDateHeader("Expires", 1L);

        for (String whitePath : whiteList) {
            if (servletPath.equals(whitePath)) {
                return true;
            }
        }

       /* String bestMatchPattern = httpServletRequest.getAttribute(BEST_MATCH_PATTERN).toString();
        logger.debug("[BEST MATCH] " + bestMatchPattern);

        //set current logined user account to thread
        String account = (String) httpServletRequest.getSession(true).getAttribute(LOGIN_ACCOUNT_SESSIONKEY);
        if (account == null) {
            //验证用户登录
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
            return false;
        }*/

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        /*String servletPath = httpServletRequest.getServletPath().replaceFirst("/","");

        if(this.login_url.equals(servletPath)){

            String username = (String) httpServletRequest.getAttribute("username");

        }
        if(this.logout_url.equals(servletPath)){
            httpServletRequest.getSession().removeAttribute("username");
        }*/

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setWhiteList(String[] whiteList) {
        this.whiteList = whiteList;
    }

    public void setLogin_url(String login_url) {
        this.login_url = login_url;
    }

    public void setLogout_url(String logout_url) {
        this.logout_url = logout_url;
    }
}
