# 尚硅谷 Spring 零基础入门到进阶（笔记）BV1kR4y1b7Qc

# P7：入门案例程序开发

1. 创建父工程
2. 创建子模块引入spring相关依赖spring-context

```xml
<!-- Spring依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>6.0.2</version>
</dependency>
```

3.  创建bean.xml文件夹使用bena标签注入对象参数为id对象的唯一标识class对象所在的全路径

4.  加载配置文件 获取bena对象

```java
ClassPathXmlApplication context = new ClassPathXmlApplication(); //参数为配置文件名
Object obj = context.getBean(""); //参数为name
```

# P8：入门案例程序分析

1. 创建对象，无参构造执行？（执行）

2. 不用 new 关键字创建对象使用（反射）

   2.1 加载 bean.xml 配置文件

   2.2 对 xml 进行解析

   2.3 获取 xml 文件 bean 标签属性值 id class

   2.4 使用反射根据类全路径创建对象

   ```java
   Class<?> clazz = Class.forName("com.sunl19ht.spring6.User");
   //老版本JDK创建对象方法 新版本已过时
   //   Object o = clazz.newInstance();

   //新版本创建对象方法
   User user = (User) clazz.getDeclaredConstructor().newInstance();
   System.out.println(user);
   ```

3. 创建对象放在哪里？（IOC 容器）

    ```Java
    org.springframework.beans.factory.support.DefaultListableBeanFactory
    private final Map<String, BeanDefinition> beanDefinitionMap; //将对象放入Map集合
    //key 唯一标识
    //value 类的定义（描述信息：bena的名称 bean是单例/多例 bean的作用域范围）
    ```

# P9：整合 Log4j2 日志框架

1. 日志信息优先级

   ​ TRACE：追踪，最低日志级别

   ​ DEBUG：调试信息

   ​ INFO：信息 输出重要的信息 使用较多

   ​ WARN：警告 输出警告的信息

   ​ ERROR：错误 输出错误信息

   ​ FATAL：严重错误

   级别高的会自动屏蔽级别低的日志 例如设置 WARN 的知识 则 INFO，DEBUG 的日志级别的日志不显示

2. 日志信息的输出目的地（指定输出到控制台或文件中）

3. 日志输出的格式

   ```xml
   <!-- Log4j2-->
   <dependency>
       <groupId>org.apache.logging.log4j</groupId>
       <artifactId>log4j-core</artifactId>
       <version>2.19.0</version>
   </dependency>
   <dependency>
       <groupId>org.apache.logging.log4j</groupId>
       <artifactId>log4j-slf4j-impl</artifactId>
       <version>2.19.0</version>
   </dependency>
   ```

