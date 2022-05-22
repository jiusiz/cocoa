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
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-13 下午 4:50
 */
public class AnnotationNotFoundException extends RuntimeException{
    public AnnotationNotFoundException() {
        super();
    }

    public AnnotationNotFoundException(String message) {
        super(message);
    }

    public AnnotationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AnnotationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
