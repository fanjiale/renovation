package com.weixin.common.mapper;


import com.weixin.common.model.user.UserBound;


public interface UserBoundMapper {

    /**
     * 查询OpenId绑定的手机号
     *
     * @param openId
     * @return String
     */
    String queryUserBoundPhoneNum(String openId);

    /**
     * 查询手机号所属openid
     *
     * @param accNbr
     * @return String
     */
    String queryOpenid(String accNbr);

    /**
     * 绑定OpenId和手机号
     *
     * @param userBound ： 用户绑定号码存储对象
     * @author XMZ
     */
    void insertBoundAccNbr(UserBound userBound);

    /**
     * 更换绑定OpenId和手机号
     *
     * @param userBound ： 用户绑定号码存储对象
     * @author XMZ
     */
    void updateBoundAccNbr(UserBound userBound);

    /**
     * 解除绑定OpenId和手机号
     *
     * @param openId：用户微信号标识
     * @author XMZ
     */
    void deleteBoundAccNbr(String openId);

    /**
     * 解除号码的绑定关系
     *
     * @param accNbr
     */
    void deleteBoundRelation(String accNbr);

}
