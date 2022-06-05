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

package io.github.jiusiz.core.adapter.handler;

import io.github.jiusiz.core.adapter.HandlerMethodReturnValueHandler;
import io.github.jiusiz.core.annotation.mapping.Reply;
import io.github.jiusiz.core.method.ReturnType;
import io.github.jiusiz.core.model.EventModel;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 回复消息的返回值处理器
 * @author jiusiz
 * @since 0.1
 */
public class ReplyMessageReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(ReturnType returnType, Event event) {
        return (event instanceof MessageEvent) && returnType.hasMethodAnnotation(Reply.class);
    }

    @Override
    public EventModel handleReturnValue(EventModel model, Object returnValue, ReturnType returnType, Event event) {
        MessageEvent me = (MessageEvent) event;
        me.getSubject().sendMessage(returnValue.toString());
        return model;
    }

}
