package com.andy.init;

import com.andy.dao.S_userDao;
import com.andy.service.S_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
/**
 * Created by Andy on 2016/11/4.
 * 初始化用户
 */
@Test
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class InitSysUser extends AbstractTestNGSpringContextTests{

    @Autowired
    private S_userService userService;

    @Autowired
    private S_userDao userDao;

    @Test
    public void initUser(){
        delete();

        String user_name = "管理员";
        String user_code = "admin";
        String password = "1";

        userService.createUser(user_code,user_name,password);
    }

    private void delete(){
        userDao.execute("delete from S_USER");
    }
}
