# cocoa

注解开发风格的QQ机器人框架，类似Spring MVC的开发体验

目前最新稳定版文档 -> [0.1.0](https://github.com/jiusiz/cocoa/tree/0.1.x#cocoa)

## 快速开始

### 1.创建Spring Boot项目

使用以下的链接创建新的Spring Boot项目

[spring initializr](https://start.spring.io/)

**本项目基于JDK11构建，请不要使用JDK11以下的JDK版本**

**Spring Boot版本不建议低于2.6**

### 2.引入依赖

maven

```xml
<dependency>
    <groupId>io.github.jiusiz</groupId>
    <artifactId>cocoa-spring-boot-starter</artifactId>
    <version>0.2.0-SNAPSHOT</version>
</dependency>
```

gradle

```kotlin
implementation("io.github.jiusiz:cocoa-spring-boot-starter:0.2.0-SNAPSHOT")
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

### 6.进阶示例

1. 监听指定事件

```java
@EventController(机器人的QQ号)
public class HelloController {
    //                    发送人QQ号            发送人昵称            正则表达式
    @FriendMessageMapping(sender = 123456789, senderName = "张三", content = "^你好.*")
    public void f(FriendMessageEvent event) {
        // 其他代码
    }
    //                   群号                发送人权限   ...... 剩余的和 @FriendMessageMapping 一致
    @GroupMessageMapping(group = 987654321, permission = MemberPermission.ADMINISTRATOR)
    public void g(){
        // 其他代码
    }
}
```

上面的例子中`f`方法只会监听来自`123456789`且发送人昵称为`张三`且消息以`你好`开始的好友消息事件。

上面的例子中`g`方法只会监听来自群`987654321`且发送人的权限为管理员的群消息事件。

2. 联系人装配

```java
@EventController(机器人的QQ号)
public class HelloController {
    //           好友QQ号
    @FriendWired(123456789)
    private Friend friend;
    //          群号
    @GroupWired(987654321)
    private Group group;

    @Scheduled(cron = "0 0/1 * * * ? ") // 需要在Spring Boot主类加上`@EnableScheduling`
    public void hello() {
        friend.sendMessage("你好 " + LocalDateTime.now());
        group.sendMessage("你好 " + LocalDateTime.now());
    }
}
```

上面的例子中，cocoa会在bot登录完成后，获取注解上标注的好友或群，注入到属性中。

配合Spring Boot的定时任务，简单快捷即可实现定时发送信息。

### 7.进阶文档

[点击转到进阶用法文档](https://github.com/jiusiz/cocoa/blob/main/Advanced.md)

## 声明

本项目仅作为学习使用，请勿用作非法途径

## 鸣谢

[mirai 高效率 QQ 机器人支持库](https://github.com/mamoe/mirai)

[Spring Boot](https://github.com/spring-projects/spring-boot)
