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

package io.github.jiusiz.core.adapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.jiusiz.core.adapter.handler.ReplyMessageReturnValueHandler;
import io.github.jiusiz.core.adapter.resolver.EventArgumentResolver;
import io.github.jiusiz.core.adapter.resolver.EventMessageArgumentResolver;
import io.github.jiusiz.core.adapter.resolver.MessageSubjectArgumentResolver;
import io.github.jiusiz.core.method.HandlerMethod;
import io.github.jiusiz.core.method.MethodParameter;
import io.github.jiusiz.core.method.ReturnType;
import io.github.jiusiz.core.model.EventModel;
import net.mamoe.mirai.event.Event;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-29 下午 4:37
 */
public class HandlerMethodAdapter extends AbstractHandlerMethodAdapter {

    private List<HandlerMethodArgumentResolver> argumentResolvers;

    private List<HandlerMethodReturnValueHandler> returnValueHandlers;

    private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        initArgumentResolvers();
        initReturnValueHandlers();
    }

    /**
     * 初始化参数解析器
     */
    private void initArgumentResolvers() {
        // TODO: 2022-5-29 自定义参数解析器
        List<HandlerMethodArgumentResolver> resolvers = getDefaultArgumentResolvers();
        if (resolvers.isEmpty()) {
            logger.warn("获取默认参数解析器失败，这可能是一个内部错误。");
        }
        this.argumentResolvers = resolvers;
    }

    /**
     * 初始化返回值处理器
     */
    private void initReturnValueHandlers() {
        // TODO: 2022年6月2日 自定义返回值处理器
        List<HandlerMethodReturnValueHandler> returnValueHandlers = getDefaultHandlerMethodReturnValueHandlers();
        if (returnValueHandlers.isEmpty()) {
            logger.warn("获取默认返回值处理器失败，这可能是一个内部错误。");
        }
        this.returnValueHandlers = returnValueHandlers;
    }

    /**
     * 创建默认的参数解析器
     * @return 参数解析器列表
     */
    private List<HandlerMethodArgumentResolver> getDefaultArgumentResolvers() {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new EventArgumentResolver());
        resolvers.add(new EventMessageArgumentResolver());

        resolvers.add(new MessageSubjectArgumentResolver());
        return resolvers;
    }

    /**
     * 创建默认的返回值处理器
     * @return 返回值处理器列表
     */
    private List<HandlerMethodReturnValueHandler> getDefaultHandlerMethodReturnValueHandlers() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();
        returnValueHandlers.add(new ReplyMessageReturnValueHandler());
        return returnValueHandlers;
    }

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return true;
    }

    @Override
    protected EventModel handleInternal(Event event, HandlerMethod handler) throws Exception {
        Object[] args = resolverArgument(event, handler);
        Object returnValue = invokeMethod(handler, args);
        return handleReturnValue(returnValue, event, handler.getReturnType());
    }

    /**
     * 解析参数
     * @param event 当前事件
     * @param handler 处理器
     * @return 参数
     */
    private Object[] resolverArgument(Event event, HandlerMethod handler) {
        if (handler.getParameterCount() == 0) {
            return null;
        }
        Object[] args = new Object[handler.getParameterCount()];
        MethodParameter[] parameters = handler.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            HandlerMethodArgumentResolver resolver = getArgumentResolverFromCache(parameters[i]);
            if (resolver == null) {
                resolver = getArgumentResolver(event, parameters[i]);
            }
            Object arg = resolver.resolverArgument(event, parameters[i]);
            args[i] = arg;
        }

        return args;
    }

    @Nullable
    private EventModel handleReturnValue(Object returnValue, Event event, ReturnType returnType) {
        HandlerMethodReturnValueHandler returnValueHandler = getReturnValueHandler(returnType, event);
        if (returnValueHandler != null) {
            EventModel eventModel = new EventModel();
            // 设置已处理
            eventModel.setHandled(true);
            return returnValueHandler.handleReturnValue(eventModel, returnValue, returnType, event);
        }
        return new EventModel();
    }

    private HandlerMethodArgumentResolver getArgumentResolverFromCache(MethodParameter parameter) {
        return argumentResolverCache.get(parameter);
    }

    @NonNull
    private HandlerMethodArgumentResolver getArgumentResolver(Event event, MethodParameter parameter) {
        if (argumentResolvers != null) {
            for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
                if (argumentResolver.supports(event, parameter)) {
                    // 添加到缓存中
                    argumentResolverCache.put(parameter, argumentResolver);
                    return argumentResolver;
                }
            }
        }
        // TODO: 2022-5-30 制定异常
        throw new RuntimeException();
    }

    private HandlerMethodReturnValueHandler getReturnValueHandler(ReturnType returnType, Event event) {
        if (returnValueHandlers != null) {
            for (HandlerMethodReturnValueHandler returnValueHandler : returnValueHandlers) {
                if (returnValueHandler.supportsReturnType(returnType, event)) {
                    return returnValueHandler;
                }
            }
        }
        logger.warn("未找到返回值处理器，将忽略本次方法的返回值。");
        return null;
    }

    private Object invokeMethod(HandlerMethod handler, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (handler.getParameterCount() == 0) {
            return handler.getMethod().invoke(handler.getBean());
        } else {
            return handler.getMethod().invoke(handler.getBean(), args);
        }
    }

}
