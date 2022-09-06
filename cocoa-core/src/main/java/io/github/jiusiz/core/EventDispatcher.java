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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.jiusiz.core.adapter.BotOnlineHandlerAdapter;
import io.github.jiusiz.core.adapter.HandlerMethodAdapter;
import io.github.jiusiz.core.handler.BasicHandlerMapping;
import io.github.jiusiz.core.handler.BotOnlineHandlerMapping;
import io.github.jiusiz.core.handler.MessageHandlerMapping;
import io.github.jiusiz.core.model.EventModel;
import net.mamoe.mirai.event.Event;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

/**
 * @author jiusiz
 * @version 0.2.0
 * @since 0.1.0 2022-05-07 下午 8:24
 */
public class EventDispatcher extends AbstractEventDispatcher {

    private List<HandlerMapping> handlerMappings;

    private List<HandlerAdapter> handlerAdapters;

    @Override
    protected void onRefresh(ApplicationContext context) {
        initHandlerMappings(context);
        initHandlerAdapters(context);
    }

    private void initHandlerMappings(ApplicationContext context) {
        this.handlerMappings = null;

        Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);

        if (!matchingBeans.isEmpty()) {
            logger.info("已经在容器中找到HandlerMapping，默认将不再生效");
            this.handlerMappings = new ArrayList<>(matchingBeans.values());
        }
        if (handlerMappings == null) {
            this.handlerMappings = getDefaultHandlerMappings();
        }
    }

    private void initHandlerAdapters(ApplicationContext context) {
        this.handlerAdapters = null;

        Map<String, HandlerAdapter> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerAdapter.class, true, false);

        if (!matchingBeans.isEmpty()) {
            logger.debug("已经在容器中找到HandlerMapping，默认将不再生效");
            this.handlerAdapters = new ArrayList<>(matchingBeans.values());
        }

        if (handlerAdapters == null) {
            this.handlerAdapters = getDefaultHandlerAdapters();
        }
    }

    /**
     * 暴露接受事件
     * @param event 事件
     */
    public void doService(Event event) {

        if (passInternalEvent(event.getClass().getName())) {
            return;
        }

        if (logger.isTraceEnabled()) {
            logger.trace("接受到事件:" + event.getClass());
        }

        dispatch(event);
    }

    private boolean passInternalEvent(String className) {
        // 部分需要放行的事件
        return className.startsWith("net.mamoe.mirai.internal.network.protocol");
    }

    /**
     * 实际的调度方法
     * @param event 事件
     */
    private void dispatch(Event event) {
        Object handler = getHandlerMapping(event);
        EventModel ev = null;

        // 如果没有处理器则会直接返回，无法处理本次事件
        if (handler == null) {
            if (logger.isTraceEnabled()) {
                logger.trace("无法处理事件：" + event.getClass().getName());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("无法处理事件：" + event.getClass().getName());
            }
            return;
        }

        HandlerAdapter ha = getHandlerAdapter(handler);

        if (ha == null) {
            logger.warn("未找到适配器处理此方法:" + handler.getClass());
            return;
        }

        try {
            ev = ha.handle(event, handler);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: 2022-5-25 异常处理器
        }
    }

    /**
     * 根据事件获取处理器
     * @param event 事件
     * @return 获得到的处理器
     */
    private Object getHandlerMapping(Event event) {
        if (handlerMappings != null) {
            for (HandlerMapping handlerMapping : handlerMappings) {
                Object handler = handlerMapping.getHandler(event);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        if (handlerAdapters != null) {
            for (HandlerAdapter handlerAdapter : handlerAdapters) {
                if (handlerAdapter.supports(handler)) {
                    return handlerAdapter;
                }
            }
        }
        return null;
    }

    /**
     * 在容器中注册bean
     * @param clazz bean的类型
     * @param <T> bean类型
     * @return bean
     */
    protected <T> T registerBean(Class<T> clazz) {
        return context.getAutowireCapableBeanFactory().createBean(clazz);
    }

    private List<HandlerMapping> getDefaultHandlerMappings() {
        List<HandlerMapping> defaultHandlerMappings = new ArrayList<>();
        defaultHandlerMappings.add(registerBean(BotOnlineHandlerMapping.class));
        defaultHandlerMappings.add(registerBean(MessageHandlerMapping.class));
        defaultHandlerMappings.add(registerBean(BasicHandlerMapping.class));
        return defaultHandlerMappings;
    }

    private List<HandlerAdapter> getDefaultHandlerAdapters() {
        List<HandlerAdapter> defaultHandlerAdapters = new ArrayList<>();
        defaultHandlerAdapters.add(registerBean(BotOnlineHandlerAdapter.class));
        defaultHandlerAdapters.add(registerBean(HandlerMethodAdapter.class));
        return defaultHandlerAdapters;
    }

}
