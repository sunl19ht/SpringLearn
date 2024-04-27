package com.sunl19ht.spring6.iocxml.ditest;

import java.util.Arrays;

//员工类
public class Emp {
    //标识员工属于某个部门
    private Dept dept;
    private String eName;
    private Integer age;
    private String[] loves; //爱好

    public String[] getLoves() {
        return loves;
    }

    public void setLoves(String[] loves) {
        this.loves = loves;
    }

    public void work() {
        System.out.println("员工：" + eName + " 年龄：" + age + " 正在工作...");
        dept.info();
        System.out.println(Arrays.toString(loves));
    }

    public Dept getDept() {
        return dept;
    }

    public String geteName() {
        return eName;
    }

    public Integer getAge() {
        return age;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
