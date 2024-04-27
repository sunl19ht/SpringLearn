package com.sunl19ht.spring6.iocxml.dimap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStu {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-diref.xml");
        Student student1 = context.getBean("studentp", Student.class);
        student1.run();
    }
}
