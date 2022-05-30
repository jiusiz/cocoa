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

package io.github.jiusiz.core.adapter.resolver;

import io.github.jiusiz.core.adapter.HandlerMethodArgumentResolver;
import io.github.jiusiz.core.method.MethodParameter;
import net.mamoe.mirai.event.Event;

/**
 * 原始事件解析器
 * 如果参数为当前事件的父类，这返回原始事件
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-29 下午 5:50
 */
public class EventArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(Event event, MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(event.getClass());
    }

    @Override
    public Object resolverArgument(Event event, MethodParameter parameter) {
        return event;
    }
}
