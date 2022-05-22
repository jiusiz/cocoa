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

import io.github.jiusiz.core.adapter.ArgumentResolver;
import io.github.jiusiz.core.method.MethodParameter;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 消息事件参数解析器
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-17 下午 6:57
 */
public class MessageEventArgumentResolver implements ArgumentResolver {

    @Override
    public boolean supportsArgument(MethodParameter parameter) {
        return MessageEvent.class.isAssignableFrom(parameter.getEventClass());
    }

    @Override
    public Object resolveArgument(Event event, MethodParameter parameter) {
        MessageEvent ms = (MessageEvent) event;
        if (MessageEvent.class.isAssignableFrom(parameter.getParameterType())){
            return ms;
        }
        if (User.class.isAssignableFrom(parameter.getParameterType())){
            return ms.getSender();
        }
        if (Contact.class.isAssignableFrom(parameter.getParameterType())){
            return ms.getSubject();
        }
        return null;
    }
}
