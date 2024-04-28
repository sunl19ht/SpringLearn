package com.sunl19ht.spring6.autowired.service;

import com.sunl19ht.spring6.autowired.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserDao userDao;

//    private UserDao userDao;
//
//    @Autowired
//    public void setUserDao(UserDao userDao) {
//        this.userDao = userDao;
//    }

//    private UserDao userDao;
//
//    @Autowired
//    public UserServiceImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }

//    private UserDao userDao;
//    public UserServiceImpl(@Autowired UserDao userDao) {
//        this.userDao = userDao;
//    }

    // 最后的方式 使用两个注解根据名称进行注入
    @Autowired
    @Qualifier(value = "redisDaoImpl")
    private UserDao userDao;

    @Override
    public void add() {
        userDao.add();
    }
}
