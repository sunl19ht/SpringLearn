package com.sunl19ht.srping6.aop.example;

public class CalculatorStaticProxy implements Calculator{
    private CalculatorImpl calculator;

    public CalculatorStaticProxy(CalculatorImpl calculator) {
        this.calculator = calculator;
    }

    @Override
    public int add(int i, int j) {
        System.out.println("日志记录：add(" + i + "," + j + ")");
        int addResult = calculator.add(i, j);
        System.out.println("日志记录：方法结束了 结果是：" + addResult);
        return addResult;
    }

    @Override
    public int sub(int i, int j) {
        System.out.println("日志记录：add(" + i + "," + j + ")");
        int subResult = calculator.sub(i, j);
        System.out.println("日志记录：方法结束了 结果是：" + subResult);
        return subResult;
    }

    @Override
    public int mul(int i, int j) {
        System.out.println("日志记录：add(" + i + "," + j + ")");
        int mulResult = calculator.mul(i, j);
        System.out.println("日志记录：方法结束了 结果是：" + mulResult);
        return mulResult;
    }

    @Override
    public int div(int i, int j) {
        System.out.println("日志记录：add(" + i + "," + j + ")");
        int divResult = calculator.div(i, j);
        System.out.println("日志记录：方法结束了 结果是：" + divResult);
        return divResult;
    }
}
