package com.sunl19ht.bean.impl;

import com.sunl19ht.anno.Bean;
import com.sunl19ht.anno.Di;
import com.sunl19ht.bean.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationApplication implements ApplicationContext {
    //创建map集合 用于放bean的实例对象
    private Map<Class, Object> beanFactory = new HashMap<>();
    private static String rootPath = "";

    //返回对象
    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    //设置包扫描规则 当前包及其子包 如果哪个类上有@Bean注解就将这个Bean反射及其实例化
    //创建有参构造 传递包路径 设置包扫描规则
    public AnnotationApplication(String basePackage) throws Exception{
    /// public static void pathDemo1(String basePackage) {
        //com.sunl19ht
        //1. 将.替换为/
        String packagePath = basePackage.replaceAll("\\.", "\\\\");

        //2. 获取包的绝对路径
        Enumeration<URL> urls = null;
        try {
            urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while(urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String filePath = URLDecoder.decode(url.getPath(), "utf-8");

                //获取包前面部分路径
                rootPath = filePath.substring(0, filePath.length() - packagePath.length());
                //进行包扫描
                loadBean(new File(filePath));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //属性注入
        loadDi();
    }

    private void loadBean(File file) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //1. 判断当前是否为文件夹
        if (file.isDirectory()) {   //true为文件夹
            //2. 获取文件夹下所有内容
            File[] childrenFiles = file.listFiles();
            //3. 如果文件夹内容为空直接返回
            if (childrenFiles == null || childrenFiles.length == 0) {
                return;
            }
            //4. 不为空循环遍历文件夹所有内容
            for (File child : childrenFiles) {
                //4.1 得到每个File对象 继续判断 如果还是文件夹递归执行
                if (child.isDirectory()) {
                    //递归
                    loadBean(child);
                } else {
                    //4.2 遍历File对象不是文件夹是文件
                    //4.3 得到包的路径+类名称部分
                    String pathWithClass = child.getAbsolutePath().substring(rootPath.length() - 1);
                    //4.4 判断当前文件类型是否是.class
                    if (pathWithClass.contains(".class")) {
                        //4.5 如果是。class文件吧路径\替换为. 把.class去掉
                        String allName = pathWithClass.replaceAll("\\\\", ".")
                                .replace(".class", "");
                        //4.6 判断类上是否有@Bean注解 如果有 实例化
                        //4.6.1 获取类Class对象
                        Class<?> clazz = Class.forName(allName);
                        //4.6.2 如果不是接口
                        if (!clazz.isInterface()) {
                            //4.6.3 判断类上是否有@Bean注解
                            Bean annotation = clazz.getAnnotation(Bean.class);
                            if (annotation != null) {
                                //4.6.4 实例化
                                Object instance = clazz.getConstructor().newInstance();
                                //4.7 把对象实例化之后放入beanFactory中
                                //4.7.1 判断当前类有接口 让接口class作为key
                                if (clazz.getInterfaces().length > 0) {
                                    beanFactory.put(clazz.getInterfaces()[0], instance);
                                } else {
                                    beanFactory.put(clazz, instance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //属性注入
    private void loadDi() {
        //实例化对象都在beanFactory的map集合中
        //1. 遍历map集合
        Set<Map.Entry<Class, Object>> entries = beanFactory.entrySet();
        for (Map.Entry<Class, Object> entry : entries) {
            //2. 获取map集合的每个对象 每个对象的属性获取到
            Object obj = entry.getValue();
            // 获取对象Class
            Class<?> clazz = obj.getClass();
            // 获取每个对象的睡醒
            Field[] declaredFields = clazz.getDeclaredFields();
            //3. 遍历每个对象属性数组 得到每个属性
            for (Field field : declaredFields) {
                //4. 判断属性上是否有@Di注解
                Di annotation = field.getAnnotation(Di.class);
                if (annotation != null) {
                    //如果是私有属性 进行私有属性设置
                    field.setAccessible(true);
                    //5. 如果有@Di注解 就把对象进行设置(注入)
                    try {
                        field.set(obj, beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
