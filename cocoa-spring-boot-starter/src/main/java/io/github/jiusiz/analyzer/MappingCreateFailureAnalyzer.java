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

import io.github.jiusiz.core.exception.CreateMappingException;
import io.github.jiusiz.core.exception.mapping.AnnotationNotFoundException;
import io.github.jiusiz.core.exception.mapping.EqualsMappingException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 0.1.0 2022-05-21 上午 10:27
 */
public class MappingCreateFailureAnalyzer extends AbstractFailureAnalyzer<CreateMappingException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, CreateMappingException cause) {
        if (cause instanceof AnnotationNotFoundException) {
            return new FailureAnalysis(cause.getMessage(),
                    "您可以提交issue来帮助我们排查问题\nhttps://github.com/jiusiz/cocoa/issues/new ", cause);
        }
        if (cause instanceof EqualsMappingException) {
            String description1 = ((EqualsMappingException) cause).getDescription1();
            String description2 = ((EqualsMappingException) cause).getDescription2();
            String description = "检测到以下的方法注解信息完全相同\n" + description1 + "\n" + description2 + "\n";
            return new FailureAnalysis(
                    description, "请修改其中任意一个的注解信息", cause);
        }
        return null;
    }

}
