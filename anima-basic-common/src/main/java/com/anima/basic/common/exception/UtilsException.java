package com.anima.basic.common.exception;

/**
 * 工具类异常
 *
 * @author hww
 */
public class UtilsException extends AbstractRuntimeException {

    public UtilsException(String detailMessage) {
        super(detailMessage);
    }

    public UtilsException(Throwable e) {
        super(e);
    }

    public UtilsException(String detailMessage, Throwable e) {
        super(detailMessage, e);
    }

    public static UtilsException exception(String detailMessage) {
        return new UtilsException(detailMessage);
    }

    public static UtilsException exception(Throwable e) {
        return new UtilsException(e);
    }

    public static UtilsException exception(String detailMessage, Throwable e) {
        return new UtilsException(detailMessage, e);
    }
}
