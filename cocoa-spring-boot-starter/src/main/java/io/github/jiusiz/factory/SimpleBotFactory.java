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

package io.github.jiusiz.factory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.github.jiusiz.exception.DeviceInfoReadException;
import io.github.jiusiz.properties.BotProperties;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.LoggerAdapters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-15 下午 8:45
 */
public abstract class SimpleBotFactory {

    private static final Logger logger = LoggerFactory.getLogger(SimpleBotFactory.class);

    public static Bot createBot(Long qq, String password) {
        return BotFactory.INSTANCE.newBot(qq, password);
    }

    public static Bot createBot(Long qq, String password, BotProperties botProperties) {
        String specificDeviceJson = null;
        boolean useRandomDevice = true;
        boolean specificDevice = false;
        String canonicalPath = null;

        if (StringUtils.hasText(botProperties.getDevice())) {
            File deviceFile = new File("." + File.separator + botProperties.getDevice());
            if (deviceFile.exists()) {
                // 如果在当前文件夹下，读取
                try (FileInputStream in = new FileInputStream(deviceFile)) {
                    String json = readInputStream(in);
                    if (!StringUtils.hasText(json)) {
                        logger.warn("提供的device没有内容！将使用随机生成的device.json");
                    } else if (!json.contains("deviceInfoVersion")) {
                        useRandomDevice = false;
                        specificDevice = true;
                        specificDeviceJson = json;
                    } else {
                        useRandomDevice = false;
                        canonicalPath = deviceFile.getCanonicalPath();
                    }
                } catch (IOException e) {
                    throw new DeviceInfoReadException(e);
                }
            } else {
                // 不在当前文件夹下，可能在类路径里
                ClassPathResource deviceResource = new ClassPathResource(botProperties.getDevice());
                if (deviceResource.exists()) {
                    // 存在于类路径下
                    try {
                        InputStream inputStream = deviceResource.getInputStream();
                        String json = readInputStream(inputStream);
                        if (!StringUtils.hasText(json)) {
                            logger.warn("提供的device没有内容！将使用随机生成的device.json");
                        } else if (!json.contains("deviceInfoVersion")) {
                            specificDevice = true;
                            specificDeviceJson = json;
                            useRandomDevice = false;
                        } else {
                            useRandomDevice = false;
                            canonicalPath = deviceResource.getFile().getCanonicalPath();
                        }
                    } catch (IOException e) {
                        throw new DeviceInfoReadException(e);
                    }
                }
            }
        }

        Bot bot;
        final boolean finalUseRandomDevice = useRandomDevice;
        final boolean finalSpecificDevice = specificDevice;
        final String finalSpecificDeviceJson = specificDeviceJson;
        final String finalCanonicalPath = canonicalPath;
        bot = BotFactory.INSTANCE.newBot(qq, password, new BotConfiguration() {{
            // 配置登录设备信息
            if (finalUseRandomDevice) {
                fileBasedDeviceInfo();
            } else if (finalSpecificDevice) {
                loadDeviceInfoJson(finalSpecificDeviceJson);
            } else {
                fileBasedDeviceInfo(finalCanonicalPath);
            }

            // 配置缓存目录
            if (StringUtils.hasText(botProperties.getCache())) {
                setCacheDir(new File(botProperties.getCache() + File.separator + qq));
            }
            // 配置是否自动重连
            if (botProperties.getAutoReconnection()) {
                setAutoReconnectOnForceOffline(true);
            }
            // 是否缓存联系人
            if (botProperties.getContactCache()) {
                enableContactCache();
            } else {
                enableContactCache();
            }
            // 配置登录协议
            setProtocol(botProperties.getProtocol());
            // 配置心跳策略
            setHeartbeatStrategy(botProperties.getHeartbeatStrategy());
            // 设置日志代理
            setBotLoggerSupplier((bot) -> LoggerAdapters.asMiraiLogger(logger));
        }});
        return bot;
    }

    private static String readInputStream(InputStream in) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        byte[] bytes = bufferedInputStream.readAllBytes();
        return new String(bytes);
    }

}