4. 加入日志配置文件 在类的根路径下提供 log4j2.xml 配置文件 （文件名固定为：log4j2.xml，文件必须在根路径下）

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <configuration>
       <loggers>
           <!--
               level指定日志级别，从低到高的优先级：
                   TRACE < DEBUG < INFO < WARN < ERROR < FATAL
                   trace：追踪，是最低的日志级别，相当于追踪程序的执行
                   debug：调试，一般在开发中，都将其设置为最低的日志级别
                   info：信息，输出重要的信息，使用较多
                   warn：警告，输出警告的信息
                   error：错误，输出错误信息
                   fatal：严重错误
           -->
           <root level="DEBUG">
               <appender-ref ref="spring6log"/>
               <appender-ref ref="RollingFile"/>
               <appender-ref ref="log"/>
           </root>
       </loggers>

       <appenders>
           <!--输出日志信息到控制台-->
           <console name="spring6log" target="SYSTEM_OUT">
               <!--控制日志输出的格式-->
               <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss SSS} [%t] %-3level %logger{1024} - %msg%n"/>
           </console>

           <!--文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定，适合临时测试用-->
           <File name="log" fileName="d:/spring6_log/test.log" append="false">
               <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
           </File>

           <!-- 这个会打印出所有的信息，
               每次大小超过size，
               则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，
               作为存档-->
           <RollingFile name="RollingFile" fileName="d:/spring6_log/app.log"
                        filePattern="log/$${date:yyyy-MM}/app-%d{MM-dd-yyyy}-%i.log.gz">
               <PatternLayout pattern="%d{yyyy-MM-dd 'at' HH:mm:ss z} %-5level %class{36} %L %M - %msg%xEx%n"/>
               <SizeBasedTriggeringPolicy size="50MB"/>
               <!-- DefaultRolloverStrategy属性如不设置，
               则默认为最多同一文件夹下7个文件，这里设置了20 -->
               <DefaultRolloverStrategy max="20"/>
           </RollingFile>
       </appenders>
   </configuration>
   ```

5. 手动写日志

   ```java
   import org.slf4j.Logger;
   //创建Logger对象
   private Logger logger = LoggerFactory.getLogger(); //参数为当前类
   logger.info("") //参数为日志内容
   ```

# P11：IoC 概述

## IoC（Inversion of Control）控制反转

Spring 通过 IoC 容器管理所有 Java 对象的实例化和初始化 在 IoC 容器管理的对象成为 Spring Bean 或 Bean，它与使用 new 关键字创建的对象没有任何区别

IoC 不是一种技术 而是一种思想 对象的创建和销毁都是通过 IoC 容器管理

1. xml 中定义 Bean 的信息（BeanDefinition）

2. 通过 BeanDefinitionReader 进行读取配置文件

3. Bean 的定义信息加载到 IoC 容器进行实例化（BeanFactory 工厂+反射）初始化对象-->最终对象

## DI（Dependency Injection）依赖注入

​ 指 Spring 创建对象的过程中将对象依赖属性通过配置进行注入

1.  set 注入

    1.1 创建一个类 定义属性 生成属性 set 方法

    ```java
    private String bName; //书名
    private String author; //作者

    public Book() {}

    public Book(String bName, String author) {
        this.bName = bName;
        this.author = author;
    }

    public String getbName() {
        return bName;
    }

    public String getAuthor() {
        return author;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    ```

    1.2 在 spring 配置文件配置

    ```xml
    <!-- 1. 基于 set 方法完成注入 -->
    <bean id="book" class="com.sunl19ht.spring6.iocxml.di.Book">
    	<!-- name：为get set方法对应的名字 首字母小写 -->
        <property name="bName" value="Spring 5.0 开发指南"/>
        <property name="author" value="孙龙"/>
    </bean>
    ```

    ```java
    @Test
    public void testSet() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-di.xml");
        Book book = context.getBean(Book.class);
        System.out.println(book);
    }
    ```

2.  构造注入

    创建类 定义属性 生成有参构造

    ```java
    public Book(String bName, String author) {
        this.bName = bName;
        this.author = author;
    }
    ```

    进行配置

    ```xml
    <!-- 2. 构造器注入 -->
    <bean id="bookCon" class="com.sunl19ht.spring6.iocxml.di.Book">
        <constructor-arg name="bName" value="Spring 5.0 开发指南"/>
        <!-- 可以通过index指定参数位置 -->
        <!-- <constructor-arg index="0" value="Spring 5.0 开发指南"/> </constructor-arg>-->

        <constructor-arg name="author" value="孙龙"/>
    </bean>
    ```

    ## 特殊值处理

    1. 字面量赋值

       ```java
       int a = 10;
       String b = "abc";
       ```

    2. null 值

       ```xml
           <bean id="book" class="com.sunl19ht.spring6.iocxml.di.Book">
               <property name="others">
                   <null/>
               </property>
           </bean>
       ```

    3. xml 实体

       ```xml
       <property name="others" value="&lt;&gt;"/>
       ```

       ```java
       String others = "<>"
       ```

    4. CDATA 节

       ```xml
       <property name="others">
       	<value>
               <![CDATA[a < b]]>
           </value>
       </property>
       ```

## IoC 容器在 Spring 的实现

1. BeanFactory

   这是 IoC 容器的基本实现，是 Spring 内部使用的接口，面向 Spring 本身，不提供给开发人员

2. ApplicationContext

   这是 BeanFactory 的子接口，提供了更多的高级特性。面向 Spring 的使用者，几乎所有场合都是用 ApplicationContext

# P15：XML 管理 Bean

<bean id="bean的唯一标识" class="bean的全路径"></bean>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 创建User类的对象 -->
    <bean id="user" class="com.sunl19ht.spring6.iocxml.User"></bean>
</beans>
```

获取 bean 的三种方式

