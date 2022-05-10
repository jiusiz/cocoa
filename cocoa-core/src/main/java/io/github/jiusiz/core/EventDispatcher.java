package io.github.jiusiz.core;

import net.mamoe.mirai.event.Event;
import org.springframework.context.ApplicationContext;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-07 下午 8:24
 */
public class EventDispatcher extends AbstractEventDispatcher {

    /**
     * 实际的调度方法
     *
     * @param event 事件
     */
    void dispatch(Event event) {
        // TODO 调度
    }

    @Override
    protected void onRefresh(ApplicationContext context) {
        // TODO 1.加载handler mapping
    }
}
