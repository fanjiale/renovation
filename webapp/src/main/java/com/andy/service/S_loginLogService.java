package com.andy.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fanjl on 2016/9/30.
 */
public interface S_loginLogService {
    /**
     * 记录用户登录
     *
     * @param user
     * @param request
     */
    void recordUserLoginLog(JSONObject user, HttpServletRequest request);
}
