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

package io.github.jiusiz.analyzer;

import io.github.jiusiz.exception.BotCreateException;
import io.github.jiusiz.exception.DeviceInfoReadException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-22 下午 7:02
 */
public class BotCreateFailureAnalyzer extends AbstractFailureAnalyzer<BotCreateException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, BotCreateException cause) {
        if (DeviceInfoReadException.class.equals(cause.getClass())) {
            return new FailureAnalysis("device信息读取错误\n异常信息：" + cause.getMessage(),
                    "请修改应用中的cocoa.device配置", cause);
        }
        return null;
    }
}
