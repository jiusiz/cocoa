package io.github.jiusiz.core;

import net.mamoe.mirai.contact.Contact;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-09 下午 8:08
 */
public final class MessageEventInfo implements EventMatchable {

    private MatchPattern pattern;
    private String content;
    private Long sender;
    private Contact subject;
    private SubjectType subjectType;
    private String remark;
    private Integer time;

    private MessageEventInfo() {
    }

    @Override
    public boolean match(MessageEventInfo matchInfo) {
        // TODO 添加实现
        return false;
    }

    public static class Builder {
        // TODO: 2022-5-10 实现构建器
    }

    public enum MatchPattern {
        ALL,
        RULE,
        MUTE
    }

    public enum SubjectType {
        FRIEND,
        GROUP
    }

}
