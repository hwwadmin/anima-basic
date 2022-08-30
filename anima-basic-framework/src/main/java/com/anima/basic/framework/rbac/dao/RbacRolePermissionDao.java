package com.anima.basic.framework.rbac.dao;

import com.anima.basic.boot.core.pojo.dao.BaseRepository;
import com.anima.basic.framework.rbac.model.RbacRolePermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RBAC角色权限控制表DAO
 *
 * @author hww
 */
@Repository
public interface RbacRolePermissionDao extends BaseRepository<RbacRolePermissionEntity, Long> {

    @Query("select t.roleId from RbacRolePermissionEntity t where t.permissionId =:permissionId and t.deleteTime is null ")
    List<Long> getRoleIds(Long permissionId);

}
