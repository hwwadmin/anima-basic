package com.anima.basic.framework.role.dao;

import com.anima.basic.boot.core.pojo.dao.BaseRepository;
import com.anima.basic.framework.role.model.RoleEntity;
import org.springframework.stereotype.Repository;

/**
 * 角色表DAO
 *
 * @author hww
 */
@Repository
public interface RoleDao extends BaseRepository<RoleEntity, Long> {

}
