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