```java
//读取配置文件
ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
//1. 根据id获取bean
User user = (User) context.getBean("user");
System.out.println("根据id获取bean：" + user);

//2. 根据类型获取bean
User user2 = context.getBean(User.class);
System.out.println("根据类型获取bean：" + user2);

//3. 根据id和类型获取bean
User user3 = context.getBean("user", User.class);
System.out.println("根据id和类型获取bean：" + user3);
```

根据类型获取 Bean 时 IoC 容器中指定类型 Bean 只能有一个

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 创建User类的对象 -->
    <bean id="user" class="com.sunl19ht.spring6.iocxml.User"></bean>
    <bean id="user1" class="com.sunl19ht.spring6.iocxml.User"></bean>
</beans>
```

如果组件类实现了接口可以通过接口获取 bean 前提是 bena 唯一

```java
public interface UserDao {
    void run();
}
```

```java
public class UserDaoImpl implements UserDao{
    @Override
    public void run() {
        System.out.println("UserDaoImpl run...");
    }
}
```

```java
public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

    UserDao userDao = context.getBean(UserDao.class);
    userDao.run();
    System.out.println(userDao);
}
```

如果一个接口有多个实现类并且配置了 bean 则会报错 bean 不唯一

## 特殊类型属性注入（对象类型属性注入，Map 类型）

```java
public class Dept {
    //部门名称
    private String dName;
    public void info() {
        System.out.println("部门名称：" + dName);
    }
}
public class Emp {
    //标识员工属于某个部门
    private Dept dept;
    private String eName;
    private Integer age;

    public void work() {
        System.out.println("员工" + eName + "年龄" + age + "正在工作");
    }
}
```

1.  引用外部 Bean

    ```xml
    <bean id="dept" class="com.sunl19ht.spring6.iocxml.ditest.Dept">
        <property name="dName" value="财务部"/>
    </bean>
    <bean id="emp" class="com.sunl19ht.spring6.iocxml.ditest.Emp">
        <property name="eName" value="张三"/>
        <property name="age" value="18"/>
        <!-- 对象类型注入 name：setDept首字母小写 ref：bean的id值-->
        <property name="dept" ref="dept"/>
    </bean>
    ```

2.  内部 Bean

    ```xml
    <!--
    方式2：内部bean注入
    	将bean的定义卸载 property里面
    -->
    <bean id="emp2" class="com.sunl19ht.spring6.iocxml.ditest.Emp">
        <property name="eName" value="李四"/>
        <property name="age" value="20"/>
        <property name="dept">
            <bean id="dept2" class="com.sunl19ht.spring6.iocxml.ditest.Dept">
                <property name="dName" value="管理部"/>
            </bean>
        </property>
    </bean>
    ```

3.  级联属性赋值

    ```xml
    <!--
    方式3：级联赋值
    -->
    <bean id="dept3" class="com.sunl19ht.spring6.iocxml.ditest.Dept">
    	<property name="dName" value="开发部"/>
    </bean>
    <bean id="emp3" class="com.sunl19ht.spring6.iocxml.ditest.Emp">
        <property name="eName" value="Tom"/>
        <property name="age" value="30"/>
        <property name="dept" ref="dept3"/>
        <property name="dept.dname" value="测试部"/>
    </bean>
    ```

## 数组类型注入

```xml
<bean id="dept" class="com.sunl19ht.spring6.iocxml.ditest.Dept">
	<property name="dName" value="技术部"/>
</bean>
<bean id="emp" class="com.sunl19ht.spring6.iocxml.ditest.Emp">
	<!-- 普通属性 -->
	<property name="eName" value="张三"/>
	<property name="age" value="18"/>
	<!-- 对象类型属性 -->
	<property name="dept" ref="dept"/>
	<!-- 注入数组类型 -->
	<property name="loves">
        <array>
            <value>吃饭</value>
            <value>睡觉</value>
            <value>Codeing</value>
        </array>
	</property>
