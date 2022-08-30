package com.anima.basic.framework.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.anima.basic.framework.user.pojo.dto.Login4PasswordDTO;
import com.anima.basic.framework.user.pojo.vo.LoginVO;
import com.anima.basic.framework.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块开放请求控制器
 *
 * @author hww
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户密码登录
     */
    @PostMapping("/open/login/password")
    public LoginVO passwordLogin(@RequestBody Login4PasswordDTO dto) {
        return this.userService.login4password(dto.getUsername(), dto.getPassword());
    }

    /**
     * 令牌校验
     */
    @PostMapping("/checkToken")
    public void checkToken() {
        log.info("token:{}", StpUtil.getTokenInfo());
    }

}
