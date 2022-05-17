package io.github.jiusiz.core.adapter.resolver;

import io.github.jiusiz.core.adapter.ArgumentResolver;
import io.github.jiusiz.core.method.HandlerMethod;
import io.github.jiusiz.core.method.MethodParameter;
import net.mamoe.mirai.event.Event;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 下午 6:57
 */
public class MessageEventArgumentResolver implements ArgumentResolver {
    @Override
    public boolean supportsArgument(MethodParameter parameter) {
        return false;
    }

    @Override
    public Object resolveArgument(Event event, HandlerMethod handler) {
        return null;
    }
}
