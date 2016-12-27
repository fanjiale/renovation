package com.andy.dao;

import com.alibaba.fastjson.JSONObject;
import com.skytech.ark.jdbc.IGenericDAO;

import java.util.List;

/**
 * Created by fanjl on 2016/9/30.
 */
public interface S_userDao extends IGenericDAO<JSONObject> {

    List<JSONObject> listUserByUserCode(String username);

    JSONObject list(int page, int count, String sort, String order, JSONObject condition);
}
