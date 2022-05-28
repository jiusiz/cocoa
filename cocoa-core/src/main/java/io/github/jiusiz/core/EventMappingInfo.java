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

package io.github.jiusiz.core;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.lang.Nullable;

/**
 * 事件映射信息
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-12 下午 9:16
 */
@Deprecated
public class EventMappingInfo {

    private final Event event;

    public EventMappingInfo(MessageEvent event) {
        this.event = event;
    }

    @Nullable
    public MessageEventInfo getMessageEventInfo() {
        if (MessageEvent.class.isAssignableFrom(this.event.getClass())) {
            return new MessageEventInfo((MessageEvent) event);
        }
        return null;
    }

    public class MessageEventInfo {
        public Bot bot;
        public Contact subject;
        public User sender;
        public String senderName;
        public MessageChain message;
        public Integer time;

        public MessageEventInfo(MessageEvent event) {
            this.bot = event.getBot();
            this.subject = event.getSubject();
            this.sender = event.getSender();
            this.senderName = event.getSenderName();
            this.message = event.getMessage();
            this.time = event.getTime();
        }

        /**
         * 获取消息转义后的mirai码
         * @return 转义后的mirai码
         */
        public String getMiraiCode() {
            return this.message.serializeToMiraiCode();
        }

        /**
         * 获取发送人id
         * @return 发送人id
         */
        public Long getSenderId() {
            return this.sender.getId();
        }

        /**
         * 获取群id，如果不是群，则返回null
         * @return 群id
         */
        public Long getGroupId() {
            if (this.subject instanceof Group) {
                return subject.getId();
            }
            return null;
        }

        /**
         * 获取发送人昵称
         * @return 发送人昵称
         */
        public String getName() {
            return this.senderName;
        }

        /**
         * 获取机器人的id
         * @return 机器人的id
         */
        public Long getBotId() {
            return this.bot.getId();
        }
    }
}
