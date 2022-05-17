package io.github.jiusiz.core.adapter.resolver;

import io.github.jiusiz.core.adapter.ArgumentResolver;
import io.github.jiusiz.core.method.MethodParameter;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;

/**
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
