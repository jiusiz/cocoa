package io.github.jiusiz;

import io.github.jiusiz.core.BotContainer;
import io.github.jiusiz.core.supports.SingleBotContainer;
import io.github.jiusiz.properties.BotProperties;
import net.mamoe.mirai.BotFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

        botProperties.getQq().forEach(qq ->
                SingleBotContainer.addBot(BotFactory.INSTANCE.newBot(qq.getAccount(), qq.getPassword())));
        return singleBotContainer;
    }

}
