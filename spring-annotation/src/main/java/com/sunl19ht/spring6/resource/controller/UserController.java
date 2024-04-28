package com.sunl19ht.spring6.resource.controller;

import com.sunl19ht.spring6.resource.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;

@Controller("myUserController")
public class UserController {
    //根据名称进行注入
//    @Resource(name = "myUserService") //默认根据名称进行装配byName 未指定name使用属性名作为name 通过name找不要到会自动启动通过类型装配
//    private UserService userService;

    //根据类型匹配
    @Resource
    private UserService userService;

    public void add() {
        System.out.println("Controller.....");
        userService.add();
    }
}
