package io.github.jiusiz.core.handler;

import java.lang.reflect.Method;
import java.util.*;

import io.github.jiusiz.core.EventMappingAnnotationInfo;
import io.github.jiusiz.core.EventMappingInfo;
import io.github.jiusiz.core.annotation.EventController;
import io.github.jiusiz.core.annotation.method.EventMapping;
import io.github.jiusiz.core.exception.AnnotationNotFoundException;
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
public class MessageEventHandlerMapping extends AbstractEventHandlerMapping {

    private final Map<Long, List<EventMappingAnnotationInfo>> botIdMap = new HashMap<>();

    /**
     * 是否为本类需要的处理器
     */
    @Override
    protected boolean isHandler(Class<?> type) {
        return AnnotatedElementUtils.hasAnnotation(type, EventController.class);
    }

    /**
     * 是否为本类需要的处理器方法
     */
    @Override
    protected boolean isHandlerMethod(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, EventMapping.class);
    }

    /**
     * 创建注解匹配信息
     */
    @Override
    protected EventMappingAnnotationInfo createMessageEventInfo(Method method, Class<?> beanType) {
        // 获取类上面的EventController
        AnnotationAttributes eventController = AnnotatedElementUtils
                .getMergedAnnotationAttributes(beanType, EventController.class);

        // 获取方法EventMapping注解信息
        AnnotationAttributes eventMapping = AnnotatedElementUtils
                .getMergedAnnotationAttributes(method, EventMapping.class);

        if (eventController == null || eventMapping == null) {
            throw new AnnotationNotFoundException("获取注解信息失败");
        }

        Long botId = (Long) eventController.get("botId");
        String content = eventMapping.getString("content");
        Long sender = (Long) eventMapping.get("sender");
        String senderName = eventMapping.getString("senderName");
        Class<?> event = eventMapping.getClass("event");

        EventMappingAnnotationInfo info = new EventMappingAnnotationInfo(content, sender, senderName, event);
        // 加入botIdMap中
        if (this.botIdMap.containsKey(botId)) {
            List<EventMappingAnnotationInfo> list = this.botIdMap.get(botId);
            list.add(info);
            // 排序
            Collections.sort(list);
        } else {
            ArrayList<EventMappingAnnotationInfo> list = new ArrayList<>();
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
        Long botId = messageEventInfo.getBotId();
        List<EventMappingAnnotationInfo> mappingInfoList = this.botIdMap.get(botId);
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
    protected boolean isMessageEvent(Event event) {
        return event instanceof MessageEvent;
    }

    /**
     * 获取事件映射信息
     * @param messageEvent 消息事件
     * @return 事件的映射信息
     */
    protected EventMappingInfo getEventMappingInfo(MessageEvent messageEvent) {
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