</bean>
```

## 集合属性注入

```xml
<bean id="emp1" class="com.sunl19ht.spring6.iocxml.ditest.Emp">
        <property name="eName" value="张三"/>
        <property name="age" value="18"/>
        <property name="loves">
            <list>
                <value>吃饭</value>
                <value>睡觉</value>
                <value>Coding...</value>
            </list>
        </property>
    </bean>

    <bean id="emp2" class="com.sunl19ht.spring6.iocxml.ditest.Emp">
        <property name="eName" value="李四"/>
        <property name="age" value="20"/>
        <property name="loves">
            <list>
                <value>抽烟</value>
                <value>喝酒</value>
                <value>烫头</value>
                <value>Coding...</value>
            </list>
        </property>
    </bean>

    <bean id="dept" class="com.sunl19ht.spring6.iocxml.ditest.Dept">
        <property name="dName" value="技术部"/>
        <!-- 注入集合 -->
        <property name="empList">
            <list>
                <!-- 如果是String类型可以写value -->
                <ref bean="emp1"/>
                <ref bean="emp2"/>
            </list>
        </property>
    </bean>
```

## Map 类型注入

```xml
 <!-- 创建老师对象 -->
<bean id="teacher1" class="com.sunl19ht.spring6.iocxml.dimap.Teacher">
    <property name="teacherId" value="1"/>
    <property name="teacherName" value="张老师"/>
</bean>
<bean id="teacher2" class="com.sunl19ht.spring6.iocxml.dimap.Teacher">
    <property name="teacherId" value="2"/>
    <property name="teacherName" value="李老师"/>
    </bean>
    <!-- 创建学生对象 -->
    <bean id="student1" class="com.sunl19ht.spring6.iocxml.dimap.Student">
    <property name="sID" value="1"/>
    <property name="sName" value="张三"/>
    <!-- 注入 map集合 -->
    <property name="teacherMap">
        <!-- 注入 map集合 -->
<!--        <map>-->
<!--            <entry key="1" value-ref="teacher1"/>-->
<!--            <entry key="2" value-ref="teacher2"/>-->
<!--        </map>-->
        <map>
            <entry>
                <key>
                    <value>1</value> <!-- 指定key值 -->
                </key>>
                <ref bean="teacher1"/> <!-- 指定value值 -->
            </entry>
            <entry>
                <key>
                    <value>2</value> <!-- 指定key值 -->
                </key>>
                <ref bean="teacher2"/> <!-- 指定value值 -->
            </entry>
        </map>>
    </property>
</bean>
```

## 引用集合类型的 bean

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/util
       http://www.springframework.org/schema/util/spring-util.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--
        1. 创建三个对象
        2. 注入普通类型属性
        3. 使用util：类型 定义
        4. 在学生bean引入util：定义bean 完成list map属性注入
    -->
    <util:list id="lessonList">
        <ref bean="lesson1"/>
        <ref bean="lesson2"/>
    </util:list>
    <util:map id="teacherMap">
        <entry>
            <key>
                <value>10010</value>
            </key>
            <ref bean="teacher1"></ref>
        </entry>
        <entry>
            <key>
                <value>10086</value>
            </key>
            <ref bean="teacher2"></ref>
        </entry>
    </util:map>
    <bean id="lesson1" class="com.sunl19ht.spring6.iocxml.dimap.Lesson">
        <property name="lessonName" value="java开发"/>
    </bean>
    <bean id="lesson2" class="com.sunl19ht.spring6.iocxml.dimap.Lesson">
        <property name="lessonName" value="前端开发"/>
    </bean>

    <bean id="teacher1" class="com.sunl19ht.spring6.iocxml.dimap.Teacher">
        <property name="teacherId" value="100"/>
        <property name="teacherName" value="张老师"/>
    </bean>
    <bean id="teacher2" class="com.sunl19ht.spring6.iocxml.dimap.Teacher">
        <property name="teacherId" value="200"/>
        <property name="teacherName" value="李老师"/>
    </bean>

    <bean id="student" class="com.sunl19ht.spring6.iocxml.dimap.Student">
        <property name="sID" value="1001"/>
        <property name="sName" value="张三"/>
        <property name="lessonsList" ref="lessonList"/>
        <property name="teacherMap" ref="teacherMap"/>
    </bean>
</beans>
```

## p 命名空间注入

    导入P命名空间 xmlns:p="http://www.springframework.org/schema/p"

```xml
    <!-- p命名空间注入 -->
    <bean id="studentp" class="com.sunl19ht.spring6.iocxml.dimap.Student" p:sID="100" p:sName="sunl19ht" p:lessonsList-ref="lessonList" p:teacherMap-ref="teacherMap">

    </bean>
```

