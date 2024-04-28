package com.sunl19ht.spring6.iocxml.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPost implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("3. bean的后置处理器，初始化之前进行执行");
        System.out.println(beanName + "::" + bean);
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("5. bean的后置处理器，初始化之后进行执行");
        System.out.println(beanName + "::" + bean);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
