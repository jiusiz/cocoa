package io.github.jiusiz.core;

import io.github.jiusiz.core.method.HandlerMethod;
import net.mamoe.mirai.event.Event;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 7:29
 */
public interface HandlerMapping {
    HandlerMethod getHandler(Event event);
}
