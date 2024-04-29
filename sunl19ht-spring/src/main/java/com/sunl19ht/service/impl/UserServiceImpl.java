package com.sunl19ht.service.impl;

import com.sunl19ht.anno.Bean;
import com.sunl19ht.anno.Di;
import com.sunl19ht.dao.UserDao;
import com.sunl19ht.service.UserService;

@Bean
public class UserServiceImpl implements UserService {

    @Di
    private UserDao userDao;

    @Override
    public void add() {
        System.out.println("UserServiceImpl add");
        userDao.add();
    }
}
