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

package io.github.jiusiz.core.handler.info;

/**
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-27 下午 3:21
 */
public final class EventId {
    private final Long botId;
    private final Class<?> eventClass;

    public EventId(Long botId, Class<?> eventClass) {
        this.botId = botId;
        this.eventClass = eventClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventId eventId = (EventId) o;

        if (botId != null ? !botId.equals(eventId.botId) : eventId.botId != null) return false;
        return eventClass != null ? eventClass.equals(eventId.eventClass) : eventId.eventClass == null;
    }

    @Override
    public int hashCode() {
        int result = botId != null ? botId.hashCode() : 0;
        result = 31 * result + (eventClass != null ? eventClass.hashCode() : 0);
        return result;
    }
}
