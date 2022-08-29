package com.anima.basic.common.exception;

/**
 * 参数校验异常
 *
 * @author hww
 */
public class IllegalValidatedException extends AbstractRuntimeException {

    public IllegalValidatedException(String detailMessage) {
        super(detailMessage);
    }

    public IllegalValidatedException(Throwable e) {
        super(e);
    }

    public IllegalValidatedException(String detailMessage, Throwable e) {
        super(detailMessage, e);
    }

    public static IllegalValidatedException exception(String detailMessage) {
        return new IllegalValidatedException(detailMessage);
    }

    public static IllegalValidatedException exception(Throwable e) {
        return new IllegalValidatedException(e);
    }

    public static IllegalValidatedException exception(String detailMessage, Throwable e) {
        return new IllegalValidatedException(detailMessage, e);
    }
}
