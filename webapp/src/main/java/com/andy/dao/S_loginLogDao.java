package com.andy.dao;

import com.alibaba.fastjson.JSONObject;
import com.skytech.ark.jdbc.IGenericDAO;

import java.util.List;

/**
 * Created by Andy on 2016/11/4.
 * 用户登录日志
 */
public interface S_loginLogDao extends IGenericDAO<JSONObject> {

    List<JSONObject> listUserByUserName(String username);
}
