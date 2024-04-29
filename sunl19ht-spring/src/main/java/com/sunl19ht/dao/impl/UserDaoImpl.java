package com.sunl19ht.dao.impl;

import com.sunl19ht.anno.Bean;
import com.sunl19ht.dao.UserDao;

@Bean
public class UserDaoImpl implements UserDao {
    @Override
    public void add() {
        System.out.println("UserDaoImpl add");
    }
}
