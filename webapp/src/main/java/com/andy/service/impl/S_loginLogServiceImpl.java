package com.andy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.andy.dao.S_loginLogDao;
import com.andy.dao.S_userDao;
import com.andy.service.S_loginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by fanjl on 2016/9/30.
 */
@Service
public class S_loginLogServiceImpl implements S_loginLogService {

    @Autowired
    private S_loginLogDao loginLogDao;

    @Autowired
    private S_userDao userDao;

    @Override
    public void recordUserLoginLog(JSONObject user, HttpServletRequest request) {
        String ip;
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }

        JSONObject loginLog = new JSONObject();
        loginLog.put("user_name", user.getString("user_name"));
        loginLog.put("login_time", new Date());
        loginLog.put("ip_address", ip);

        loginLogDao.save(loginLog);

        user.put("last_access_time", new Date());
        userDao.update(user);
    }


}
