package io.github.jiusiz.core;

import io.github.jiusiz.core.model.EventModel;
import net.mamoe.mirai.event.Event;

/**
 * 处理器适配器
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-16 下午 8:14
 */
public interface HandlerAdapter {

    boolean supports(Object handler);

    EventModel handle(Event event, Object handler);
}
