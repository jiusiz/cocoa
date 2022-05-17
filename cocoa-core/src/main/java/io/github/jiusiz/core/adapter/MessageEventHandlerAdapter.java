package io.github.jiusiz.core.adapter;

import io.github.jiusiz.core.method.HandlerMethod;
import net.mamoe.mirai.event.Event;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 上午 10:30
 */
public class MessageEventHandlerAdapter extends AbstractHandlerAdapter {

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return true;
    }

    @Override
    protected void handleInternal(Event event, Object handler) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO: 2022-5-17 初始化参数解析器，返回值解析器
    }
}
