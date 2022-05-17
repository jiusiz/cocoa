package io.github.jiusiz.core.method;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 下午 2:48
 */
public class MethodParameter {

    private final Executable executable;

    private final Parameter parameter;

    private final int parameterIndex;

    private Class<?> eventClass;

    public MethodParameter(Method method, int parameterIndex, Parameter parameter) {
        this.executable = method;
        this.parameterIndex = parameterIndex;
        this.parameter = parameter;
    }

    public void setEventClass(Class<?> eventClass) {
        this.eventClass = eventClass;
    }

    public Class<?> getEventClass() {
        return eventClass;
    }

    public Class<?> getParameterType() {
        return parameter.getType();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodParameter that = (MethodParameter) o;

        if (parameterIndex != that.parameterIndex) return false;
        if (!Objects.equals(executable, that.executable)) return false;
        if (!Objects.equals(parameter, that.parameter)) return false;
        return Objects.equals(eventClass, that.eventClass);
    }

    @Override
    public int hashCode() {
        int result = executable != null ? executable.hashCode() : 0;
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        result = 31 * result + parameterIndex;
        result = 31 * result + (eventClass != null ? eventClass.hashCode() : 0);
        return result;
    }
}
