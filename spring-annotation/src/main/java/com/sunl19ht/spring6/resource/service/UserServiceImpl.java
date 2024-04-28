package com.sunl19ht.spring6.resource.service;

import com.sunl19ht.spring6.resource.dao.UserDao;
import com.sunl19ht.spring6.resource.dao.UserDaoImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("myUserService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao myUserDao;
    @Override
    public void add() {
        myUserDao.add();
    }
}
