package io.github.jiusiz.core;

import io.github.jiusiz.core.method.HandlerMethod;
import net.mamoe.mirai.event.Event;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-07 下午 8:24
 */
public class EventDispatcher extends AbstractEventDispatcher {

    private List<HandlerMapping> handlerMappings;


    @Override
    protected void onRefresh(ApplicationContext context) {
        initHandlerMappings(context);
    }

    private void initHandlerMappings(ApplicationContext context) {
        this.handlerMappings = null;

        Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);

        if (!matchingBeans.isEmpty()) {
            this.handlerMappings = new ArrayList<>(matchingBeans.values());
        }

        // TODO 增加：无handlerMapping的策略
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
    public void dispatch(Event event) {
        HandlerMethod handler = getHandler(event);

        // 如果没有处理器则会直接返回，无法处理本次事件
        if (handler == null) {
            return;
        }
        // TODO: 2022-5-12 增加适配器
    }

    /**
     * 根据事件获取处理器
     */
    private HandlerMethod getHandler(Event event) {
        if (handlerMappings != null) {
            for (HandlerMapping handlerMapping : handlerMappings) {
                HandlerMethod handler = handlerMapping.getHandler(event);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }


}
