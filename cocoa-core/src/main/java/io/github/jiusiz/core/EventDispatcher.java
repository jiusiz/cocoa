package io.github.jiusiz.core;

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

    /**
     * 实际的调度方法
     *
     * @param event 事件
     */
    void dispatch(Event event) {
        // TODO 调度
    }

    @Override
    protected void onRefresh(ApplicationContext context) {
        initHandlerMapping(context);
    }

    private void initHandlerMapping(ApplicationContext context) {
        this.handlerMappings = null;

        Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);

        if (!matchingBeans.isEmpty()) {
            this.handlerMappings = new ArrayList<>(matchingBeans.values());
        }

        // TODO 增加：无handlerMapping的策略
    }
}
