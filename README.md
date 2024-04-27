## Spring

### P7：入门案例程序开发

 1. 创建父工程

 2. 创建子模块 引入spring相关依赖 spring-context

    ```xml
    <!-- Spring依赖 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.0.2</version>
    </dependency>
    ```

 3. 创建bean.xml文件夹 使用bena标签注入对象 参数为 id：对象的唯一标识 class：对象所在的全路径

 4. ClassPathXmlApplication context = new ClassPathXmlApplication()加载配置文件

 5. 使用Object obj = context.getBean("")获取对象 参数为name：对象的唯一标识

### P8：入门案例程序分析

1. 创建对象，无参构造执行？（执行）

2. 不用new关键字创建对象使用（反射）

   2.1 加载bean.xml配置文件

   2.2 对xml进行解析

   2.3 获取xml文件bean标签属性值id class

   2.4 使用反射根据类全路径创建对象

   ```java
   Class<?> clazz = Class.forName("com.sunl19ht.spring6.User");
   //老版本JDK创建对象方法 新版本已过时
   //   Object o = clazz.newInstance();
   
   //新版本创建对象方法
   User user = (User) clazz.getDeclaredConstructor().newInstance();
   System.out.println(user);
   ```

3. 创建对象放在哪里？（IOC容器）

```Java
org.springframework.beans.factory.support.DefaultListableBeanFactory
private final Map<String, BeanDefinition> beanDefinitionMap; //将对象放入Map集合
//key 唯一标识
//value 类的定义（描述信息：bena的名称 bean是单例/多例 bean的作用域范围）
```

### P9：整合Log4j2日志框架

1. 日志信息优先级

   ​	TRACE：追踪，最低日志级别

   ​	DEBUG：调试信息

   ​	INFO：信息 输出重要的信息 使用较多

   ​	WARN：警告 输出警告的信息

   ​	ERROR：错误 输出错误信息

   ​	FATAL：严重错误

   级别高的会自动屏蔽级别低的日志 例如设置WARN的知识 则 INFO，DEBUG的日志级别的日志不显示

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

 4. 加入日志配置文件 在类的根路径下提供log4j2.xml配置文件 （文件名固定为：log4j2.xml，文件必须在根路径下）

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

### P11：IoC概述
   #### IoC（Inversion of Control）控制反转

   Spring通过IoC容器管理所有Java对象的实例化和初始化 在IoC容器管理的对象成为Spring Bean或Bean，它与使用new关键字创建的对象没有任何区别

   IoC不是一种技术 而是一种思想 对象的创建和销毁都是通过IoC容器管理

1. xml中定义Bean的信息（BeanDefinition）

2. 通过BeanDefinitionReader进行读取配置文件

3. Bean的定义信息加载到IoC容器进行实例化（BeanFactory工厂+反射）初始化对象-->最终对象

#### DI（Dependency Injection）依赖注入

​	指Spring创建对象的过程中将对象依赖属性通过配置进行注入

	1. set注入
	1. 构造注入

#### IoC容器在Spring的实现

1. BeanFactory

   这是IoC容器的基本实现，是Spring内部使用的接口，面向Spring本身，不提供给开发人员

2. ApplicationContext

   这是BeanFactory的子接口，提供了更多的高级特性。面向Spring的使用者，几乎所有场合都是用ApplicationContext

### P15：XML管理Bean

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

获取bean的三种方式

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

根据类型获取Bean时 IoC容器中指定类型Bean只能有一个

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

如果组件类实现了接口可以通过接口获取bean 前提是bena唯一

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

 如果一个接口有多个实现类并且配置了bean则会报错 bean不唯一
