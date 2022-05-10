package io.github.jiusiz.core;

import net.mamoe.mirai.event.Event;

/**
 * 声明此类可以由事件匹配
 *
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 8:02
 */
public interface MatchableEventHandlerMapping {
    boolean match(Event event, EventMatchInfo matchInfo);
}
