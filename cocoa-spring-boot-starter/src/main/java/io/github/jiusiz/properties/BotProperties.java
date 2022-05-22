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

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
