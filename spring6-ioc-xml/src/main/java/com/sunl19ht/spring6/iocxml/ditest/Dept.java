package com.sunl19ht.spring6.iocxml.ditest;

import java.util.Arrays;
import java.util.List;

//部门
public class Dept {
    //部门名称
    private String dName;

    private List<Emp> empList;

    public List<Emp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Emp> empList) {
        this.empList = empList;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public void info() {
        System.out.println("部门名称：" + dName);
        for (Emp emp : empList) {
            System.out.println(emp.geteName() + " " + Arrays.toString(emp.getLoves()));
        }
    }
}
