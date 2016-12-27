package com.weixin.common.control;


import com.alibaba.fastjson.JSONObject;
import com.weixin.common.config.load.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 刷新缓存
 */
@Controller
@RequestMapping("/cache")
public class ClearCacheController {
    private static Logger log = LoggerFactory.getLogger(ClearCacheController.class);

    @Autowired
    ConfigLoader configLoader;

    /**
     * 处理请求
     *
     * @throws Exception
     */
    @RequestMapping(value = "clearCache", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject clearCache(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.error("系统缓存刷新！");

        configLoader.reload();

        return new JSONObject();
    }

}
