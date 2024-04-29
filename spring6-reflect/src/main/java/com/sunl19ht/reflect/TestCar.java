package com.sunl19ht.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestCar {
    public static void main( String[] args ) {

    }

    //1. 获取Class对象的多种方式
    @Test
    public void test01() throws Exception {
        //1. 类名.class
        Class<Car> clazz1 = Car.class;

        //2. 对象.getClass()
        Class clazz2 = new Car().getClass();

        //3. Class.forName("全路径")
        Class<?> clazz3 = Class.forName("com.sunl19ht.reflect.Car");

        //实例化
        Car car = (Car) clazz3.getDeclaredConstructor().newInstance();
        System.out.println(car);
    }

    //2. 获取构造方法
    @Test
    public void test02() throws Exception {
        Class<Car> clazz = Car.class;
        //获取所有构造
//        Constructor<?>[] constructors = clazz.getConstructors();    //如果有private获取不到

        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();    //获取所有构造方法public和private
        for (Constructor<?> c : declaredConstructors) {
            System.out.println("构造方法名称：" + c.getName() + " 构造方法参数：" + c.getParameterCount());
        }

        //指定有参构造创建对象
        //1. 构造public
//        Constructor c1 = clazz.getConstructor(String.class, int.class, String.class); //加上参数的类型
//        Car car1 = (Car) c1.newInstance("夏利", 10, "红色");    //实例化对象
//        System.out.println(car1);

        //2. 构造private
        Constructor<Car> c2 = clazz.getDeclaredConstructor(String.class, int.class, String.class);
        c2.setAccessible(true); //设置访问私有构造
        Car car2 = c2.newInstance("捷达", 16, "白色");
        System.out.println(car2);
    }

    //3. 获取属性
    @Test
    public void test03() throws Exception {
        Class<Car> clazz = Car.class;
        Car car = clazz.getDeclaredConstructor().newInstance();
        //获取所有public属性
//        Field[] fields = clazz.getFields();

        //获取所有属性包含private
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //操作类中的属性赋值
            if(field.getName().equals("name")) {
                //设置允许访问
                field.setAccessible(true);
                field.set(car, "奔驰");   //对象, 属性值
            }
            System.out.println("属性名称：" + field.getName() + " 属性类型：" + field.getType());
        }
        System.out.println(car);
    }

    //4. 获取方法
    @Test
    public void test04() throws Exception {
        Car car = new Car("奔驰", 10, "黑色");
        Class<? extends Car> clazz = car.getClass();

        //操作public方法
        Method[] methods = clazz.getMethods();
        for (Method m1 : methods) {
            System.out.println("方法名：" + m1.getName());
            //执行某个方法toString
            if(m1.getName().equals("toString")) {
                String invoke = (String) m1.invoke(car);//对象, 其他参数
                System.out.println("toString执行：" + invoke);
            }
        }

        //操作private方法
        Method[] methodsAll = clazz.getDeclaredMethods();
        for (Method method : methodsAll) {
            if (method.getName().equals("run")) {
                method.setAccessible(true);
                method.invoke(car);
            }
        }
    }
}