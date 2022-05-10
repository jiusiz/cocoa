package io.github.jiusiz.core.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 9:41
 */
public class ApplicationContextSupport implements ApplicationContextAware {

    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
