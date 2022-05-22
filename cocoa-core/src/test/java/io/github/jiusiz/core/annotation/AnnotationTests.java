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

package io.github.jiusiz.core.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-10 下午 9:17
 */
public class AnnotationTests {

    @Test
    void AnnotatedUtilsTest() {
        AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(TestWithAnno.class, EventController.class);
        assert attributes != null;
        long botId = (long) attributes.get("botId");
        System.out.println(botId);
    }
}
