package com.weixin.common.user.bmo.impl;

import com.weixin.common.mapper.UserBoundMapper;
import com.weixin.common.mapper.WechatUserMapper;
import com.weixin.common.model.user.UserBound;
import com.weixin.common.model.user.WechatUser;
import com.weixin.common.user.bmo.UserBoundBMO;
import com.weixin.common.util.CommonConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("userBoundBMO")
public class UserBoundBMOImpl implements UserBoundBMO {

    private static Logger log = Logger.getLogger(UserBoundBMOImpl.class);

    @Autowired
    private UserBoundMapper userBoundMapper;

    @Autowired
    private WechatUserMapper wechatUserMapper;

    /**
     * 查询OpenId绑定的手机号
     *
     * @param openId：用户微信号标识
     * @return String
     * @author XMZ
     */
    public String queryUserBoundPhoneNum(String openId) {
        String phone_num = null;
        try {
            phone_num = userBoundMapper.queryUserBoundPhoneNum(openId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phone_num;
    }

    /**
     * 绑定OpenId和手机号
     *
     * @param userBound : 用户绑定号码存储对象
     * @return String
     * @author XMZ
     */
    public String insertBoundAccNbr(UserBound userBound) {
        String result = CommonConstants.EXTRACT_SUCESS; //成功
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date expDate = sdf.parse("3000-1-1");//默认失效时间
            userBound.setEff_time(new Date());
            userBound.setExp_time(expDate);
            userBoundMapper.deleteBoundRelation(userBound.getPhone_num());
            userBoundMapper.insertBoundAccNbr(userBound);
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonConstants.EXTRACT_FAIL; //失败
        }
        return result;
    }

    /**
     * 更换绑定OpenId和手机号
     *
     * @param userBound : 用户绑定号码存储对象
     * @return String
     * @author XMZ
     */
    public String updateBoundAccNbr(UserBound userBound) {
        String result = CommonConstants.EXTRACT_SUCESS; //成功
        try {
            userBound.setEff_time(new Date());
            userBoundMapper.deleteBoundRelation(userBound.getPhone_num());
            userBoundMapper.updateBoundAccNbr(userBound);
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonConstants.EXTRACT_FAIL; //失败
        }
        return result;
    }

    /**
     * 解除绑定OpenId和手机号
     *
     * @param openId：用户微信号标识
     * @return String
     * @author XMZ
     */
    public Map<String, Object> deleteBoundAccNbr(String openId) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String resultCode = CommonConstants.EXTRACT_SUCESS; //成功
        String resultMsg = "用户解绑成功";
        try {
            userBoundMapper.deleteBoundAccNbr(openId);
        } catch (Exception e) {
            e.printStackTrace();
            resultCode = CommonConstants.EXTRACT_FAIL; //失败
            resultMsg = "用户解绑失败";
        }
        retMap.put("resultCode", resultCode);
        retMap.put("resultMsg", resultMsg);
        return retMap;
    }

    /**
     * 新增微信用户基本信息
     *
     * @param wechatUser：用户基础信息
     * @author XMZ
     */
    public void insertWechatUser(WechatUser wechatUser) {
        try {
            wechatUserMapper.insertWechatUser(wechatUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新微信用户基本信息
     *
     * @param wechatUser：用户基础信息
     * @author XMZ
     */
    public void updateWechatUser(WechatUser wechatUser) {
        try {
            wechatUserMapper.updateWechatUser(wechatUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询微信用户
     *
     * @param openId
     * @return String
     */
    public WechatUser queryWechatUserByOpenId(String openId) {
        WechatUser wechatUser = wechatUserMapper.queryWechatUserByOpenId(openId);
        if (wechatUser == null) {
            return null;
        } else return wechatUser;
    }

    /**
     * 查询微信用户是否已存在
     *
     * @param openId
     * @return String
     */
    public boolean queryWechatUserIsExistByOpenId(String openId) {
        int count = wechatUserMapper.queryWechatUserCountByOpenId(openId);

        return count > 0;
    }
}
