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

package io.github.jiusiz.core.adapter.resolver;

import io.github.jiusiz.core.adapter.HandlerMethodArgumentResolver;
import io.github.jiusiz.core.method.MethodParameter;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 消息主体参数解析器
 * <p>支持解析联系人-群{@link net.mamoe.mirai.contact.Group}/好友{@link net.mamoe.mirai.contact.Friend}/陌生人{@link net.mamoe.mirai.contact.Stranger}，
 * 群员-发送者{@link net.mamoe.mirai.contact.Member}
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-30 下午 7:26
 */
public class MessageSubjectArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(Event event, MethodParameter parameter) {
        return (event instanceof MessageEvent) &&
                Contact.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Contact resolverArgument(Event event, MethodParameter parameter) {
        MessageEvent me = (MessageEvent) event;

        if (event instanceof GroupMessageEvent && Group.class.isAssignableFrom(parameter.getParameterType())) {
            return ((GroupMessageEvent) event).getGroup();
        }

        if (event instanceof GroupMessageEvent && Member.class.isAssignableFrom(parameter.getParameterType())) {
            return ((GroupMessageEvent) event).getSender();
        }

        return me.getSubject();
    }
}
