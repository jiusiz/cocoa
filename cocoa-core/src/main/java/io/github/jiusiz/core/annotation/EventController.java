package io.github.jiusiz.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 声明此类为处理器，如果不填入botId 则不会使用
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-10 下午 3:06
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface EventController {

    @AliasFor("botId")
    long value() default 0;

    @AliasFor("value")
    long botId() default 0;
}
