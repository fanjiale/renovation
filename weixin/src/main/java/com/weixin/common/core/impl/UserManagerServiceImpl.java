package com.weixin.common.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.weixin.common.core.UserManagerService;
import com.weixin.common.mapper.WechatUserMapper;
import com.weixin.common.model.user.WechatUser;
import com.weixin.common.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Andy on 2016/12/15.
 */
@Component("userManagerServiceImpl")
public class UserManagerServiceImpl implements UserManagerService {

    private static Logger log = LoggerFactory.getLogger(UserManagerServiceImpl.class);

    @Autowired
    private WechatUserMapper wechatUserMapper;

    @Override
    public WechatUser getUserInfo(String open_id, String access_token) {
        WechatUser wechatUser = null;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid=" + open_id + "&lang=zh_CN";
        JSONObject jsonObject = HttpUtils.httpRequest(requestUrl, "GET", null);

        // 如果请求成功
        if (null != jsonObject) {
            try {
                wechatUser = new WechatUser();
                wechatUser.setOpen_id(open_id);
                wechatUser.setNick_name(jsonObject.getString("nickname"));
                wechatUser.setSex(jsonObject.getInteger("sex"));
                wechatUser.setCity(jsonObject.getString("city"));
                wechatUser.setProvince(jsonObject.getString("province"));
                wechatUser.setLanguage(jsonObject.getString("language"));
                wechatUser.setCountry(jsonObject.getString("country"));
                wechatUser.setUnion_id(jsonObject.getString("unionid"));
                wechatUser.setRemark(jsonObject.getString("remark"));
                wechatUser.setGroup_id(jsonObject.getString("groupid"));
                wechatUser.setHead_img_url(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                wechatUser = null;
                // 获取用户基本信息失败
                log.error("获取userInfo失败 errcode:{} errmsg:{}",
                        jsonObject.getString("errcode"),
                        jsonObject.getString("errmsg"));
            }
        }

        return wechatUser;
    }
}
