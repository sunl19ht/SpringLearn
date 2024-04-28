package com.sunl19ht.spring6.resource.dao;

import org.springframework.stereotype.Repository;

@Repository("myRedisDao")
public class RedisDaoImpl implements UserDao {
    @Override
    public void add() {
        System.out.println("redis add");
    }
}
