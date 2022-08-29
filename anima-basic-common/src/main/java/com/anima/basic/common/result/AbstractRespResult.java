package com.anima.basic.common.result;

import com.anima.basic.common.constants.AppConstants;
import com.anima.basic.common.enums.StatusCodeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 响应抽象类
 *
 * @author hww
 */
public abstract class AbstractRespResult {

    /**
     * 响应状态
     */
    @Getter
    private final int code;

    /**
     * 响应消息
     */
    @Getter
    private final String msg;

    protected AbstractRespResult() {
        this.code = StatusCodeEnum.succeed.getCode();
        this.msg = StatusCodeEnum.succeed.getDefaultMessage();
    }

    protected AbstractRespResult(int code, String message) {
        super();
        this.code = code;
        if (StringUtils.isBlank(message)) {
            this.msg = AppConstants.EMPTY_STRING;
        } else {
            this.msg = message;
        }
    }

    protected AbstractRespResult(@NotNull StatusCodeEnum statusCode, String message) {
        super();
        this.code = statusCode.getCode();
        if (StringUtils.isBlank(message)) {
            this.msg = statusCode.getDefaultMessage();
        } else {
            this.msg = message;
        }
    }

    @JsonIgnore
    public boolean isSucceed() {
        return Objects.equals(this.code, StatusCodeEnum.succeed.getCode());
    }

}
