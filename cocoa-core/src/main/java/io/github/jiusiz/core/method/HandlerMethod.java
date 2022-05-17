package io.github.jiusiz.core.method;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 9:33
 */
public class HandlerMethod {

    private final Object bean;

    private final Method method;

    private final Class<?> beanType;

    private final MethodParameter[] parameters;

    public HandlerMethod(Object bean, Method method) {
        Assert.notNull(bean, "Bean is required");
        Assert.notNull(method, "Method is required");

        this.bean = bean;
        this.method = method;
        this.beanType = ClassUtils.getUserClass(bean);
        this.parameters = initParameters();
    }

    private MethodParameter[] initParameters() {
        MethodParameter[] jmp = new MethodParameter[method.getParameterCount()];
        Parameter[] ps = method.getParameters();

        for (int i = 0; i < ps.length; i++) {
            // 构造参数
            jmp[i] = new MethodParameter(method, i, ps[i]);
        }
        return jmp;
    }

    public Method getMethod() {
        return method;
    }

    public Object getBean() {
        return bean;
    }

    public MethodParameter[] getParameters() {
        return parameters;
    }

    public MethodParameter getParameter(int index) {
        if (index >= parameters.length || index < 0) {
            throw new RuntimeException("获取参数时超过索引上界");
        }
        return parameters[index];
    }

    public int getParameterCount() {
        return method.getParameterCount();
    }

}
