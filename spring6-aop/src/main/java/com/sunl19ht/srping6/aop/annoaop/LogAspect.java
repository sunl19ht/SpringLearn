package com.sunl19ht.srping6.aop.annoaop;

import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//切面类
@Aspect //切面类
@Component  //IOC容器进行管理
public class LogAspect {
    //设置切入点和通知类型
    //切入点表达式：execution(public int com.sunl19ht.srping6.aop.annoaop.CalculatorImpl.*(int,int)
    //切入点表达式：execution(访问修饰符 返回值类型 方法所在类的全路径.方法名(参数列表)) *为任意路径 ..为任意参数
    //通知类型：前置 返回 异常 后置 环绕
//    @Before()
    @Before(value = "execution(public int com.sunl19ht.srping6.aop.annoaop.CalculatorImpl.add(int, int))") //切入点表达式
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName(); //增强方法的名字
        Object[] args = joinPoint.getArgs();    //参数
        System.out.println("Logger--->前置通知方法名称：" + methodName + " 参数：" + Arrays.toString(args));
    }
//    @AfterReturning()
    @AfterReturning(value = "execution(public int com.sunl19ht.srping6.aop.annoaop.CalculatorImpl.add(int, int))", returning = "result") //returning 目标方法返回值
    public void afterReturningMethod(JoinPoint joinPoint, Object result) { //Object 接收目标方法的返回值
        String methodName = joinPoint.getSignature().getName(); //增强方法的名字
        System.out.println("Logger--->返回通知方法名称：" + methodName + " 返回结果：" + result);
    }
//    @AfterThrowing() //出现异常会执行 并且获取异常信息
    @AfterThrowing(value = "execution(public int com.sunl19ht.srping6.aop.annoaop.CalculatorImpl.add(int, int))", throwing = "ex")  //throwing 目标方法异常
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable ex) {    //Throwable 接收目标方法异常
        String methodName = joinPoint.getSignature().getName(); //增强方法的名字
        System.out.println("Logger--->异常通知方法名称：" + methodName + " 异常信息：" + ex);
    }
//    @After()
    @After(value = "pointCut()")
    public void afterMethod(JoinPoint joinPoint) {  //JoinPoint 接收通知方法的参数
        String methodName = joinPoint.getSignature().getName(); //增强方法的名字
        System.out.println("Logger--->后置通知方法名称：" + methodName);
    }
//    @Around()
    @Around(value = "execution(public int com.sunl19ht.srping6.aop.annoaop.CalculatorImpl.add(int, int))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String argString = Arrays.toString(args);
        Object result = null;
        try {
            System.out.println("环绕");
            result = joinPoint.proceed();
            System.out.println("目标方法返回值之后");
        } catch (Throwable e) {
            String string = e.toString();
            System.out.println("目标方法出现异常" + string);
        } finally {
            System.out.println("执行完毕");
        }
        return result;
    }

    //重用切入点表达式
    @Pointcut("execution(public int com.sunl19ht.srping6.aop.annoaop.CalculatorImpl.*(int, int))")
    public void pointCut() {}
}
