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

package io.github.jiusiz.core.handler;

import java.lang.reflect.Method;
import java.util.List;

import io.github.jiusiz.core.AnnotationMappingInfo;
import io.github.jiusiz.core.annotation.mapping.MessageMapping;
import io.github.jiusiz.core.exception.AnnotationNotFoundException;
import io.github.jiusiz.core.exception.EqualsMappingException;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StringUtils;

/**
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-27 下午 4:48
 */
public class MessageHandlerMapping extends AbstractHandlerMapping {

    @Override
    protected boolean isHandlerMethod(Method beanMethod) {
        return AnnotatedElementUtils.hasAnnotation(beanMethod, MessageMapping.class);
    }

    @Override
    protected AnnotationMappingInfo createAnnotationMappingInfo(Object bean, Method beanMethod) {

        MessageMapping messageMapping = AnnotatedElementUtils
                .getMergedAnnotation(beanMethod, MessageMapping.class);
        if (messageMapping == null) {
            throw new AnnotationNotFoundException("在" + bean.getClass().getName() + "." +
                    beanMethod.getName() + "上未找到MessageMapping注解，这可能是内部问题");
        }

        String description = bean.getClass().getName() + "." + beanMethod.getName() + "() ";

        return new MessageAnnotationMappingInfo(messageMapping.content(), messageMapping.sender(),
                messageMapping.senderName(), messageMapping.group(),
                messageMapping.permission(), messageMapping.event(), description);
    }

    @Override
    protected AnnotationMappingInfo getMappingInfo(List<AnnotationMappingInfo> mappingInfos, Event event) {
        if (mappingInfos == null || mappingInfos.isEmpty()) {
            return null;
        }
        for (AnnotationMappingInfo mappingInfo : mappingInfos) {
            if (mappingInfo.match(event)) {
                return mappingInfo;
            }
        }
        return null;
    }


    static class MessageAnnotationMappingInfo implements AnnotationMappingInfo,
            Comparable<MessageAnnotationMappingInfo> {

        private final String content;

        private final Long sender;

        private final String senderName;

        private final Long group;

        private final MemberPermission permission;

        private final Class<? extends MessageEvent> eventClass;

        private final String description;

        private int sort = 0;

        public MessageAnnotationMappingInfo(String content, Long sender, String senderName,
                Long group, MemberPermission permission, Class<? extends MessageEvent> event, String description) {

            this.content = content;
            if (StringUtils.hasText(content)) this.sort++;
            this.sender = sender;
            if (sender != 0) this.sort++;
            this.senderName = senderName;
            if (StringUtils.hasText(senderName)) this.sort++;
            this.group = group;
            if (group != 0) this.sort++;
            this.permission = permission;
            if (permission != MemberPermission.MEMBER) this.sort++;
            this.eventClass = event;
            if (event != MessageEvent.class) this.sort++;
            this.description = description;
        }

        @Override
        public boolean match(Event event) {

            if (!(event instanceof MessageEvent)) return false;
            MessageEvent me = (MessageEvent) event;

            // 事件类型
            if (eventClass != MessageEvent.class && eventClass != event.getClass()) return false;

            if (me instanceof GroupMessageEvent) {
                GroupMessageEvent gme = (GroupMessageEvent) me;
                // 群号
                if (group != 0 && group != gme.getGroup().getId()) return false;
                // 权限
                if (permission != MemberPermission.MEMBER && permission != gme.getPermission()) return false;
            }

            // 发送人
            if (sender != 0 && sender != me.getSender().getId()) return false;

            // 发送人昵称
            if (StringUtils.hasText(senderName) && !senderName.equals(me.getSenderName())) return false;

            // 正文正则匹配
            if (StringUtils.hasText(content) &&
                    !me.getMessage().serializeToMiraiCode().matches(content)) {
                return false;
            }

            return eventClass.isAssignableFrom(event.getClass());
        }

        @Override
        public int compareFrom(@NotNull AnnotationMappingInfo o) {
            return compareTo((MessageAnnotationMappingInfo) o);
        }

        @Override
        public int compareTo(@NotNull MessageAnnotationMappingInfo o) {

            int sort1 = this.sort;
            int sort2 = o.sort;
            if (sort2 - sort1 != 0) return sort2 - sort1;

            // 群号
            sort1 += compare(group, o.group);
            if (sort2 - sort1 != 0) return sort2 - sort1;

            // 权限
            sort1 += compare(permission.getLevel(), o.permission.getLevel());
            if (sort2 - sort1 != 0) return sort2 - sort1;

            // 发送人id
            sort1 += compare(sender, o.sender);
            if (sort2 - sort1 != 0) return sort2 - sort1;

            // 发送人昵称
            sort1 += compare(senderName.hashCode(), o.senderName.hashCode());
            if (sort2 - sort1 != 0) return sort2 - sort1;

            // 正文
            sort1 += compare(content.hashCode(), o.content.hashCode());
            if (sort2 - sort1 != 0) return sort2 - sort1;

            // 事件类型
            if (eventClass != o.eventClass) {
                return -1;
            }

            throw new EqualsMappingException(this.description + "与" + o.description + "的注解信息相同");
        }

        private int compare(int n1, int n2) {
            return Integer.compare(n1, n2);
        }

        private int compare(long n1, long n2) {
            return Long.compare(n1, n2);
        }
    }
}
