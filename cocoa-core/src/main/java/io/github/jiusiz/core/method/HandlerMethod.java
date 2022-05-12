package io.github.jiusiz.core.method;

import java.lang.reflect.Method;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 9:33
 */
public class HandlerMethod {

    private Object bean;
    private Method method;

    public HandlerMethod(Object bean, Method method){
        this.bean = bean;
        this.method = method;
    }

}
