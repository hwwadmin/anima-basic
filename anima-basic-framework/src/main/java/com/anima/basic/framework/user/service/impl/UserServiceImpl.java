package com.anima.basic.framework.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.anima.basic.boot.core.mvc.CrudServiceImpl;
import com.anima.basic.boot.core.password.PasswordEncoder;
import com.anima.basic.framework.user.dao.UserDao;
import com.anima.basic.framework.user.model.UserEntity;
import com.anima.basic.framework.user.pojo.vo.LoginVO;
import com.anima.basic.framework.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户领域服务实现
 *
 * @author hww
 */
@Service
@Slf4j
public class UserServiceImpl extends CrudServiceImpl<UserEntity, UserDao> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao) {
        super(userDao);
    }

    @Override
    public LoginVO login4password(String username, String password) {
        UserEntity user = this.dao.findByUsernameAndDeleteTimeIsNull(username);
        Assert.isTrue(passwordEncoder.matches(password, user.getPassword()), "用户名或密码错误");
        StpUtil.login(user.getId());
        return LoginVO.builder()
                .userId(user.getId())
                .accessToken(StpUtil.getTokenValue())
                .build();
    }

}
