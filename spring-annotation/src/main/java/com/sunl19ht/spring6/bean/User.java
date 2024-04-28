package com.sunl19ht.spring6.bean;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * value 默认为类名首字母小写
 */
//@Repository
//@Service
//@Controller
@Component(value = "user")  //<bean id="user" class="com.sunl19ht.spring6.bean.User"/">
public class User {
}
