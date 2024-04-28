package com.sunl19ht.spring6.autowired.controller;

import com.sunl19ht.spring6.autowired.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
//    方式1
//    @Autowired
//    UserService userService;

    //方式2 set方法
//    private UserService userService;
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }


    //方法3 构造方法
//    private UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

//    //方法4 形参注入
//    private UserService userService;
//    public UserController(@Autowired UserService userService) {
//        this.userService = userService;
//    }

    //方法5 当有参数的构造方法只有一个时，@Autowired可以省略
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void add() {
        System.out.println("Controller.....");
        userService.add();
    }
}
