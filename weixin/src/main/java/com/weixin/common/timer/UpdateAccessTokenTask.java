package com.weixin.common.timer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.common.config.load.xml.XMLConfigLoader;
import com.weixin.common.core.WxService;
import com.weixin.common.mapper.AccessTokenMapper;
import com.weixin.common.model.token.AccessToken;
import com.weixin.common.util.CommonConstants;

/**
 * 更新AccessToken任务执行
 */
@Component("updateAccessTokenTask")
public class UpdateAccessTokenTask {

    @Autowired
    AccessTokenMapper accessTokenMapper;
    @Autowired
    private WxService wxService;

    /**
     * 更新AccessToken
     */
    public void update() {
        /*try {
            HashMap<String, Object> config = accessTokenMapper.getAccessTokenConfig(CommonConstants.ACCESS_TOKEN_TYPE_NORMAL);
            if (checkIsSelfServerTask(config)) {
                AccessToken at = wxService.getAccessToken(XMLConfigLoader.getBaseConfig().getAppid(), XMLConfigLoader.getBaseConfig().getSecret());
                if (at == null) {
                    throw new Exception("获取AccessToken返回为Null");
                }
                String accessToken = at.getToken();
                if (accessToken != null && accessToken.length() > 0) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("accessToken", accessToken);
                    params.put("type", CommonConstants.ACCESS_TOKEN_TYPE_NORMAL);
                    accessTokenMapper.updateAccessToken(params);
                } else {
                    throw new Exception("获得AccessToken返回为空");
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 判断是否由本进程执行更新
     * 
     * @param config
     * @return
     */
    public boolean checkIsSelfServerTask(HashMap<String, Object> config) {

        BigDecimal period = (BigDecimal) config.get("PERIOD");
        if (period == null || period.doubleValue() > 0.0625) {
            return true;
        }

        return false;
    }
}
