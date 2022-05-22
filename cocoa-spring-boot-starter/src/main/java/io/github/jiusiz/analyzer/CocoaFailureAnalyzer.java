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
