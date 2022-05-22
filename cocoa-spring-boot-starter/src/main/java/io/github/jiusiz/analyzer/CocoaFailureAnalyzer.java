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

import io.github.jiusiz.core.exception.EqualsControllerException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-21 上午 10:27
 */
public class CocoaFailureAnalyzer extends AbstractFailureAnalyzer<EqualsControllerException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, EqualsControllerException cause) {
        return new FailureAnalysis("遇到了两个相同的控制器", "请将这两个相同的控制器更改为不同的", cause);
    }

}
