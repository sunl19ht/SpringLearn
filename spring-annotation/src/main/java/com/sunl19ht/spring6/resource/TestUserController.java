package com.sunl19ht.spring6.resource;

import com.sunl19ht.spring6.resource.controller.UserController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUserController {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserController userController = context.getBean("myUserController", UserController.class);
        userController.add();
    }
}
