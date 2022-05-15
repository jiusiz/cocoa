package io.github.jiusiz.factory;

import io.github.jiusiz.properties.BotProperties;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-15 下午 8:45
 */
public abstract class SimpleBotFactory {

    public static Bot createBot(Long qq, String password) {
        return BotFactory.INSTANCE.newBot(qq, password);
    }

    public static Bot createBot(Long qq, String password, BotProperties botProperties) {
        String device = null;
        try {
            device = readStringByClassPath(botProperties.getDevice());
        } catch (IOException ignored) {
            // 没有读取到，我们使用默认的device
        }
        final String finalDevice = device;
        return BotFactory.INSTANCE.newBot(qq, password, new BotConfiguration() {{
            // 配置登录设备信息
            if (StringUtils.hasText(finalDevice) && finalDevice.contains("deviceInfoVersion")) {
                loadDeviceInfoJson(finalDevice);
            } else if (StringUtils.hasText(botProperties.getDevice())) {
                fileBasedDeviceInfo(botProperties.getDevice());
            } else {
                fileBasedDeviceInfo();
            }
            // 配置缓存目录
            if (StringUtils.hasText(botProperties.getCache())) {
                setCacheDir(new File(botProperties.getCache() + qq));
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
        }});
    }

    private static String readStringByClassPath(String resource) throws IOException {

        ClassPathResource classPathResource = new ClassPathResource(resource);
        if (!classPathResource.exists()) {
            throw new FileNotFoundException("文件：" + resource + " 不存在");
        }
        return readInputStream(classPathResource.getInputStream());
    }

    private static String readInputStream(InputStream in) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        byte[] bytes;
        try {
            bytes = bufferedInputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(bytes);
    }

}
