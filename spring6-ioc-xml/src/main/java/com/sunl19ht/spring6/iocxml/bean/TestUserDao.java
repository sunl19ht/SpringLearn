package com.sunl19ht.spring6.iocxml.bean;

import com.sunl19ht.spring6.iocxml.bean.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUserDao {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

        UserDao userDao = context.getBean(UserDao.class);
        userDao.run();
        System.out.println(userDao);
    }
}
