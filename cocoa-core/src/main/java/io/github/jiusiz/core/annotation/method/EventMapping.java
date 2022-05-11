package io.github.jiusiz.core.annotation.method;

import net.mamoe.mirai.event.Event;

import java.lang.annotation.*;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-11 下午 9:16
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventMapping {

    String content() default "";

    long sender() default 0;

    String senderName() default "";

    Class<?> event() default Event.class;

}
