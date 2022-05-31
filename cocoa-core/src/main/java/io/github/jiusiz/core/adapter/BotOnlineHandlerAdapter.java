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

package io.github.jiusiz.core.adapter;

import io.github.jiusiz.core.BotContainer;
import io.github.jiusiz.core.HandlerAdapter;
import io.github.jiusiz.core.context.event.BotLoginSuccessEvent;
import io.github.jiusiz.core.handler.BotOnlineHandlerMapping;
import io.github.jiusiz.core.model.EventModel;
import net.mamoe.mirai.event.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1 2022-05-31 下午 3:42
 */
public class BotOnlineHandlerAdapter implements HandlerAdapter, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private BotContainer botContainer;

    private int online = 0;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.botContainer = applicationContext.getBean(BotContainer.class);
    }

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof BotOnlineHandlerMapping.BotOnlineHandler);
    }

    @Override
    public EventModel handle(Event event, Object handler) {
        online++;
        if (online == botContainer.getBots().size()) {
            applicationContext.publishEvent(new BotLoginSuccessEvent(applicationContext));
        }
        return new EventModel();
    }
}
