package com.anima.basic.framework.user.service;

import com.anima.basic.boot.core.mvc.CrudService;
import com.anima.basic.framework.user.model.UserEntity;
import com.anima.basic.framework.user.pojo.vo.LoginVO;

/**
 * 用户领域服务
 *
 * @author hww
 */
public interface UserService extends CrudService<UserEntity> {

    LoginVO login4password(String username, String password);

}
