# 进阶用法

## 1. 注解部分

### 1.1 @EventController

使用`@EventController`注解将类作为事件控制器，参与事件的响应。

`botId`属性是必须要填写的，此id的机器人事件由这个控制器处理。

在`@EventController`中合并了`@Component`，会将其加入到spring容器中，便于cocoa管理。

```java
// 示例
@EventController(123456)
public class Xxx{
    // 其他代码
}
```

### 1.2 @EventMapping

`@EventMapping`注解需要与`@EventController`一起使用，`@EventMapping`只能在方法上使用，这个方法会真正地处理来自bot的事件。

```java
// 示例
@EventController(123456789)
public class Xxx {
    @EventMapping
    public void hello(Event event) {
        // 其他代码
    }
}
```

在上面的示例中，来自bot(123456789)的事件会由`Xxx.hello()`方法处理，本次的事件对象会成为方法的第一参数，你可以在方法内添加属于自己的逻辑代码。

`@EventMapping`默认处理的事件是`Event`，这是所有事件的统一接口，任何事件都会由它来处理。

不建议使用这种方式处理事件，尽量每一个方法只处理一个子类事件。

你可以指定注解的属性值来改变处理的事件。

- content：`String`类型，声明方法希望事件的消息正文和`content`匹配，使用正则模式匹配。
- sender：`long`类型，声明方法希望处理的事件发送者id。
- senderName：`String`类型，声明方法希望处理的事件发送者名字。
- event：`Class<?>`类型，声明方法希望处理的事件类型。

你可以使用下面的合成注解。

- `@FriendMessageMapping`：希望处理事件的类型为好友消息事件。
- `@GroupMessageMapping`：希望处理事件的类型为群消息事件。
