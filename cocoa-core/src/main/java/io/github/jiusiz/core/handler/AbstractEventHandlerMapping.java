package io.github.jiusiz.core.handler;

import io.github.jiusiz.core.HandlerMapping;
import io.github.jiusiz.core.MessageEventMappingInfo;
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

    protected final Map<MessageEventMappingInfo, HandlerMethod> handlerMethodCenter = new HashMap<>();

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

    /**
     * 处理bean，将带有注解的方法封装为映射信息
     */
    private void processBean(Class<?> beanType) {
        Assert.notNull(beanType, "beanType require not null");

        Method[] methods = beanType.getDeclaredMethods();
        for (Method method : methods) {
            if (isHandlerMethod(method)) {
                MessageEventMappingInfo messageEventMappingInfo = createMessageEventInfo(method, beanType);
                registerHandlerMethod(messageEventMappingInfo, method, beanType);
            }
        }

    }

    /**
     * 将bean封装为HandlerMethod
     */
    private void registerHandlerMethod(MessageEventMappingInfo messageEventMappingInfo, Method method, Class<?> beanType) {
        HandlerMethod handlerMethod = new HandlerMethod(beanType, method);
        handlerMethodCenter.put(messageEventMappingInfo, handlerMethod);
    }

    /**
     * 创建映射信息
     */
    protected abstract MessageEventMappingInfo createMessageEventInfo(Method method, Class<?> beanType);

    /**
     * 判断是否为需要的处理器
     */
    protected abstract boolean isHandler(Class<?> type);

    /**
     * 是否为需要的处理方法
     */
    protected abstract boolean isHandlerMethod(Method method);
}
