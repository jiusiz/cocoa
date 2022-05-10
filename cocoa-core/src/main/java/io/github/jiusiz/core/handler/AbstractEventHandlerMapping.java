package io.github.jiusiz.core.handler;

import io.github.jiusiz.core.HandlerMapping;
import io.github.jiusiz.core.MessageEventInfo;
import io.github.jiusiz.core.method.HandlerMethod;
import io.github.jiusiz.core.support.ApplicationContextSupport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-10 下午 3:20
 */
public abstract class AbstractEventHandlerMapping extends ApplicationContextSupport implements InitializingBean, HandlerMapping {

    protected final Map<String, HandlerMethod> handlerMethods = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean的方法调用。。。");
        initHandlerMethods();
    }

    /**
     * 初始化处理器的映射方法
     */
    protected void initHandlerMethods() {
        ApplicationContext context = getApplicationContext();
        String[] beanNames = context.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            Class<?> beanType = context.getType(beanName);
            if (beanType != null && isHandler(beanType)) {
                processBean(beanType);
            }
        }
    }

    private void processBean(Class<?> beanType) {
        Assert.notNull(beanType, "beanType require not null");

        Method[] methods = beanType.getDeclaredMethods();
        for (Method method : methods) {
            if (isHandlerMethod(method)) {
                MessageEventInfo messageEventInfo = createMessageEventInfo(method, beanType);
            }
        }

    }

    protected abstract MessageEventInfo createMessageEventInfo(Method method, Class<?> beanType);

    /**
     * 判断是否为需要的处理器
     *
     * @return 是否为本类需要的处理器
     */
    protected abstract boolean isHandler(Class<?> type);

    protected abstract boolean isHandlerMethod(Method method);
}
