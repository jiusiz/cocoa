package io.github.jiusiz.core;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * TODO 更名为注解信息封装
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 8:08
 */
public final class MessageEventMappingInfo {

    private MatchPattern pattern;
    private String content;
    private Long sender;
    private Contact subject;
    private SubjectType subjectType;
    private String senderName;
    private Integer time;
    private Class<?> event;
    private Long botId;

    private MessageEventMappingInfo() {
    }

    public static class Builder {

        private MessageEventMappingInfo messageEventMappingInfo;

        /**
         * 空构造
         */
        public Builder() {
            this.messageEventMappingInfo = new MessageEventMappingInfo();
        }

        /**
         * 根据MessageEvent构建MessageMappingInfo
         */
        public Builder(MessageEvent messageEvent) {
            this.messageEventMappingInfo = new MessageEventMappingInfo();
            this.messageEventMappingInfo.content = messageEvent.getMessage().serializeToMiraiCode();
            this.messageEventMappingInfo.sender = messageEvent.getSender().getId();
            this.messageEventMappingInfo.subject = messageEvent.getSubject();

            if (this.messageEventMappingInfo.subject instanceof Group) {
                this.messageEventMappingInfo.subjectType = SubjectType.GROUP;
            } else {
                this.messageEventMappingInfo.subjectType = SubjectType.USER;
            }
            this.messageEventMappingInfo.senderName = messageEvent.getSenderName();
            this.messageEventMappingInfo.time = messageEvent.getTime();
        }

        public Builder content(String content) {
            this.messageEventMappingInfo.content = content;
            return this;
        }

        public Builder sender(Long sender) {
            this.messageEventMappingInfo.sender = sender;
            return this;
        }

        public Builder subject(Contact subject) {
            this.messageEventMappingInfo.subject = subject;
            return this;
        }

        public Builder subjectType(SubjectType subjectType) {
            this.messageEventMappingInfo.subjectType = subjectType;
            return this;
        }

        public Builder senderName(String senderName) {
            this.messageEventMappingInfo.senderName = senderName;
            return this;
        }

        public Builder time(Integer time) {
            this.messageEventMappingInfo.time = time;
            return this;
        }

        public Builder event(Class<?> event) {
            this.messageEventMappingInfo.event = event;
            return this;
        }

        public Builder botId(Long botId) {
            this.messageEventMappingInfo.botId = botId;
            return this;
        }

        public MessageEventMappingInfo build() {
            return this.messageEventMappingInfo;
        }
    }

    public enum MatchPattern {
        ALL,
        RULE,
        MUTE
    }

    public enum SubjectType {
        USER,
        GROUP
    }

}
