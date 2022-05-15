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
    private String device = null;
    private String cache = null;
    private Boolean autoReconnection = false;
    private Boolean contactCache = false;

    public List<QQAccount> getQq() {
        verify();
        return qq;
    }

    public Boolean getContactCache() {
        return contactCache;
    }

    public void setContactCache(Boolean contactCache) {
        this.contactCache = contactCache;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public Boolean getAutoReconnection() {
        return autoReconnection;
    }

    public void setAutoReconnection(Boolean autoReconnection) {
        this.autoReconnection = autoReconnection;
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
