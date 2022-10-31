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

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-04-23 下午 9:39
 */
public class QQAccount {
    /**
     * QQ账号
     */
    private Long account;

    /**
     * 密码
     */
    private String password;

    /**
     * 经过MD5编码后的密码，能防止密码明文泄露
     */
    private String pwdMd5;

    public Long getAccount() {
        return account;
    }

    public String getPwdMd5() {
        return pwdMd5;
    }

    public void setPwdMd5(String pwdMd5) {
        this.pwdMd5 = pwdMd5;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "QQAccount{" +
                "account=" + account +
                ", password='" + password + '\'' +
                ", pwdMd5='" + pwdMd5 + '\'' +
                '}';
    }
}
