package com.andy.web;

import com.andy.service.S_userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Andy on 2016/11/10.
 */
@RestController
@RequestMapping(value = "/menu1")
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private S_userService userService;

    @RequestMapping(value = "refresh", method = RequestMethod.POST)
    public void list() {
        logger.info("刷新菜单配置");


    }
}
