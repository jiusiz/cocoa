package io.github.jiusiz.core.context;

import io.github.jiusiz.core.BotContainer;
import net.mamoe.mirai.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-04-23 下午 9:29
 */
public class SingleBotContainer implements BotContainer {

    private static final ConcurrentHashMap<Long, Bot> bots = new ConcurrentHashMap<>();

    @Override
    public Bot getBot(Long id) {
        return bots.get(id);
    }

    @Override
    public List<Bot> getBots() {
        ArrayList<Bot> botList = new ArrayList<>();
        bots.forEach((k, v) -> botList.add(v));
        return botList;
    }

    public static void addBot(Bot bot) {
        bots.put(bot.getId(), bot);
    }
}
