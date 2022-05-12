package io.github.jiusiz.core.handler;

import io.github.jiusiz.core.EventMappingInfo;
import io.github.jiusiz.core.MessageEventMappingInfo;
import io.github.jiusiz.core.annotation.EventController;
import io.github.jiusiz.core.annotation.method.EventMapping;
import io.github.jiusiz.core.method.HandlerMethod;
import net.mamoe.mirai.event.Event;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.Method;

/**
 * 只是用来保存和消息时间有关的处理器映射
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 9:41
 */
public class MessageEventHandlerMapping extends AbstractEventHandlerMapping {

    @Override
    protected boolean isHandler(Class<?> type) {
        return AnnotatedElementUtils.hasAnnotation(type, EventController.class);
    }

    @Override
    protected boolean isHandlerMethod(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, EventMapping.class);
    }

    @Override
    protected MessageEventMappingInfo createMessageEventInfo(Method method, Class<?> beanType) {
        // 获取方法EventMapping注解信息
        AnnotationAttributes eventMapping = AnnotatedElementUtils
                .getMergedAnnotationAttributes(method, EventMapping.class);

        // 获取类上面的EventController
        AnnotationAttributes eventController = AnnotatedElementUtils
                .getMergedAnnotationAttributes(beanType, EventController.class);

        MessageEventMappingInfo mappingInfo = null;
        if (eventMapping != null && !eventMapping.isEmpty() && eventController != null) {
            mappingInfo = new MessageEventMappingInfo.Builder()
                    .sender((Long) eventMapping.get("sender"))
                    .senderName(eventMapping.getString("senderName"))
                    .event(eventMapping.getClass("event"))
                    .content(eventMapping.getString("content"))
                    .botId((Long) eventController.get("botId"))
                    .build();
        }
        return mappingInfo;
    }

    @Override
    protected HandlerMethod getHandlerInternal(Event event) {
        // TODO: 2022-5-12 等待实现
        return null;
    }

    @Override
    protected EventMappingInfo getRealEventMappingInfo(Event event) {
        // TODO: 2022-5-12 创建实际事件映射信息
        return null;
    }
}
