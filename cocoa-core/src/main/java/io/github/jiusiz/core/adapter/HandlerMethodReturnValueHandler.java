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

import io.github.jiusiz.core.method.ReturnType;
import io.github.jiusiz.core.model.EventModel;
import net.mamoe.mirai.event.Event;
import org.springframework.lang.Nullable;

/**
 * 处理器方法的返回值处理器
 * @author jiusiz
 * @since 0.2
 */
public interface HandlerMethodReturnValueHandler {

    /**
     * 是否支持返回值
     * @param returnType 返回参数
     * @param event 当前事件
     * @return 是否支持
     */
    boolean supportsReturnType(ReturnType returnType, Event event);

    /**
     * 处理返回值
     * @param model 事件模型
     * @param returnValue 返回值
     * @param returnType 返回类型
     * @param event 当期事件
     * @return 事件模型
     */
    EventModel handleReturnValue(@Nullable EventModel model, Object returnValue, ReturnType returnType, Event event);

}
