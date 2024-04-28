package com.sunl19ht.spring6.iocxml.life;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-life.xml");
        User user = context.getBean("user", User.class);
        System.out.println("6. bean对象创建完成可以使用");
        System.out.println(user);

        context.close();    // 7. 容器销毁
    }
}
