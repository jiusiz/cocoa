package io.github.jiusiz.core.adapter;

import io.github.jiusiz.core.method.HandlerMethod;
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

    boolean supportsArgument(MethodParameter parameter);

    Object resolveArgument(Event event, HandlerMethod handler);

}
