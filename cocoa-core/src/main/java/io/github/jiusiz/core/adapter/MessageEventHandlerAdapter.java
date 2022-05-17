package io.github.jiusiz.core.adapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.jiusiz.core.adapter.resolver.MessageEventArgumentResolver;
import io.github.jiusiz.core.method.HandlerMethod;
import io.github.jiusiz.core.method.MethodParameter;
import io.github.jiusiz.core.model.EventModel;
import net.mamoe.mirai.event.Event;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 上午 10:30
 */
public class MessageEventHandlerAdapter extends AbstractHandlerMethodAdapter {

    private List<ArgumentResolver> argumentResolvers;

    private final Map<MethodParameter, ArgumentResolver> argumentResolverCache = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        initArgumentResolvers();
    }

    /**
     * 初始化参数解析器
     */
    private void initArgumentResolvers() {
        this.argumentResolvers = getDefaultArgumentResolvers();
    }

    /**
     * 获取默认的参数解析器
     */
    private List<ArgumentResolver> getDefaultArgumentResolvers() {
        List<ArgumentResolver> list = new ArrayList<>();
        list.add(new MessageEventArgumentResolver());
        return list;
    }

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return true;
    }

    @Override
    protected EventModel handleInternal(Event event, HandlerMethod handler) {
        // 准备参数
        Object[] args = new Object[handler.getParameterCount()];

        MethodParameter[] parameters = handler.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            MethodParameter parameter = parameters[i];
            // 设置事件的class
            parameter.setEventClass(event.getClass());
            // 先在缓存中查找
            ArgumentResolver resolver = findArgumentResolverFromCache(parameter);

            if (resolver == null) {
                // 若没有找到，寻找全部的解析器
                resolver = findArgumentResolver(parameter);
                // 找到之后加入缓存
                this.argumentResolverCache.put(parameter, resolver);
            }

            args[i] = resolver.resolveArgument(event, parameter);
        }

        EventModel eventModel = invokeMethod(handler, args);
        eventModel.setEventClass(event.getClass());
        // TODO: 2022-5-17 这里是返回值处理器工作的地方

        return eventModel;
    }

    private ArgumentResolver findArgumentResolverFromCache(MethodParameter parameter) {
        return this.argumentResolverCache.get(parameter);
    }

    private ArgumentResolver findArgumentResolver(MethodParameter parameter) {
        if (argumentResolvers != null) {
            for (ArgumentResolver argumentResolver : argumentResolvers) {
                if (argumentResolver.supportsArgument(parameter)) {
                    return argumentResolver;
                }
            }
        }
        throw new RuntimeException("未找到参数解析器");
    }

    private EventModel invokeMethod(HandlerMethod handler, Object[] args) {
        Method method = handler.getMethod();
        Object bean = handler.getBean();
        Object result = null;

        try {
            result = method.invoke(bean, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new EventModel(result);
    }
}
