package com.andy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.andy.dao.S_userDao;
import com.andy.service.S_userService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by fanjl on 2016/9/30.
 */
@Service
public class S_serServiceImpl implements S_userService {

    @Autowired
    private S_userDao userDao;

    @Override
    public boolean validateUserInfo(String user_code, String password) {
        List<JSONObject> users = userDao.listUserByUserCode(user_code);

        if (!users.isEmpty()) {
            JSONObject user = users.get(0);

            String ps = user.getString("password");

            if (DigestUtils.md5Hex(password).endsWith(ps)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject findUserByUserCode(String user_code) {
        List<JSONObject> users = userDao.listUserByUserCode(user_code);

        if (!users.isEmpty()) {
            return users.get(0);
        }

        return new JSONObject();
    }

    @Override
    public String createUser(String user_code, String user_name, String password) {
        JSONObject condition = new JSONObject();
        condition.put("user_code", user_code);
        condition.put("user_name", user_name);
        condition.put("password", DigestUtils.md5Hex(password));
        condition.put("create_time", new Date());
        condition.put("update_time", new Date());

        return userDao.save(condition);
    }

    @Override
    public JSONObject list(JSONObject param) {
        int page = param.getInteger("page");
        int count = param.getInteger("count");
        String sort = param.getString("sort");
        String order = param.getString("order");
        JSONObject condition = param.getJSONObject("condition");


        return userDao.list(page, count, sort, order, condition);
    }
}
