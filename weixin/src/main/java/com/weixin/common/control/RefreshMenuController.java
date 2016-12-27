package com.weixin.common.control;


import com.weixin.common.core.MenuService;
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
 * @alias 更新菜单Controller
 */
@Controller
@RequestMapping("/menu")
public class RefreshMenuController {
    private static Logger log = LoggerFactory.getLogger(RefreshMenuController.class);
    @Autowired
    private MenuService menuService;


    /**
     * @throws Exception
     * @alias 处理更新菜单请求
     */
    @RequestMapping(value = "refreshMenu", method = RequestMethod.POST)
    @ResponseBody
    public int refreshMenu(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        return menuService.refresh();
    }
}
