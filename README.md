# cocoa
注解开发风格的QQ机器人框架，类似Spring MVC的开发体验

## 快速开始

### 1.创建Spring Boot项目

在这个位置创建新的Spring Boot项目，不需要其他starter

[spring initializr](https://start.spring.io/)

**本项目基于JDK11构建，请不要使用JDK10及以下的JDK版本**

### 2.引入依赖

```xml
<dependency>
    <groupId>io.github.jiusiz</groupId>
    <artifactId>cocoa-spring-boot-starter</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

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
    public void hello() {
        System.out.println("Hello cocoa!");
    }
}
```

### 5.运行

运行Spring Boot的main方法，在登录提示登录成功之后，使用添加有机器人好友的账号向它发送信息

就会看到控制台打印出 
> Hello cocoa!

🎉 恭喜你，已经成创建了一个QQ机器人

## 声明
本项目仅作为学习使用，请勿用作非法途径

## 支持
[mirai](https://github.com/mamoe/mirai)

[spring-boot](https://github.com/spring-projects/spring-boot)
