/*
 * Copyright (C) 2022-2022 jiusiz.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.jiusiz.core.adapter;

import io.github.jiusiz.core.HandlerAdapter;
import io.github.jiusiz.core.method.HandlerMethod;
import io.github.jiusiz.core.model.EventModel;
import net.mamoe.mirai.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-16 下午 9:04
 */
public abstract class AbstractHandlerMethodAdapter implements HandlerAdapter, InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(Object handler) {
        // 询问子类是否支持
        return (handler instanceof HandlerMethod && supportsInternal((HandlerMethod) handler));
    }

    /**
     * 子类是否支持
     * @param handlerMethod 处理器方法
     * @return 是否支持
     */
    protected abstract boolean supportsInternal(HandlerMethod handlerMethod);

    @Override
    public EventModel handle(Event event, Object handler) throws Exception {
        return handleInternal(event, (HandlerMethod) handler);
    }

    protected abstract EventModel handleInternal(Event event, HandlerMethod handler) throws Exception;
}
