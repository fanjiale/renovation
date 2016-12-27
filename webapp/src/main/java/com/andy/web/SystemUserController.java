package com.andy.web;

import com.alibaba.fastjson.JSONObject;
import com.andy.service.S_userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Andy on 2016/11/10.
 */
@RestController
@RequestMapping(value = "/system/user")
public class SystemUserController {

    private static final Logger logger = LoggerFactory.getLogger(SystemUserController.class);

    @Autowired
    private S_userService userService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public JSONObject list(@RequestBody JSONObject param) {
        logger.info("获取系统用户列表");

        return userService.list(param);
    }
}
