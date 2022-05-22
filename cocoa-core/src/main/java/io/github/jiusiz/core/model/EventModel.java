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

package io.github.jiusiz.core.model;

/**
 * 存储事件返回数据的model
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 上午 10:44
 */
public class EventModel {

    private Object primitiveValue;

    private Class<?> eventClass;

    public EventModel(Object primitiveValue) {
        this.primitiveValue = primitiveValue;
    }

    public void setEventClass(Class<?> eventClass) {
        this.eventClass = eventClass;
    }
}
