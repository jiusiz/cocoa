package io.github.jiusiz.core.exception;

/**
 * @author jiusiz
 * @version 0.1.0
 * @since 2022-05-14 下午 9:30
 */
public class EqualsControllerException extends RuntimeException{
    public EqualsControllerException() {
        super();
    }

    public EqualsControllerException(String message) {
        super(message);
    }

    public EqualsControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EqualsControllerException(Throwable cause) {
        super(cause);
    }

    protected EqualsControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
