package io.github.jiusiz.core;

import net.mamoe.mirai.Bot;
import org.springframework.lang.Nullable;
import java.util.List;

/**
 * 机器人bot实例的容器
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-04-05 下午 11:54
 */
public interface BotContainer {
    /**
     * 根据机器人id获取实例，若不存在则返回null
     * @param id QQ号
     * @return 机器人实例
     */
    @Nullable
    Bot getBot(Long id);

    /**
     * 获取容器中所有的机器人实例
     * @return Bot列表
     */
    List<Bot> getBots();


}
