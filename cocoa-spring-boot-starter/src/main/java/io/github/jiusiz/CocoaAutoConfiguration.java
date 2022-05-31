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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.github.jiusiz.core.BotContainer;
import io.github.jiusiz.core.ContactAssembler;
import io.github.jiusiz.core.EventDispatcher;
import io.github.jiusiz.core.context.SingleBotContainer;
import io.github.jiusiz.factory.SimpleBotFactory;
import io.github.jiusiz.properties.BotProperties;
import io.github.jiusiz.properties.QQAccount;
import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-02-27 上午 1:04
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({BotProperties.class})
public class CocoaAutoConfiguration {

    public static final String QQ_LOGIN_THREAD_POOL_EXECUTOR = "io.github.jiusiz.loginThreadPoolExecutor";

    private final BotProperties botProperties;

    @Autowired
    public CocoaAutoConfiguration(BotProperties botProperties) {
        this.botProperties = botProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public BotContainer botContainer() {
        SingleBotContainer singleBotContainer = new SingleBotContainer();

        for (QQAccount qq : botProperties.getQq()) {
            Bot bot = SimpleBotFactory.createBot(qq.getAccount(), qq.getPassword(), botProperties);
            SingleBotContainer.addBot(bot);
        }
        return singleBotContainer;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(BotContainer.class)
    public EventDispatcher eventDispatcher() {
        return new EventDispatcher();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(BotContainer.class)
    public ContactAssembler contactAssembler() {
        return new ContactAssembler();
    }

    @Bean(QQ_LOGIN_THREAD_POOL_EXECUTOR)
    @ConditionalOnBean(BotContainer.class)
    public ThreadPoolExecutor threadPoolExecutor() {
        int size = botProperties.getQq().size();
        BlockingQueue<Runnable> bq = new LinkedBlockingDeque<>();
        return new ThreadPoolExecutor(size, size * 2, 10, TimeUnit.MINUTES, bq);
    }

}
