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

package io.github.jiusiz.core.context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.github.jiusiz.core.BotContainer;
import net.mamoe.mirai.Bot;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-04-19 下午 10:44
 */
public abstract class AbstractBotContainer implements BotContainer {

    private static final ConcurrentHashMap<Long, Bot> bots = new ConcurrentHashMap<>();

    private List<Bot> botsCache;

    @Override
    public Bot getBot(Long id) {
        return bots.get(id);
    }

    @Override
    public List<Bot> getBots() {
        if (botsCache == null || botsCache.size() != bots.values().size()) {
            ArrayList<Bot> botList = new ArrayList<>();
            bots.forEach((k, v) -> botList.add(v));
            botsCache = botList;
        }
        return botsCache;
    }

    public static void addBot(Bot bot) {
        bots.put(bot.getId(), bot);
    }

}
