package com.sunl19ht.spring6;

import org.slf4j.Logger;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUser {

    //创建Logger对象
    private Logger logger = LoggerFactory.getLogger(TestUser.class);

    @Test
    public void testUserObject() {
        //1. 加载spring的配置文件 对象创建
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");    //参数为配置文件名
        //2. 获取创建的对象
        User user = (User) context.getBean("user");
        System.out.println(user);
        //3. 使用对象调用测试方法
        user.add();
        logger.info("testUserObject执行调用成功");
    }

    @Test
    public void testUserObject1() throws Exception {
        //获取Class对象
        Class<?> clazz = Class.forName("com.sunl19ht.spring6.User");
        //老版本JDK创建对象方法 新版本已过时
//        Object o = clazz.newInstance();

        //新版本创建对象方法
        User user = (User) clazz.getDeclaredConstructor().newInstance();
        System.out.println(user);
    }
}
