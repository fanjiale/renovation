package com.andy.pojo;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by fanjl on 2016/9/30.
 * 返回客户端的数据格式
 */
public class Result extends JSONObject {

    private boolean success = true;

    private String errormsg;

    public void setErrorMsg(String errormsg){
        this.success = false;
        this.errormsg = errormsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
