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
