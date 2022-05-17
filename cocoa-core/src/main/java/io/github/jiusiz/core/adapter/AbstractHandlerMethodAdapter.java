package io.github.jiusiz.core.adapter;

import io.github.jiusiz.core.HandlerAdapter;
import io.github.jiusiz.core.method.HandlerMethod;
import net.mamoe.mirai.event.Event;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-16 下午 9:04
 */
public abstract class AbstractHandlerMethodAdapter implements HandlerAdapter, InitializingBean {

    @Override
    public boolean supports(Object handler) {
        // 询问子类是否支持
        return (handler instanceof HandlerMethod && supportsInternal((HandlerMethod) handler));
    }

    /**
     * 子类是否支持
     */
    protected abstract boolean supportsInternal(HandlerMethod handlerMethod);

    @Override
    public void handle(Event event, Object handler) {
        handleInternal(event, (HandlerMethod)handler);
    }

    protected abstract void handleInternal(Event event, HandlerMethod handler);
}
