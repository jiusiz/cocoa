/*
 * Copyright (C) 2022-2022 jiusiz.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.jiusiz.core.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 下午 2:48
 */
public class MethodParameter {

    private final Executable method;

    private final Parameter parameter;

    private final int parameterIndex;

    @Nullable
    private Class<?> eventClass;

    public MethodParameter(Method method, int parameterIndex, Parameter parameter) {
        this.method = method;
        this.parameterIndex = parameterIndex;
        this.parameter = parameter;
    }

    @Deprecated
    public void setEventClass(@Nullable Class<?> eventClass) {
        this.eventClass = eventClass;
    }

    @Nullable
    @Deprecated
    public Class<?> getEventClass() {
        return eventClass;
    }

    public Class<?> getParameterType() {
        return parameter.getType();
    }

    public boolean hasMethodAnnotation(Class<? extends Annotation> annotationType) {
        return AnnotatedElementUtils.hasAnnotation(method, annotationType);
    }

    public boolean hasParameterAnnotation(Class<? extends Annotation> annotationType) {
        return AnnotatedElementUtils.hasAnnotation(parameter, annotationType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodParameter that = (MethodParameter) o;

        if (parameterIndex != that.parameterIndex) return false;
        if (!Objects.equals(method, that.method)) return false;
        if (!Objects.equals(parameter, that.parameter)) return false;
        return Objects.equals(eventClass, that.eventClass);
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        result = 31 * result + parameterIndex;
        result = 31 * result + (eventClass != null ? eventClass.hashCode() : 0);
        return result;
    }
}
