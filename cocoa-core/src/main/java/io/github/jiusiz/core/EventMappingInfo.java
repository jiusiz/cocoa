package io.github.jiusiz.core;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * 事件映射信息
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-12 下午 9:16
 */
public class EventMappingInfo {
    private Class<?> type;
    private MessageEventInfo messageEventInfo;

    public EventMappingInfo(MessageEvent event) {
        this.type = event.getClass();
        this.messageEventInfo = new MessageEventInfo(event);
    }

    public MessageEventInfo getMessageEventInfo(){
        return this.messageEventInfo;
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
         */
        public String getMiraiCode() {
            return this.message.serializeToMiraiCode();
        }

        /**
         * 获取发送人id
         */
        public Long getSenderId() {
            return this.sender.getId();
        }

        /**
         * 获取群id，如果不是群，则返回null
         */
        public Long getGroupId() {
            if (this.subject instanceof Group) {
                return subject.getId();
            }
            return null;
        }

        /**
         * 获取发送人昵称
         */
        public String getName() {
            return this.senderName;
        }

        /**
         * 获取机器人的id
         */
        public Long getBotId(){
            return this.bot.getId();
        }
    }
}
