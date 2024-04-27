package com.sunl19ht.spring6.iocxml.bean;

public class UserDaoImpl implements UserDao{
    @Override
    public void run() {
        System.out.println("UserDaoImpl run...");
    }
}
