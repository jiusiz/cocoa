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

package io.github.jiusiz;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import io.github.jiusiz.core.BotContainer;
import io.github.jiusiz.core.EventDispatcher;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 监听spring boot启动，启动完成后进行自动登录所有机器人
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-18 下午 2:27
 */
public class CocoaApplicationRunListener implements SpringApplicationRunListener {

    public CocoaApplicationRunListener() {
    }

    public CocoaApplicationRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        ThreadPoolExecutor executor = context.getBean(ThreadPoolExecutor.class);
        BotContainer container = context.getBean(BotContainer.class);
        EventDispatcher dispatcher = context.getBean(EventDispatcher.class);

        List<Bot> bots = container.getBots();

        // 使用线程池启动每一个机器人
        for (Bot bot : bots) {
            bot.getEventChannel().subscribeAlways(Event.class, dispatcher::doService);
            executor.execute(bot::login);
        }

    }
}
