package io.github.jiusiz.core.annotation.method;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-11 下午 9:39
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EventMapping(event = FriendMessageEvent.class)
public @interface FriendMessageMapping {

    @AliasFor(annotation = EventMapping.class)
    String senderName() default "";

    @AliasFor(annotation = EventMapping.class)
    long sender() default 0;

    @AliasFor(annotation = EventMapping.class)
    String content() default "";

}
