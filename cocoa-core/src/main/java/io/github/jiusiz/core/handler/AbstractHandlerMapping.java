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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.jiusiz.core.AnnotationMappingInfo;
import io.github.jiusiz.core.HandlerMapping;
import io.github.jiusiz.core.annotation.EventController;
import io.github.jiusiz.core.annotation.mapping.EventMapping;
import io.github.jiusiz.core.exception.mapping.AnnotationNotFoundException;
import io.github.jiusiz.core.handler.info.EventId;
import io.github.jiusiz.core.method.HandlerMethod;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.BotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-26 下午 9:14
 */
public abstract class AbstractHandlerMapping implements HandlerMapping, InitializingBean, ApplicationContextAware {

    protected final Map<EventId, List<AnnotationMappingInfo>> eventIdMappingInfoMap = new HashMap<>();

    protected final Map<AnnotationMappingInfo, HandlerMethod> handlerMethodMap = new HashMap<>();

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        initHandlerMethod(context);
    }

    /**
     * 初始化HandlerMethod
     * @param context 上下文容器
     */
    private void initHandlerMethod(ApplicationContext context) {
        String[] beanNames = BeanFactoryUtils
                .beanNamesForAnnotationIncludingAncestors(context, EventController.class);
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            processBean(bean);
        }

        // 对eventIdToMappingInfoMap排序
        for (List<AnnotationMappingInfo> mappingInfos : eventIdMappingInfoMap.values()) {
            // 排序，保持多条件映射信息在前
            mappingInfos.sort(AnnotationMappingInfo::compareFrom);
        }
    }

    /**
     * 处理bean中的方法
     * @param bean bean
     */
    private void processBean(Object bean) {

        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());

        // 寻找每一个方法
        for (Method method : methods) {
            if (AnnotatedElementUtils.hasAnnotation(method, EventMapping.class)
                    && isHandlerMethod(method)) {

                EventController eventController = AnnotatedElementUtils
                        .getMergedAnnotation(bean.getClass(), EventController.class);
                EventMapping eventMapping = AnnotatedElementUtils
                        .getMergedAnnotation(method, EventMapping.class);

                if (eventController == null) {
                    throw new AnnotationNotFoundException(bean.getClass().getName() +
                            "." + method.getName() + "() 上未找到注解EventController，这可能是内部问题");
                }
                if (eventMapping == null) {
                    throw new AnnotationNotFoundException(bean.getClass().getName() +
                            "." + method.getName() + "() 上未找到EventMapping，这可能是内部问题");
                }
                Class<? extends Event> event = eventMapping.event();
                long botId = eventController.botId();

                EventId eventId = new EventId(botId, event);
                AnnotationMappingInfo mappingInfo = createAnnotationMappingInfo(bean, method);

                if (eventIdMappingInfoMap.containsKey(eventId)) {
                    List<AnnotationMappingInfo> mappingInfos = eventIdMappingInfoMap.get(eventId);
                    mappingInfos.add(mappingInfo);
                } else {
                    ArrayList<AnnotationMappingInfo> mappingInfoList = new ArrayList<>();
                    mappingInfoList.add(mappingInfo);
                    eventIdMappingInfoMap.put(eventId, mappingInfoList);
                }

                // 创建HandlerMethod
                HandlerMethod handlerMethod = new HandlerMethod(bean, method);
                // 保存到map中
                handlerMethodMap.put(mappingInfo, handlerMethod);
            }
        }
    }

    /**
     * 子类实现的是否为处理器方法
     * 每个不同的子类应该使用不同的逻辑，不应该出现一个方法属于多个映射
     * @param beanMethod 方法
     * @return 是否为处理器方法
     */
    protected abstract boolean isHandlerMethod(Method beanMethod);

    /**
     * 子类实现，创建AnnotationMappingInfo
     * 每一个子类的AnnotationMappingInfo实现不尽相同，应该避免再次创建AnnotationMappingInfo
     * （主要是因为AnnotationMappingInfo没有equals方法）
     * @param bean bean
     * @param beanMethod bean的方法
     * @return 注解映射信息
     */
    protected abstract AnnotationMappingInfo createAnnotationMappingInfo(Object bean, Method beanMethod);

    @Override
    public Object getHandler(Event event) {

        // 如果不是BotEvent，默认返回null，除非子类重写getHandlerMethodNotBotEvent方法
        if (!(event instanceof BotEvent)) {
            return getHandlerMethodNotBotEvent(event);
        }

        long id = ((BotEvent) event).getBot().getId();
        EventId eventId = new EventId(id, event.getClass());
        List<AnnotationMappingInfo> mappingInfos = eventIdMappingInfoMap.get(eventId);

        AnnotationMappingInfo mappingInfo = getMappingInfo(mappingInfos, event);

        return handlerMethodMap.get(mappingInfo);
    }

    /**
     * 在注解映射信息列表中寻找匹配的AnnotationMappingInfo
     * 此列表应该是已经排序的
     * @param mappingInfos 注解映射信息列表
     * @param event 当前事件
     * @return 注解映射信息
     */
    protected abstract AnnotationMappingInfo getMappingInfo(List<AnnotationMappingInfo> mappingInfos, Event event);

    /**
     * 当此次事件不是BotEvent的时候调用
     * 如果事件不是BotEvent，那么本类是无法处理的
     * 如果要返回处理器，需要重写此方法
     * @param event 事件
     * @return 处理器
     */
    protected Object getHandlerMethodNotBotEvent(Event event) {
        return null;
    }

}
