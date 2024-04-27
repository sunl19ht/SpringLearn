package com.sunl19ht.spring6.iocxml.bean;

public class PersonDaoImpl implements UserDao{
    @Override
    public void run() {
        System.out.println("PersonDaoImpl run...");
    }
}
