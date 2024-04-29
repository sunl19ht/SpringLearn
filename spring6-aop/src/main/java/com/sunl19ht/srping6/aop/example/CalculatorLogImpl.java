package com.sunl19ht.srping6.aop.example;

public class CalculatorLogImpl implements Calculator {
    @Override
    public int add(int i, int j) {
        System.out.println("日志记录：add(" + i + "," + j + ")");
        int result = i + j;
        System.out.println("方法内部 result = " + result);
        System.out.println("日志记录：方法结束了 结果是：" + result);
        return result;
    }

    @Override
    public int sub(int i, int j) {
        System.out.println("日志记录：sub(" + i + "," + j + ")");
        int result = i - j;
        System.out.println("方法内部 result = " + result);
        System.out.println("日志记录：方法结束了 结果是：" + result);
        return result;
    }

    @Override
    public int mul(int i, int j) {
        System.out.println("日志记录：mul(" + i + "," + j + ")");
        int result = i * j;
        System.out.println("方法内部 result = " + result);
        System.out.println("日志记录：方法结束了 结果是：" + result);
        return result;
    }

    @Override
    public int div(int i, int j) {
        System.out.println("日志记录：div(" + i + "," + j + ")");
        int result = i / j;
        System.out.println("方法内部 result = " + result);
        System.out.println("日志记录：方法结束了 结果是：" + result);
        return result;
    }
}
