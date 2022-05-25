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

package io.github.jiusiz.core.annotation.mapping;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.core.annotation.AliasFor;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-25 上午 10:30
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EventMapping
public @interface MessageMapping {

    String content() default "";

    long sender() default 0;

    String senderName() default "";

    @AliasFor(annotation = EventMapping.class)
    Class<? extends MessageEvent> event() default MessageEvent.class;
}
