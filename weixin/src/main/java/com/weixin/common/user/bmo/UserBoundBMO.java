package com.weixin.common.user.bmo;

import com.weixin.common.model.user.UserBound;
import com.weixin.common.model.user.WechatUser;

import java.util.Map;


public interface UserBoundBMO {

    /**
     * 查询OpenId绑定的手机号
     *
     * @param openId：用户微信号标识
     * @return String
     * @author XMZ
     */
    String queryUserBoundPhoneNum(String openId);

    /**
     * 绑定OpenId和手机号
     *
     * @param userBound : 用户绑定号码存储对象
     * @return String
     * @author XMZ
     */
    String insertBoundAccNbr(UserBound userBound);

    /**
     * 更换绑定OpenId和手机号
     *
     * @param userBound : 用户绑定号码存储对象
     * @return String
     * @author XMZ
     */
    String updateBoundAccNbr(UserBound userBound);

    /**
     * 解除绑定OpenId和手机号
     *
     * @param openId：用户微信号标识
     * @return String
     * @author XMZ
     */
    Map<String, Object> deleteBoundAccNbr(String openId);

    /**
     * 新增微信用户基本信息
     *
     * @param wechatUser：用户基础信息
     * @author XMZ
     */
    void insertWechatUser(WechatUser wechatUser);

    /**
     * 更新微信用户基本信息
     *
     * @param wechatUser：用户基础信息
     * @author XMZ
     */
    void updateWechatUser(WechatUser wechatUser);

    /**
     * 查询微信用户
     *
     * @param openId
     * @return String
     */
    WechatUser queryWechatUserByOpenId(String openId);

    /**
     * 查询微信用户是否已存在
     *
     * @param openId
     * @return String
     */
    boolean queryWechatUserIsExistByOpenId(String openId);
}
