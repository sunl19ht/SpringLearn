package com.sunl19ht.spring6.iocxml.factorybean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUser {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-factory.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user);
    }
}
