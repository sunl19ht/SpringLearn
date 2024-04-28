package com.sunl19ht.spring6.autowired.dao;

import org.springframework.stereotype.Repository;

@Repository
public class RedisDaoImpl implements UserDao{
    @Override
    public void add() {
        System.out.println("redis add");
    }
}
