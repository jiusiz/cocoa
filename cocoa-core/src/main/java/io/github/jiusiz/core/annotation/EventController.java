package io.github.jiusiz.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 声明此类为处理器，由bot字段管理机器人域
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-10 下午 3:06
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EventController {

    /**
     * 声明此类是哪一个机器人的处理器
     */
    @AliasFor("botId")
    long value();

    @AliasFor("value")
    long botId() default 0;
}
