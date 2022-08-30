package com.anima.basic.framework.user.dao;

import com.anima.basic.boot.core.pojo.dao.BaseRepository;
import com.anima.basic.framework.user.model.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * 用户表DAO
 *
 * @author hww
 */
@Repository
public interface UserDao extends BaseRepository<UserEntity, Long> {

    UserEntity findByUsernameAndDeleteTimeIsNull(String username);

}