## P31：引入外部配置文件

## 数据库外部文件

1. 引入数据库相关依赖

```xml
<!-- MySQL驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.30</version>
</dependency>

<!-- 数据源 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.2.15</version>
</dependency>
```

2. 创建外部配置文件，properties 格式，定义数据信息 用户名 密码 地址等...

```
    jdbc.user=root
    jdbc.password=123456
    jdbc.url=jdbc:mysql://localhost:3306/spring?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    jdbc.driver=com.mysql.cj.jdbc.Driver
```

3. 创建 spring 的配置文件 引入 context 命名空间 引入属性文件 使用表达式完成注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
    <!-- 引入外部属性文件 -->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!-- 完成数据库信息注入 -->
    <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="username" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
</beans>
```

## 32：Bean 的作用域

1. 概念
   在 Spring 中可以通过配置 bean 标签的 scope 属性来指定 bena 的作用域
   ```xml
   <bean id="user" class="com.sunl19ht.spring6.iocxml.life.User" scope="singleton" init-method="initMethod" destroy-method="destroyMethod">
   scope 设置 bean 的作用域
   </bean>

   ````

    singleton：单例模式（默认） 在IoC容器中这个bena的对象始终为单例 IoC容器初始化时创建

    prototype：多例模式  这个bena在IoC容器中有多个实例  在bean被获取时创建

   2. bean生命周期

       2.1 bean对象创建（调用无参构造）

       2.2 给bean对象设置相关属性

       2.3 bean后置处理器（初始化之前）
        ```xml
       <!-- bean的后置处理器要放入ioc容器才能生效-->
       <bean id="myBeanPost" class="com.sunl19ht.spring6.iocxml.life.MyBeanPost"/>
      ```

      ```java
      public class MyBeanPost implements BeanPostProcessor {
          @Override
          public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
          }
      }
        ```

       2.4 bean对象初始化

       2.5 bean后置处理器（初始化之后）
        ```xml
       <!-- bean的后置处理器要放入ioc容器才能生效-->
       <bean id="myBeanPost" class="com.sunl19ht.spring6.iocxml.life.MyBeanPost"/>
      ```

       ```java
           public class MyBeanPost implements BeanPostProcessor {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
            }
        }
        ```

        2.6 bean对象创建完成

        2.7 bean销毁（配置指定销毁方法）

        2.8 IoC容器关闭

        ```xml
        <bean id="user" class="com.sunl19ht.spring6.iocxml.life.User" scope="singleton" init-method="initMethod" destroy-method="destroyMethod">
            init-method设置bean的初始化方法
            destroy-method设置bean的销毁方法
        </bean>
        ```

   ## 36：自动装配
    使用自动装配实现

    不需要自己手动注入

    controller <-> service <-> dao

    controller：
        定义service类型属性
        生成service属性的set方法

    service：
        定义dao类型属性
        生成dao属性的set方法

    autowire="byType" 根据类型自动注入
    autowire="byName" 根据属性名自动注入
    ```xml
        <bean id="userController" class="com.sunl19ht.spring6.iocxml.auto.controller.UserController" autowire="byType">
        </bean>

        <bean id="userService" class="com.sunl19ht.spring6.iocxml.auto.service.UserServiceImpl" autowire="byType">
        </bean>

        <bean id="userDao" class="com.sunl19ht.spring6.iocxml.auto.dao.UserDaoImpl" autowire="byType">
        </bean>
    ```

    如果在IoC中没有找到任何一个兼容的bean则值为空

    如果IoC中找到多个兼容的bean则抛出异常NoUniqueBeanDefinitionException

    根据名称注入

    ```java
    UserController userController = new UserControllerImpl();
    ```
    ```xml
    要保证id标签的值和对象的变量名一致
    <bean id="userController" class="com.sunl19ht.spring6.iocxml.auto.controller.UserController" autowire="byName">
    </bean>
    ```
   ````

## 38：Spring 注解属性注入 bean

1. 引入依赖
2. 开启组件扫描

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd">
    <!--开启组件扫描功能-->
    <!-- base-package：要扫描的包 指定的包及其子包下的所有类都会被扫描 -->
    <context:component-scan base-package="com.sunl19ht.spring6"></context:component-scan>
