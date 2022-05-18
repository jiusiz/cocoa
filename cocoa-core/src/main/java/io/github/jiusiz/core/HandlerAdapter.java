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

    /**
     * 是否支持执行此handler
     * @param handler 处理器
     * @return 是否支持
     */
    boolean supports(Object handler);

    /**
     * 执行处理器，并收集返回数据
     * @param event 事件
     * @param handler 处理器
     * @return 事件返回信息
     */
    EventModel handle(Event event, Object handler);
}
