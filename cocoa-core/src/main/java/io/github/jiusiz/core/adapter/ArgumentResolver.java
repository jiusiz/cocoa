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
 * 参数解析器接口
 * 用于解析执行方法的参数，只负责一个参数
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 下午 2:37
 */
public interface ArgumentResolver {

    /**
     * 是否支持解析此参数
     * @param parameter 参数
     * @return 是否支持
     */
    boolean supportsArgument(MethodParameter parameter);

    /**
     * 解析参数
     * @param event 事件
     * @param parameter 原参数
     * @return 解析到的参数
     */
    Object resolveArgument(Event event, MethodParameter parameter);

}
