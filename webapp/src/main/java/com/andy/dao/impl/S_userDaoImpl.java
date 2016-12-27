package com.andy.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.andy.dao.S_userDao;
import com.skytech.ark.jdbc.GenericCRUD;
import com.skytech.ark.jdbc.QueryBuilder;
import com.skytech.ark.stereotype.jdbc.NamedQuerySQL;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by fanjl on 2016/9/30.
 */
@Repository
public class S_userDaoImpl extends GenericCRUD<JSONObject> implements S_userDao {

    @NamedQuerySQL("com.andy.dao.impl.list")
    private String list;

    @Override
    protected String getTableName() {
        return "S_USER";
    }

    @Override
    protected JSONObject wrap(JSONObject jsonObject) {
        return jsonObject;
    }

    @Override
    protected JSONObject unWrap(JSONObject jsonObject) {
        return jsonObject;
    }

    public List<JSONObject> listUserByUserCode(String user_code) {
        JSONObject condition = new JSONObject();
        condition.put("user_code", user_code);

        return list(condition);
    }

    @Override
    public JSONObject list(int page, int count, String sort, String order, JSONObject condition) {
        if(StringUtils.isEmpty(sort)){
            sort = "";
            order = "asc";
        }
        if(condition.getString("user_name") == null){
            QueryBuilder builder = new QueryBuilder();
            builder.addOrder(sort,order);

            return listPage(new JSONObject(),builder, page, count);
        }
        else {
            condition.put("sort", sort);
            condition.put("order", order);
            return listPage(list, condition, page, count);
        }
    }
}
