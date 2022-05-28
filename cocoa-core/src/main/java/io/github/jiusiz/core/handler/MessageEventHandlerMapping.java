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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.jiusiz.core.EventMappingAnnotationInfo;
import io.github.jiusiz.core.EventMappingInfo;
import io.github.jiusiz.core.annotation.EventController;
import io.github.jiusiz.core.annotation.mapping.EventMapping;
import io.github.jiusiz.core.annotation.mapping.MessageMapping;
import io.github.jiusiz.core.exception.mapping.AnnotationNotFoundException;
import io.github.jiusiz.core.method.HandlerMethod;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.StringUtils;

/**
 * 只是用来保存和消息时间有关的处理器映射
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 9:41
 */
@Deprecated
public class MessageEventHandlerMapping extends AbstractEventHandlerMapping {

    private final Map<Long, List<EventMappingAnnotationInfo>> botIdMap = new HashMap<>();

    /**
     * 是否为本类需要的处理器
     * @param beanType 探测到的bean的class
     * @return 是否需要
     */
    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, EventController.class);
    }

    /**
     * 是否为本类需要的处理器方法
     * @param method 探测到的方法
     */
    @Override
    protected boolean isHandlerMethod(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, EventMapping.class);
    }

    /**
     * 创建注解匹配信息
     * @param method 方法
     * @param beanType bean类型
     * @return 注解映射信息
     */
    @Override
    protected EventMappingAnnotationInfo createMessageEventInfo(Method method, Class<?> beanType) {
        // 获取类EventController注解信息
        AnnotationAttributes eventController = AnnotatedElementUtils
                .getMergedAnnotationAttributes(beanType, EventController.class);

        // 获取方法EventMapping注解信息
        AnnotationAttributes messageMapping = AnnotatedElementUtils
                .getMergedAnnotationAttributes(method, MessageMapping.class);

        if (eventController == null || messageMapping == null) {
            throw new AnnotationNotFoundException("获取注解信息失败");
        }

        Long botId = (Long) eventController.get("botId");
        String content = messageMapping.getString("content");
        Long sender = (Long) messageMapping.get("sender");
        String senderName = messageMapping.getString("senderName");
        Class<?> event = messageMapping.getClass("event");

        EventMappingAnnotationInfo info = new EventMappingAnnotationInfo(content, sender, senderName, event);
        // 加入botIdMap中
        if (this.botIdMap.containsKey(botId)) {
            List<EventMappingAnnotationInfo> list = this.botIdMap.get(botId);
            list.add(info);
            // 排序
            Collections.sort(list);
        } else {
            ArrayList<EventMappingAnnotationInfo> list = new ArrayList<>();
            list.add(info);
            this.botIdMap.put(botId, list);
        }

        return info;
    }

    @Override
    protected HandlerMethod getHandlerInternal(Event event) {
        if (!isMessageEvent(event)) {
            return null;
        }

        MessageEvent ms = (MessageEvent) event;
        EventMappingInfo info = getEventMappingInfo(ms);

        EventMappingInfo.MessageEventInfo messageEventInfo = info.getMessageEventInfo();
        if (messageEventInfo == null) {
            return null;
        }
        Long botId = messageEventInfo.getBotId();
        List<EventMappingAnnotationInfo> mappingInfoList = this.botIdMap.get(botId);
        // 未找到符合bot的Controller
        if (mappingInfoList == null || mappingInfoList.isEmpty()) {
            return null;
        }
        EventMappingAnnotationInfo bestMappingInfo = getBestMappingInfo(ms, mappingInfoList);

        if (bestMappingInfo == null) {
            return null;
        }

        return super.getHandlerMethod(bestMappingInfo);
    }

    /**
     * 判断是否为消息事件
     * @param event 事件
     * @return 是否为消息事件
     */
    private boolean isMessageEvent(Event event) {
        return event instanceof MessageEvent;
    }

    /**
     * 获取事件映射信息
     * @param messageEvent 消息事件
     * @return 事件的映射信息
     */
    private EventMappingInfo getEventMappingInfo(MessageEvent messageEvent) {
        return new EventMappingInfo(messageEvent);
    }

    /**
     * 获取最佳映射信息
     */
    private EventMappingAnnotationInfo getBestMappingInfo(MessageEvent ms, List<EventMappingAnnotationInfo> mappingInfoList) {
        String miraiCode = ms.getMessage().serializeToMiraiCode();
        long senderId = ms.getSender().getId();
        String senderName = ms.getSenderName();
        Class<? extends MessageEvent> msClass = ms.getClass();

        for (EventMappingAnnotationInfo mappingInfo : mappingInfoList) {
            // 如果注解senderId有效，并且不相等，下一个
            if (mappingInfo.getSender() != 0 && mappingInfo.getSender() != senderId) {
                continue;
            }
            // 如果content有内容并且不匹配，下一个
            if (StringUtils.hasText(mappingInfo.getContent()) && !miraiCode.matches(mappingInfo.getContent())) {
                continue;
            }
            // 如果senderName有内容且不匹配，下一个
            if (StringUtils.hasText(mappingInfo.getSenderName()) && !senderName.matches(mappingInfo.getSenderName())) {
                continue;
            }
            // 如果EventClass不为空且不为默认值，并且不与当前事件匹配，下一个
            if (mappingInfo.getEventClass() != null && mappingInfo.getEventClass() != Event.class
                    && mappingInfo.getEventClass() != msClass) {
                continue;
            }
            // 如果是注解类型的子类，返回此MappingInfo
            if (mappingInfo.getEventClass() != null && mappingInfo.getEventClass().isAssignableFrom(msClass)) {
                return mappingInfo;
            }
            return mappingInfo;
        }
        return null;
    }

}
