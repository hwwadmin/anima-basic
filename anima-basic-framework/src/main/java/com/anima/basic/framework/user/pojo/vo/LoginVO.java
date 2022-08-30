package com.anima.basic.framework.user.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 令牌视图对象
 *
 * @author hww
 */
@Data
@Builder
public class LoginVO {

    private Long userId;

    private String accessToken;

}
