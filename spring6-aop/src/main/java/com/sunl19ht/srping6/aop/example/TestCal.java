package com.sunl19ht.srping6.aop.example;

public class TestCal {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new CalculatorImpl());
        Object proxy = proxyFactory.getProxy();
        Calculator calculator = (Calculator) proxy;
        int add = calculator.add(10, 20);
        System.out.println(add);
    }
}
