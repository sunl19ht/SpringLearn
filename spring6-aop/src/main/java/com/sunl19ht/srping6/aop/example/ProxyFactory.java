package com.sunl19ht.srping6.aop.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    private Object target;
    public ProxyFactory(Object target) {
        this.target = target;
    }

    //返回代理对象
    public Object getProxy() {
        /**
         * 1. ClassLoader loader:加载动态生成类代理器的类加载器
         * 2. Class<?>[] interfaces:指定目标对象实现的接口的类型，使用泛型方法确认类型
         * 3. InvocationHandler h:指定代理对象实现目标对象的过程
         */
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        InvocationHandler h = new InvocationHandler() {
            /**
             * @param proxy  代理对象
             * @param method 需要重写目标对象的方法
             * @param args   method方法里的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("日志开始");
                Object result = method.invoke(target, args);
                System.out.println("日志结束");
                return result;
            }
        };
        return Proxy.newProxyInstance(classLoader, interfaces, h);
    }
}
