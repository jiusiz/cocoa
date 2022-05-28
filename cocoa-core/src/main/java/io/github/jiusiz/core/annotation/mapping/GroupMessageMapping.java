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

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.core.annotation.AliasFor;

/**
 * @author jiusiz
 * @version 0.2.0
 * @since 0.1.0 2022-05-11 下午 9:48
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MessageMapping(event = GroupMessageEvent.class)
public @interface GroupMessageMapping {

    @AliasFor(annotation = MessageMapping.class)
    long sender() default 0;

    @AliasFor(annotation = MessageMapping.class)
    String senderName() default "";

    @AliasFor(annotation = MessageMapping.class)
    long group() default 0;

    @AliasFor(annotation = MessageMapping.class)
    MemberPermission permission() default MemberPermission.MEMBER;

    @AliasFor(annotation = MessageMapping.class)
    String content() default "";

}
