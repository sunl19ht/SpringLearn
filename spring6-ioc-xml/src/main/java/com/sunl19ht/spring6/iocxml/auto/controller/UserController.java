package com.sunl19ht.spring6.iocxml.auto.controller;

import com.sunl19ht.spring6.iocxml.auto.service.UserService;
import com.sunl19ht.spring6.iocxml.auto.service.UserServiceImpl;

public class UserController {
    public UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void addUser() {
        System.out.println("UserController");
        //调用service的方法
        userService.addUserService();

//        UserService = new UserServiceImpl();
//        userService.addUserService();
    }
}
