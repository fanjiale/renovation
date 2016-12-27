package com.weixin.common.mapper;

import com.weixin.common.model.validate.ValidateMessage;

import java.util.Map;


public interface MessageValidateMapper {
    /**
     * 统计OpenId获取验证码的次数
     *
     * @param map
     */
    Long countOpenIdValidateTimes(Map<String, Object> map);

    /**
     * 保存校验码记录
     *
     * @param validateMessage
     */
    void insertValidateMessage(ValidateMessage validateMessage);

    /**
     * 查询验证码信息
     *
     * @param vm
     */
    ValidateMessage queryValidateMessage(ValidateMessage vm);

    /**
     * 获取UAM验证码接口的序列号
     *
     * @return
     * @author XMZ
     */
    String getUamTransactionSeq();
}
