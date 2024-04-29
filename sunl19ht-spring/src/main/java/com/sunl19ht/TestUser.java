package com.sunl19ht;

import com.sunl19ht.bean.ApplicationContext;
import com.sunl19ht.bean.impl.AnnotationApplication;
import com.sunl19ht.service.UserService;

public class TestUser {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationApplication("com.sunl19ht");
        UserService userService = (UserService) context.getBean(UserService.class);
        System.out.println(userService);
        userService.add();
    }
}
