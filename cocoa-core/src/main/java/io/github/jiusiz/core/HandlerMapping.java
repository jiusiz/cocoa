package io.github.jiusiz.core;

import net.mamoe.mirai.event.Event;
import org.springframework.lang.Nullable;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 7:29
 */
public interface HandlerMapping {
    /**
     * 获取处理器
     * @param event 事件
     * @return 处理器
     */
    @Nullable
    Object getHandler(Event event);
}
