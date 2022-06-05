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
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * 返回类型
 * @author jiusiz
 * @since 0.1
 */
public class ReturnType {

    private final Class<?> returnType;

    private final Method method;

    public ReturnType(Method method, Class<?> returnType) {
        this.method = method;
        this.returnType = returnType;
    }

    public Class<?> getReturnType() {
        return this.returnType;
    }

    public boolean hasMethodAnnotation(Class<? extends Annotation> annotationType) {
        return AnnotatedElementUtils.hasAnnotation(method, annotationType);
    }

}
