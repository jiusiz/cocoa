package io.github.jiusiz.core;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-13 下午 3:50
 */
public class EventMappingAnnotationInfo {
    private String content;
    private Long sender;
    private String senderName;
    private Class<?> eventClass;

    public EventMappingAnnotationInfo(String content, Long sender, String senderName, Class<?> eventClass) {
        this.content = content;
        this.sender = sender;
        this.senderName = senderName;
        this.eventClass = eventClass;
    }

    public Class<?> getEventClass(){
        return this.eventClass;
    }

}
