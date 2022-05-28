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

package io.github.jiusiz.core.exception;

/**
 * 相同的映射信息异常
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-14 下午 9:30
 */
public class EqualsMappingException extends RuntimeException {

    private String description1;

    private String description2;

    public EqualsMappingException() {
        super();
    }

    public EqualsMappingException(String description1, String description2) {
        super();
        this.description1 = description1;
        this.description2 = description2;
    }

    public EqualsMappingException(String message) {
        super(message);
    }

    public EqualsMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EqualsMappingException(Throwable cause) {
        super(cause);
    }

    protected EqualsMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getDescription1() {
        return description1;
    }

    public String getDescription2() {
        return description2;
    }
}
