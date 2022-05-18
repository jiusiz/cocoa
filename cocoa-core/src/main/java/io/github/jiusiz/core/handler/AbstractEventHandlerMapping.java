package io.github.jiusiz.core.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.github.jiusiz.core.EventMappingAnnotationInfo;
import io.github.jiusiz.core.HandlerMapping;
import io.github.jiusiz.core.method.HandlerMethod;
import io.github.jiusiz.core.support.ApplicationContextSupport;
import net.mamoe.mirai.event.Event;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-10 下午 3:20
 */
public abstract class AbstractEventHandlerMapping extends ApplicationContextSupport implements InitializingBean, HandlerMapping {

    protected final Map<EventMappingAnnotationInfo, HandlerMethod> handlerMethodCenter = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        initHandlerMethods();
        System.out.println("InitializingBean的方法调用完成。。。");
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
     * @param beanType bean的class
     */
    private void processBean(Class<?> beanType) {
        Assert.notNull(beanType, "beanType require not null");

        Method[] methods = beanType.getDeclaredMethods();
        for (Method method : methods) {
            if (isHandlerMethod(method)) {
                EventMappingAnnotationInfo eventMappingAnnotationInfo = createMessageEventInfo(method, beanType);
                registerHandlerMethod(eventMappingAnnotationInfo, method, beanType);
            }
        }

    }

    /**
     * 将bean封装为HandlerMethod
     * @param eventMappingAnnotationInfo 封装的注解映射
     * @param method 要探测方法
     * @param beanType bean的class
     */
    private void registerHandlerMethod(EventMappingAnnotationInfo eventMappingAnnotationInfo, Method method, Class<?> beanType) {
        HandlerMethod handlerMethod = new HandlerMethod(beanType, method);
        handlerMethodCenter.put(eventMappingAnnotationInfo, handlerMethod);
    }

    /**
     * 创建映射信息
     * @param method 方法
     * @param beanType bean类型
     * @return 封装好的注解信息
     */
    protected abstract EventMappingAnnotationInfo createMessageEventInfo(Method method, Class<?> beanType);

    /**
     * 判断是否为需要的处理器
     * @param type 处理器类型
     * @return 是否为需要的
     */
    protected abstract boolean isHandler(Class<?> type);

    /**
     * 是否为需要的处理方法
     * @param method 方法
     * @return 是否为需要的方法
     */
    protected abstract boolean isHandlerMethod(Method method);

    @Override
    public HandlerMethod getHandler(Event event) {
        return getHandlerInternal(event);
    }

    /**
     * 获得处理器的子类模板方法
     * @param event 事件
     * @return 处理器方法
     */
    protected abstract HandlerMethod getHandlerInternal(Event event);

    /**
     * 获取HandlerMethod
     * @param eventMappingAnnotationInfo 事件的注解封装信息
     * @return 处理器方法
     */
    @Nullable
    protected HandlerMethod getHandlerMethod(EventMappingAnnotationInfo eventMappingAnnotationInfo) {
        return this.handlerMethodCenter.get(eventMappingAnnotationInfo);
    }
}
