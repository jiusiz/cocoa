package io.github.jiusiz.core.method;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 下午 2:48
 */
public class MethodParameter {

    private Executable executable;

    private Parameter parameter;

    private int parameterIndex;

    public MethodParameter(Method method, int parameterIndex, Parameter parameter) {
        this.executable = method;
        this.parameterIndex = parameterIndex;
        this.parameter = parameter;
    }
}
