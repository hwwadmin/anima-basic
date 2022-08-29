package com.anima.basic.common.exception;

import com.anima.basic.common.enums.StatusCodeEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * 基础运行期异常
 *
 * @author hww
 */
public abstract class AbstractRuntimeException extends RuntimeException {

    @Getter
    private final int code;

    @Getter
    private String detailMessage;

    public AbstractRuntimeException(String detailMessage) {
        super();
        this.code = StatusCodeEnum.commonError.getCode();
        this.detailMessage = detailMessage;
    }

    public AbstractRuntimeException(Throwable e) {
        super(e);
        this.code = StatusCodeEnum.commonError.getCode();
        if (Objects.nonNull(e)) {
            this.detailMessage = e.getMessage();
        }
    }

    public AbstractRuntimeException(String detailMessage, Throwable e) {
        super(e);
        this.code = StatusCodeEnum.commonError.getCode();
        this.detailMessage = detailMessage;
    }

    public AbstractRuntimeException(int code, String detailMessage) {
        super();
        this.code = code;
        this.detailMessage = detailMessage;
    }

    public AbstractRuntimeException(int code, String detailMessage, Throwable e) {
        super(e);
        this.code = code;
        this.detailMessage = detailMessage;
    }

    @Override
    public String getMessage() {
        return this.detailMessage;
    }

}
