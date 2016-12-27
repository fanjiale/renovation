package com.andy.web;

import com.alibaba.fastjson.JSONObject;
import com.andy.pojo.Result;
import com.andy.service.S_loginLogService;
import com.andy.service.S_userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by fanjl on 2016/9/27.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private S_userService userService;

    @Autowired
    private S_loginLogService loginLogService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "userlogin", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody JSONObject userInfo, HttpSession session, HttpServletRequest request) {
        logger.info("用户" + userInfo.getString("user_code") + "请求登录");
        Result result = new Result();

        String user_code = userInfo.getString("user_code");
        String password = userInfo.getString("password");

        if (StringUtils.isEmpty(user_code) || StringUtils.isEmpty(password)) {
            result.setErrorMsg("用户名或密码不能为空！");
        }
        if (userService.validateUserInfo(user_code, password)) {
            JSONObject user = userService.findUserByUserCode(user_code);

            loginLogService.recordUserLoginLog(user, request);

            session.setAttribute("AuthenticatedAccount", user_code);

            result.put("user_code", user_code);
        } else {
            result.setErrorMsg("用户名或密码错误！");
        }

        return result;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject logout(HttpSession session) {
        session.removeAttribute("username");
        return new JSONObject();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

}