</beans>
```

| 注解         | 说明                                                                                                                                                                                    |
| ------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| @Component   | 该注解用于描述 Spring 中的 Bean，它是一个泛化的概念，仅仅表示容器中的一个组件（Bean），并且可以作用在应用的任何层次，例如 Service 层、Dao 层等。 使用时只需将该注解标注在相应类上即可。 |
| @Repository  | 该注解用于将数据访问层（Dao 层）的类标识为 Spring 中的 Bean，其功能与 @Component 相同。                                                                                                 |
| @Service     | 该注解通常作用在业务层（Service 层），用于将业务层的类标识为 Spring 中的 Bean，其功能与 @Component 相同。                                                                               |
| @@Controller | 该注解通常作用在控制层（如 SpringMVC 的 Controller），用于将控制层的类标识为 Spring 中的 Bean，其功能与 @Component 相同。                                                               |

## 39：Spring @Autowired 注解注入 bean

1. bean 对象创建

```java
@Service
public class UserServiceImpl implements UserService {

}
```

2. 定义相关属性，在属性上添加注解

```java
// 方式1
@Autowired
UserService userService;

// 方式2 set方法
private UserService userService;
@Autowired
public void setUserService(UserService userService) {
    this.userService = userService;
}

//方法3 构造方法
private UserService userService;

@Autowired
public UserController(UserService userService) {
    this.userService = userService;
}

//方法4 形参注入
private UserService userService;
public UserController(@Autowired UserService userService) {
    this.userService = userService;
}
```

当有参数的构造方法只有一个时，@Autowired 可以省略

```java
//方法5 当有参数的构造方法只有一个时，@Autowired可以省略
private UserService userService;

public UserController(UserService userService) {
    this.userService = userService;
}
```

@Autowired + @Qualifier(value = "属性名") 根据名称注入

```java
@Repository
public class RedisDaoImpl implements UserDao{
    @Override
    public void add() {
        System.out.println("redis add");
    }
}
// 最后的方式 使用两个注解根据名称进行注入
@Autowired
@Qualifier(value = "redisDaoImpl")
private UserDao userDao;
```

## 42：@Resource 注入

@Resource 注解是 JDK 扩展包中的 不是 Spring 提供
@Resource 只能用在 setter 方法上
JDK 版本高于 8 或 JDK 高于 11 需要引入依赖

```xml
<dependency>
    <groupId>jakarta.annotation</groupId>
    <artifactId>jakarta.annotation-api</artifactId>
    <version>2.1.1</version>
</dependency>
```

```java
@Controller("myUserController")
public class UserController {
    //根据名称进行注入
//    @Resource(name = "myUserService") //默认根据名称进行装配byName 未指定name使用属性名作为name 通过name找不要到会自动启动通过类型装配
//    private UserService userService;

    //根据类型匹配
    @Resource
    private UserService userService;

    public void add() {
        System.out.println("Controller.....");
        userService.add();
    }
}

```

```java
@Service("myUserService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao myUserDao;
    @Override
    public void add() {
        myUserDao.add();
    }
}
```

## 43：全注解开发 使用配置类代替配置文件

1. 创建配置类 开启组件扫描

```java
@Configuration
@ComponentScan("com.sunl19ht.spring6") //<context:component-scan base-package="com.sunl19ht.spring6"></context:component-scan>
public class SpringConfig {
}
```

2. 启用配置类

```java
public class TestUserControllerAnno {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserController controller = context.getBean(UserController.class);
        controller.add();
    }
}
```

## P44 Java反射
```java
package com.sunl19ht.reflect;

public class Car {
    private String name;
    private int age;
    private String color;

    private void run() {
        System.out.println("private run...");
    }

    //无参数构造
    public Car() {
    }

    //有参数构造
    public Car(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
```
```java
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
```
## P47：手写IoC容器
### 1. 创建子模块sunl19ht-spring
### 2. 创建测试类service dao
### 3. 创建两个注解@Bean创建对象 @Di属性注入
```java
package com.sunl19ht.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
```
```java
package com.sunl19ht.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Di {
}
```
### 4. 创建bean接口ApplicationContext
```java
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
```
### 5. 实现bean容器接口 
#### 5.1 返回对象 
#### 5.2 根据包规则加载bean
## P54：AOP代理模式
### 静态代理
```java
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
```
### 动态代理
```java
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
```