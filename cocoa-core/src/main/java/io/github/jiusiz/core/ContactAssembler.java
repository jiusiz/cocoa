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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.github.jiusiz.core.annotation.EventController;
import io.github.jiusiz.core.annotation.wired.ContactWired;
import io.github.jiusiz.core.context.event.BotLoginSuccessEvent;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.AudioSupported;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.FileSupported;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.contact.roaming.RoamingSupported;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * 联系人装配
 * @author jiusiz
 * @version 0.2.0
 * @since 0.2.0 2022-05-30 下午 8:45
 */
public class ContactAssembler implements InitializingBean, ApplicationContextAware, ApplicationListener<BotLoginSuccessEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    private final List<WaitContact> waitContacts = new ArrayList<>();

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        initContactWired(applicationContext);
    }

    private void initContactWired(ApplicationContext context) {
        String[] beanNames = BeanFactoryUtils
                .beanNamesForAnnotationIncludingAncestors(context, EventController.class);

        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            processBean(bean);
        }
    }

    private void processBean(Object bean) {
        EventController eventController = AnnotatedElementUtils
                .getMergedAnnotation(bean.getClass(), EventController.class);
        if (eventController == null) {
            return;
        }
        long botId = eventController.botId();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (AnnotatedElementUtils.hasAnnotation(field, ContactWired.class) &&
                    Contact.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);

                ContactWired contactWired = AnnotatedElementUtils.getMergedAnnotation(field, ContactWired.class);
                if (contactWired == null) {
                    continue;
                }
                long group = contactWired.group();
                long friend = contactWired.friend();
                if (group > 0 && isGroupContact(field.getType())) {
                    joinGroupWaitContact(botId, group, bean, field);
                } else if (friend > 0 && isFriendContact(field.getType())) {
                    joinFriendWaitContact(botId, friend, bean, field);
                }

            }
        }
    }

    private boolean isGroupContact(Class<?> type) {
        return (type == Contact.class || type == Group.class ||
                type == FileSupported.class || type == AudioSupported.class);
    }

    private boolean isFriendContact(Class<?> type) {
        return (type == Contact.class || type == Friend.class || type == User.class ||
                type == AudioSupported.class || type == RoamingSupported.class);
    }

    private void joinGroupWaitContact(long botId, long group, Object bean, Field field) {
        String description = bean.getClass().getName() + "." + field.getName();
        waitContacts.add(new WaitContact(botId, group, bean, field, description, WaitContact.ContactType.GROUP));
    }

    private void joinFriendWaitContact(long botId, long friend, Object bean, Field field) {
        String description = bean.getClass().getName() + "." + field.getName();
        waitContacts.add(new WaitContact(botId, friend, bean, field, description, WaitContact.ContactType.FRIEND));
    }


    @Override
    public void onApplicationEvent(@NotNull BotLoginSuccessEvent event) {
        ApplicationContext context = event.getApplicationContext();
        BotContainer container = context.getBean(BotContainer.class);
        for (WaitContact waitContact : waitContacts) {

            Bot bot = container.getBot(waitContact.botId);
            if (bot == null) {
                logger.warn("Bot(" + waitContact.botId + ") 未找到，请检查 "
                        + waitContact.description + "上的注解信息");
                break;
            }
            Contact contact = null;
            switch (waitContact.contactType) {
                case GROUP: {
                    contact = bot.getGroup(waitContact.contact);
                    break;
                }
                case FRIEND: {
                    contact = bot.getFriend(waitContact.contact);
                    break;
                }
            }
            if (contact == null) {
                logger.warn("从 " + waitContact.description +
                        " 上的注解信息中未找到联系人(" + waitContact.contact + ")");
            }
            try {
                waitContact.field.set(waitContact.bean, contact);
            } catch (IllegalAccessException e) {
                logger.warn("设置属性 " + waitContact.description + " 出现错误，错误信息为");
                e.printStackTrace();
            }
        }
    }

    static class WaitContact {
        private final Long botId;
        private final Long contact;
        private final Object bean;
        private final Field field;
        private final String description;
        private final ContactType contactType;

        public WaitContact(Long botId, Long contact, Object bean, Field field, String description, ContactType contactType) {
            this.botId = botId;
            this.contact = contact;
            this.bean = bean;
            this.field = field;
            this.description = description;
            this.contactType = contactType;
        }

        enum ContactType {
            FRIEND, GROUP
        }
    }
}
