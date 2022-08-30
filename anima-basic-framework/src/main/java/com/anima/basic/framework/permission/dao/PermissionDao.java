package com.anima.basic.framework.permission.dao;

import com.anima.basic.boot.core.pojo.dao.BaseRepository;
import com.anima.basic.framework.permission.model.PermissionEntity;
import org.springframework.stereotype.Repository;

/**
 * 权限定义表DAO
 *
 * @author hww
 */
@Repository
public interface PermissionDao extends BaseRepository<PermissionEntity, Long> {

    PermissionEntity findByUriAndMethodAndDeleteTimeIsNull(String uri, String method);

}
