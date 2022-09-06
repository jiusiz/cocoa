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

package io.github.jiusiz.core.handler;

import java.lang.reflect.Method;
import java.util.List;

import io.github.jiusiz.core.AnnotationMappingInfo;
import io.github.jiusiz.core.annotation.mapping.EventMapping;
import io.github.jiusiz.core.annotation.mapping.MessageMapping;
import net.mamoe.mirai.event.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * 基础的HandlerMapping，处理没有被其他处理的事件
 * 不会处理消息事件
 * @author jiusiz
 * @since 0.2 2022-7-7
 */
public class BasicHandlerMapping extends AbstractHandlerMapping {

    @Override
    protected boolean isHandlerMethod(Method beanMethod) {
        if (!AnnotatedElementUtils.hasAnnotation(beanMethod, MessageMapping.class) &&
                AnnotatedElementUtils.hasAnnotation(beanMethod, EventMapping.class)) {
            System.out.println("-----------");
            System.out.println(beanMethod.getName());
        }
        // 非消息注解并且是事件注解
        return !AnnotatedElementUtils.hasAnnotation(beanMethod, MessageMapping.class) &&
                AnnotatedElementUtils.hasAnnotation(beanMethod, EventMapping.class);
    }

    @Override
    protected AnnotationMappingInfo createAnnotationMappingInfo(Object bean, Method beanMethod) {
        return new BasicAnnotationMappingInfo();
    }

    @Override
    protected AnnotationMappingInfo getMappingInfo(List<AnnotationMappingInfo> mappingInfos, Event event) {
        if (mappingInfos == null || mappingInfos.isEmpty()) {
            return null;
        }
        return mappingInfos.get(0);
    }

    static class BasicAnnotationMappingInfo implements AnnotationMappingInfo {
        @Override
        public int compareFrom(@NotNull AnnotationMappingInfo o) {
            return 0;
        }

        @Override
        public boolean match(Event event) {
            return false;
        }
    }

}
