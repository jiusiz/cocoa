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
