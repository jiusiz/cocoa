package io.github.jiusiz.properties;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-04-23 下午 9:38
 */
@ConfigurationProperties(prefix = "cocoa")
public class BotProperties {
    private List<QQAccount> qq;

    public List<QQAccount> getQq() {
        verify();
        return qq;
    }

    public void setQq(List<QQAccount> qq) {
        this.qq = qq;
    }

    public void verify() {
        if (qq == null || qq.size() == 0) {
            throw new BotContainerBeanCreateException();
        }
    }

    public String toString() {
        return "BotProperties{" +
                "qq=" + qq +
                '}';
    }

    static class BotContainerBeanCreateException extends BeanCreationException {
        BotContainerBeanCreateException() {
            super("BotContainer创建失败，请检查cocoa.qq配置");
        }
    }
}
