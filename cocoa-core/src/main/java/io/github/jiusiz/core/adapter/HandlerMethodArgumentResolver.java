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

import io.github.jiusiz.core.method.MethodParameter;
import net.mamoe.mirai.event.Event;

/**
 * 从事件中解析目标参数
 *
 * 参数解析器只负责一个参数的解析
 * 如果需要解析多个参数，需要多次使用
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-30 下午 4:39
 */
public interface HandlerMethodArgumentResolver {
    /**
     * 是否支持解析此参数
     * @param event 当前事件
     * @param parameter 当前方法的某一参数
     * @return 是否支持
     */
    boolean supports(Event event, MethodParameter parameter);

    /**
     * 解析此参数
     * @param event 当前事件
     * @param parameter 当前方法的某一参数
     * @return 参数
     */
    Object resolverArgument(Event event, MethodParameter parameter);

}
