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

    1.1 创建一个类 定义属性 生成属性set方法

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

    1.2 在spring配置文件配置

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

 2. 构造注入

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

    #### 特殊值处理

    1. 字面量赋值

       ```java
       int a = 10;
       String b = "abc";
       ```

    2. null值

       ```xml
           <bean id="book" class="com.sunl19ht.spring6.iocxml.di.Book">
               <property name="others">
                   <null/>
               </property>
           </bean>
       ```

    3. xml实体

       ```xml
       <property name="others" value="&lt;&gt;"/>
       ```

       ```java
       String others = "<>"
       ```

    4. CDATA节

       ```xml
       <property name="others">
       	<value>
               <![CDATA[a < b]]>
           </value>
       </property>
       ```

       

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

#### 特殊类型属性注入（对象类型属性注入，Map类型）

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

 1. 引用外部Bean

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

 2. 内部Bean

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

 3. 级联属性赋值

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

  #### 数组类型注入

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
  #### 集合属性注入

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
#### Map类型注入
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
#### 引用集合类型的bean
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
#### p命名空间注入
    导入P命名空间 xmlns:p="http://www.springframework.org/schema/p"
```xml
    <!-- p命名空间注入 -->
    <bean id="studentp" class="com.sunl19ht.spring6.iocxml.dimap.Student" p:sID="100" p:sName="sunl19ht" p:lessonsList-ref="lessonList" p:teacherMap-ref="teacherMap">
    
    </bean>
```



