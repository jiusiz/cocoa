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

    private boolean handled;

    private boolean empty;

    @Deprecated
    private Object primitiveValue;

    private Class<?> eventClass;

    private String description;

    public EventModel() {
        this.empty = true;
        this.handled = false;
    }

    @Deprecated
    public EventModel(Object primitiveValue) {
        this.empty = false;
        this.primitiveValue = primitiveValue;
    }

    public void setEventClass(Class<?> eventClass) {
        this.empty = false;
        this.eventClass = eventClass;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
