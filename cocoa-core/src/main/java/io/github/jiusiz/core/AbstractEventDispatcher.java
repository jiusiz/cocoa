package io.github.jiusiz.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-08 下午 7:43
 */
public abstract class AbstractEventDispatcher implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    /** 日志 */
    protected final Log logger = LogFactory.getLog(getClass());

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        onRefresh(event.getApplicationContext());
    }

    protected void onRefresh(ApplicationContext context) {
        // 为子类留下模板方法，容器刷新完成后调用
    }

}
