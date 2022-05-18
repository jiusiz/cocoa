package io.github.jiusiz.core.adapter;

import io.github.jiusiz.core.method.MethodParameter;
import net.mamoe.mirai.event.Event;

/**
 * 参数解析器接口
 * 用于解析执行方法的参数，只负责一个参数
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 下午 2:37
 */
public interface ArgumentResolver {

    /**
     * 是否支持解析此参数
     * @param parameter 参数
     * @return 是否支持
     */
    boolean supportsArgument(MethodParameter parameter);

    /**
     * 解析参数
     * @param event 事件
     * @param parameter 原参数
     * @return 解析到的参数
     */
    Object resolveArgument(Event event, MethodParameter parameter);

}
