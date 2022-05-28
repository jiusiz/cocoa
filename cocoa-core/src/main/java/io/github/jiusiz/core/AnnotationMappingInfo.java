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

import net.mamoe.mirai.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-27 上午 10:48
 */
public interface AnnotationMappingInfo {

    /**
     * 排序接口
     * @param o AnnotationMappingInfo
     * @return 顺序大小
     */
    int compareFrom(@NotNull AnnotationMappingInfo o);

    /**
     * 是否与事件匹配
     * @param event 事件
     * @return 是否匹配
     */
    boolean match(Event event);

}
