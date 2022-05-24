# cocoa

注解开发风格的QQ机器人框架，类似Spring MVC的开发体验

## 快速开始

### 1.创建Spring Boot项目

使用以下的链接创建新的Spring Boot项目

[spring initializr](https://start.spring.io/)

**本项目基于JDK11构建，请不要使用JDK11以下的JDK版本**

### 2.引入依赖

maven

```xml
<dependency>
    <groupId>io.github.jiusiz</groupId>
    <artifactId>cocoa-spring-boot-starter</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

gradle

```kotlin
implementation("io.github.jiusiz:cocoa-spring-boot-starter:0.1.0-SNAPSHOT")
```

### 3.编写配置文件

在生成的application.yml配置文件中添加如下配置

```yaml
cocoa:
qq:
  - account: 机器人的QQ号
    password: 机器人的明文密码
```

### 4.编写事件Controller

新建一个controller包，并在下面创建HelloController.java

编写以下内容

```java
@EventController(机器人的QQ号)
public class HelloController {

    @FriendMessageMapping
    public void hello(FriendMessageEvent event) {
        System.out.println("Hello cocoa!");
        System.out.println(event.getMessage().contentToString());
    }
}
```

### 5.运行

运行Spring Boot的main方法，在登录提示登录成功之后，使用添加有机器人好友的账号向它发送信息

发送之后就会看到控制台打印出

> Hello cocoa!
>
> 你好

🎉 恭喜你，已经成创建了一个QQ机器人

### 6.进阶用法

[点击转到进阶用法文档]()

## 声明

本项目仅作为学习使用，请勿用作非法途径

## 鸣谢

[mirai](https://github.com/mamoe/mirai)

[spring-boot](https://github.com/spring-projects/spring-boot)
