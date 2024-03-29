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

package io.github.jiusiz.properties;

import java.util.List;

import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-04-23 下午 9:38
 */
@ConfigurationProperties(prefix = "cocoa")
public class BotProperties {
    /**
     * 机器人账号和密码
     */
    private List<QQAccount> qq;

    /**
     * 设备信息路径
     */
    private String device = null;

    /**
     * 缓存路径
     */
    private String cache = null;

    /**
     * 是否自动重连
     */
    private Boolean autoReconnection = false;

    /**
     * 是否启用联系人缓存
     */
    private Boolean contactCache = false;

    /**
     * 心跳策略
     */
    private BotConfiguration.HeartbeatStrategy heartbeatStrategy = BotConfiguration.HeartbeatStrategy.STAT_HB;

    /**
     * 登录协议
     */
    private BotConfiguration.MiraiProtocol protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;

    public List<QQAccount> getQq() {
        verify();
        return qq;
    }

    public void setQq(List<QQAccount> qq) {
        this.qq = qq;
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

    public Boolean getContactCache() {
        return contactCache;
    }

    public void setContactCache(Boolean contactCache) {
        this.contactCache = contactCache;
    }

    public BotConfiguration.HeartbeatStrategy getHeartbeatStrategy() {
        return heartbeatStrategy;
    }

    public void setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy heartbeatStrategy) {
        this.heartbeatStrategy = heartbeatStrategy;
    }

    public BotConfiguration.MiraiProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(BotConfiguration.MiraiProtocol protocol) {
        this.protocol = protocol;
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
