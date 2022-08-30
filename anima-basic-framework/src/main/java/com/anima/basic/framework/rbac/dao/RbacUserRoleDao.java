package com.anima.basic.framework.rbac.dao;

import com.anima.basic.boot.core.pojo.dao.BaseRepository;
import com.anima.basic.framework.rbac.model.RbacUserRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RBAC用户角色控制表DAO
 *
 * @author hww
 */
@Repository
public interface RbacUserRoleDao extends BaseRepository<RbacUserRoleEntity, Long> {

    @Query("select t.roleId from RbacUserRoleEntity t where t.userId =:userId and t.deleteTime is null ")
    List<Long> getRoleIds(Long userId);

}
