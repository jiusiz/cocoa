package io.github.jiusiz.core.handler;

import io.github.jiusiz.core.MessageEventInfo;
import io.github.jiusiz.core.annotation.EventHandler;
import io.github.jiusiz.core.method.HandlerMethod;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.Method;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 9:41
 */
public class MessageEventHandlerMapping extends AbstractEventHandlerMapping {

    @Override
    public HandlerMethod getHandler() {
        // TODO 等待实现
        return null;
    }

    @Override
    protected boolean isHandler(Class<?> type) {
        return AnnotatedElementUtils.hasAnnotation(type, EventHandler.class);
    }

    @Override
    protected boolean isHandlerMethod(Method method) {
        // TODO 添加新的注解
        return AnnotatedElementUtils.hasAnnotation(method, EventHandler.class);
    }

    @Override
    protected MessageEventInfo createMessageEventInfo(Method method, Class<?> beanType) {
        // TODO: 2022-5-10 更换新的方法注释
        AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(method, EventHandler.class);
        if (attributes != null && !attributes.isEmpty()) {
            // TODO: 2022-5-10 封装为MessageEventInfo
        }
        return null;
    }

}
