package com.weixin.common.core;

import com.weixin.common.model.user.WechatUser;

/**
 * 微信用户管理
 * Created by Andy on 2016/12/15.
 */
public interface UserManagerService {

    /**
     * 获取用户基本信息
     * @param open_id
     * @param access_token
     * @return
     */
    WechatUser getUserInfo(String open_id, String access_token);
}
