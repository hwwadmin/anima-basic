package com.anima.basic.framework.user.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 密码登录DTO
 *
 * @author hww
 * @date 2022/8/30
 */
@Data
public class Login4PasswordDTO {

    @NotBlank(message = "用户名缺失")
    private String username;

    @NotBlank(message = "密码缺失")
    private String password;

}
