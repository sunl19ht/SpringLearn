package com.sunl19ht.spring6.iocxml.life;

public class User {
    private String name;

    public User() {
        System.out.println("1. 调用无参构造创建bean对象");
    }

    //初始化方法
    public void initMethod() {
        System.out.println("4. bean初始化会调用指定的初始化方法");
    }

    public void destroyMethod() {
        System.out.println("7. bean销毁会调用指定的销毁方法");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("2. 设置bean的属性值");
        this.name = name;
    }
}
