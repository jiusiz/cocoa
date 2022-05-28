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

import java.util.Objects;

import io.github.jiusiz.core.exception.mapping.EqualsMappingException;
import net.mamoe.mirai.event.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-13 下午 3:50
 */
@Deprecated
public class EventMappingAnnotationInfo implements Comparable<EventMappingAnnotationInfo> {
    private String content;
    private Long sender;
    private String senderName;
    private Class<?> eventClass;

    private Integer sort;

    private EventMappingAnnotationInfo() {
    }

    public EventMappingAnnotationInfo(String content, Long sender, String senderName, Class<?> eventClass) {
        this.content = content;
        this.sender = sender;
        this.senderName = senderName;
        this.eventClass = eventClass;
        this.sort = 0;
        this.initSort();
    }

    private void initSort() {
        if (this.eventClass != Event.class) {
            this.sort++;
        }
        if (StringUtils.hasText(this.content)) {
            this.sort++;
        }
        if (this.sender > 10000) {
            this.sort++;
        }
        if (StringUtils.hasText(this.senderName)) {
            this.sort++;
        }
    }

    public String getContent() {
        return content;
    }

    public Long getSender() {
        return sender;
    }

    public String getSenderName() {
        return senderName;
    }

    public Class<?> getEventClass() {
        return eventClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventMappingAnnotationInfo that = (EventMappingAnnotationInfo) o;

        if (!Objects.equals(content, that.content)) return false;
        if (!Objects.equals(sender, that.sender)) return false;
        if (!Objects.equals(senderName, that.senderName)) return false;
        if (!Objects.equals(eventClass, that.eventClass)) return false;
        return Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (senderName != null ? senderName.hashCode() : 0);
        result = 31 * result + (eventClass != null ? eventClass.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NotNull EventMappingAnnotationInfo info) {
        if (this.equals(info)) {
            throw new EqualsMappingException("发现有两个同样的方法映射信息，无法确定使用哪一个");
        }
        int thisSort = this.sort;
        if (Objects.equals(this.sort, info.sort)) {
            if (this.content.length() > info.content.length()) {
                thisSort++;
            }
        }
        if (Objects.equals(this.sort, info.sort)) {
            if (this.sender > info.sender) {
                thisSort++;
            }
        }
        if (Objects.equals(this.sort, info.sort)) {
            if (this.senderName.length() > info.senderName.length()) {
                thisSort++;
            }
        }
        return info.sort - thisSort;
    }
}
