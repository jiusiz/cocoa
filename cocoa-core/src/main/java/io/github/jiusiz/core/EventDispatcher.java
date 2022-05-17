package io.github.jiusiz.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.mamoe.mirai.event.Event;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-07 下午 8:24
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
            this.handlerMappings = new ArrayList<>(matchingBeans.values());
        }

        // TODO 增加：添加默认handlerMapping
    }

    private void initHandlerAdapters(ApplicationContext context) {
        this.handlerAdapters = null;

        Map<String, HandlerAdapter> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerAdapter.class, true, false);

        if (!matchingBeans.isEmpty()) {
            this.handlerAdapters = new ArrayList<>(matchingBeans.values());
        }

        // TODO: 2022-5-17 添加默认HandlerAdapter
    }

    /**
     * 暴露接受事件
     */
    public void doService(Event event) {
        dispatch(event);
    }

    /**
     * 实际的调度方法
     */
    private void dispatch(Event event) {
        Object handler = getHandler(event);

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

        HandlerAdapter ha = getAdapter(handler);

        if (ha == null) {
            logger.warn("未找到适配器处理此方法" + handler.getClass());
            return;
        }

        try {
            ha.handle(event, handler);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据事件获取处理器
     */
    private Object getHandler(Event event) {
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

    private HandlerAdapter getAdapter(Object handler) {
        if (handlerAdapters != null) {
            for (HandlerAdapter handlerAdapter : handlerAdapters) {
                if (handlerAdapter.supports(handler)) {
                    return handlerAdapter;
                }
            }
        }
        return null;
    }

}
