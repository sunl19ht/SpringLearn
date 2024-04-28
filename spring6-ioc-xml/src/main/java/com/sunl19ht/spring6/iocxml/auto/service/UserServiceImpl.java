package com.sunl19ht.spring6.iocxml.auto.service;

import com.sunl19ht.spring6.iocxml.auto.dao.UserDao;
import com.sunl19ht.spring6.iocxml.auto.dao.UserDaoImpl;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
//        UserDao userDao = new UserDaoImpl();
//        userDao.addUserDao();
        System.out.println("UserServiceImpl");
        userDao.addUserDao();
    }
}
