package io.github.jiusiz.core.support;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * 提供ApplicationContext支持
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 9:41
 */
public abstract class ApplicationContextSupport implements ApplicationContextAware {

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    protected ApplicationContext getApplicationContext(){
        Assert.state(context != null, "No ApplicationContext");
        return context;
    }
}
