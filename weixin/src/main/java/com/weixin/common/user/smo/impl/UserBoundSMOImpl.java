package com.weixin.common.user.smo.impl;

import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.model.config.ValidateMessageConfig;
import com.weixin.common.model.user.UserBound;
import com.weixin.common.model.user.WechatUser;
import com.weixin.common.user.bmo.UserBoundBMO;
import com.weixin.common.user.smo.UserBoundSMO;
import com.weixin.common.util.CommonConstants;
import com.weixin.common.validate.bmo.MessageValidateBMO;
import com.weixin.webservice.util.MsgHandleUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("userBoundSMO")
public class UserBoundSMOImpl implements UserBoundSMO {

    private static Logger log = Logger.getLogger(UserBoundSMOImpl.class);

    @Autowired
    private UserBoundBMO userBoundBMO;

    @Autowired
    private MessageValidateBMO messageValidateBMO;

    @Autowired
    private ConfigLoader configLoader;

    public MessageValidateBMO getMessageValidateBMO() {
        return messageValidateBMO;
    }

    public void setMessageValidateBMO(MessageValidateBMO messageValidateBMO) {
        this.messageValidateBMO = messageValidateBMO;
    }

    public UserBoundBMO getUserBoundBMO() {
        return userBoundBMO;
    }

    public void setUserBoundBMO(UserBoundBMO userBoundBMO) {
        this.userBoundBMO = userBoundBMO;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public void setConfigLoader(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    /**
     * 查询OpenId绑定的手机号
     *
     * @param openId：用户微信号标识
     * @return String
     * @author XMZ
     */
    public String queryUserBoundPhoneNum(String openId) {
        return userBoundBMO.queryUserBoundPhoneNum(openId);
    }

    /**
     * 绑定OpenId和手机号
     *
     * @param userBound : 用户绑定号码存储对象
     * @return Map<String,Object>
     * @author XMZ
     */
    public Map<String, Object> insertBoundAccNbr(UserBound userBound) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String resultCode = CommonConstants.EXTRACT_SUCESS;
        String resultDesc = "用户绑定成功";
        try {
            String productNo = userBound.getPhone_num(); //用户号码
            String verCode = userBound.getVer_code();  //验证码

            log.debug("--------------------二次检验开始----------------------");
            //二次验证
            Map<String, Object> checkMap = new HashMap<String, Object>();
            ValidateMessageConfig validateMessageConfig = configLoader.getValidateMessageConfig();
            int type = validateMessageConfig.getType();
            if (type == CommonConstants.VALIDATEMSG_UAM) {
                checkMap = messageValidateBMO.checkVercodeFromUAM(productNo, verCode);
            } else if (type == CommonConstants.VALIDATEMSG_PLAT) {
                checkMap = messageValidateBMO.checkVercode(null, productNo, null, verCode);
            }
            log.debug("--------------------二次检验结束----------------------");

            if (checkMap.get("resultCode").equals(CommonConstants.EXTRACT_SUCESS)) {
                resultCode = userBoundBMO.insertBoundAccNbr(userBound);
                if (resultCode.equals(CommonConstants.EXTRACT_FAIL)) {
                    resultDesc = "用户绑定失败";
                }
            } else {
                resultCode = CommonConstants.EXTRACT_FAIL;
                resultDesc = "您输入的验证码校验失败！";
            }
        } catch (Exception e) {
            log.error("-----------用户绑定异常： " + MsgHandleUtil.getExceptionString(e) + "-----------");
            resultCode = CommonConstants.EXTRACT_FAIL;
            resultDesc = "用户绑定失败，未知异常";
        }
        retMap.put("resultCode", resultCode);
        retMap.put("resultMsg", resultDesc);
        return retMap;
    }

    /**
     * 获取验证码
     *
     * @param accNbr：用户号码
     * @return Map
     */
    public Map<String, Object> getVercodeByAccNbr(String accNbr) {
        Map<String, Object> map = new HashMap<String, Object>();
        ValidateMessageConfig validateMessageConfig = configLoader.getValidateMessageConfig();
        int type = validateMessageConfig.getType();
        if (type == CommonConstants.VALIDATEMSG_UAM) {
            map = messageValidateBMO.getVercodeFromUAM(accNbr);
        } else if (type == CommonConstants.VALIDATEMSG_PLAT) {
            map = messageValidateBMO.getVercodeByAccNbr(null, accNbr);
        }
        return map;
    }

    /**
     * 获取验证码（自行生成调用短信接口）
     *
     * @param accNbr：用户号码
     * @return Map
     */
    public Map<String, Object> getVercodeByAccNbr(String openId, String accNbr) {
        return messageValidateBMO.getVercodeByAccNbr(openId, accNbr);
    }

    /**
     * 更换绑定OpenId和手机号
     *
     * @param userBound : 用户绑定号码存储对象
     * @return Map<String,Object>
     * @author XMZ
     */
    public Map<String, Object> updateBoundAccNbr(UserBound userBound) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String resultCode = CommonConstants.EXTRACT_SUCESS;
        String resultDesc = "更换号码成功";
        try {
            String productNo = userBound.getPhone_num(); //用户号码
            String verCode = userBound.getVer_code();  //验证码

            log.debug("--------------------二次检验开始----------------------");
            //二次验证
            Map<String, Object> checkMap = new HashMap<String, Object>();
            ValidateMessageConfig validateMessageConfig = configLoader.getValidateMessageConfig();
            int type = validateMessageConfig.getType();
            if (type == CommonConstants.VALIDATEMSG_UAM) {
                checkMap = messageValidateBMO.checkVercodeFromUAM(productNo, verCode);
            } else if (type == CommonConstants.VALIDATEMSG_PLAT) {
                checkMap = messageValidateBMO.checkVercode(null, productNo, null, verCode);
            }
            log.debug("--------------------二次检验结束----------------------");

            if (checkMap.get("resultCode").equals(CommonConstants.EXTRACT_SUCESS)) {
                resultCode = userBoundBMO.updateBoundAccNbr(userBound);
                if (resultCode.equals(CommonConstants.EXTRACT_FAIL)) {
                    resultDesc = "更换号码失败";
                }
            } else {
                resultCode = CommonConstants.EXTRACT_FAIL;
                resultDesc = "您输入的验证码校验失败！";
            }
        } catch (Exception e) {
            log.error("-----------更换号码异常： " + MsgHandleUtil.getExceptionString(e) + "-----------");
            resultCode = CommonConstants.EXTRACT_FAIL;
            resultDesc = "更换号码失败，未知异常";
        }
        retMap.put("resultCode", resultCode);
        retMap.put("resultMsg", resultDesc);
        return retMap;
    }

    /**
     * 解除绑定OpenId和手机号
     *
     * @param openId：用户微信号标识
     * @return String
     * @author XMZ
     */
    public Map<String, Object> deleteBoundAccNbr(String openId) {
        return userBoundBMO.deleteBoundAccNbr(openId);
    }

    /**
     * 保存微信用户基本信息
     *
     * @param wechatUser：用户基础信息
     */
    public void saveWechatUser(WechatUser wechatUser) {
        try {
            boolean exist = userBoundBMO.queryWechatUserIsExistByOpenId(wechatUser.getOpen_id());
            if (exist) {
                userBoundBMO.updateWechatUser(wechatUser);
            } else {
                userBoundBMO.insertWechatUser(wechatUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
