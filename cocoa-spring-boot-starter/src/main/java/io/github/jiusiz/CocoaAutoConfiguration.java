package io.github.jiusiz;

import io.github.jiusiz.core.BotContainer;
import io.github.jiusiz.core.EventDispatcher;
import io.github.jiusiz.core.context.SingleBotContainer;
import io.github.jiusiz.factory.SimpleBotFactory;
import io.github.jiusiz.properties.BotProperties;
import io.github.jiusiz.properties.QQAccount;
import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-02-27 上午 1:04
 */
@Configuration
@EnableConfigurationProperties({BotProperties.class})
public class CocoaAutoConfiguration {

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
    public EventDispatcher eventDispatcher() {
        return new EventDispatcher();
    }

}
