package io.github.jiusiz.core.supports;

import io.github.jiusiz.core.BotContainer;
import net.mamoe.mirai.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-04-19 下午 10:44
 */
public abstract class AbstractBotContainer implements BotContainer {
    private ConcurrentHashMap<Long, Bot> singleBots;

    @Override
    public Bot getBot(Long id) {
        return singleBots.get(id);
    }

    @Override
    public List<Bot> getBots() {
        ArrayList<Bot> bots = new ArrayList<>();
        singleBots.forEach((k, v) -> bots.add(v));
        return bots;
    }

}
