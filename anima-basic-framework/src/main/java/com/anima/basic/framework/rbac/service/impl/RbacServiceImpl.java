package com.anima.basic.framework.rbac.service.impl;

import cn.dev33.satoken.context.model.SaRequest;
import com.anima.basic.boot.core.redis.RedisSupport;
import com.anima.basic.framework.permission.model.PermissionEntity;
import com.anima.basic.framework.permission.service.PermissionService;
import com.anima.basic.framework.rbac.dao.RbacRolePermissionDao;
import com.anima.basic.framework.rbac.dao.RbacUserRoleDao;
import com.anima.basic.framework.rbac.service.RbacService;
import com.anima.basic.framework.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * RBAC服务实现
 *
 * @author hww
 * @date 2022/8/30
 */
@Component
public class RbacServiceImpl implements RbacService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RbacUserRoleDao rbacUserRoleDao;
    @Autowired
    private RbacRolePermissionDao rbacRolePermissionDao;
    @Autowired
    private RedisSupport redisSupport;

    @Override
    public List<String> getRoleIdsByUserId(Long userId) {
        List<Long> roleIds = this.rbacUserRoleDao.getRoleIds(userId);
        return roleIds.stream().distinct().filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public String[] getRoleIdsByRequest(SaRequest request) {
        String uri = request.getRequestPath();
        String method = request.getMethod();
        PermissionEntity permission = this.permissionService.findByUriAndMethod(uri, method);
        // todo 后续缓存改造
        List<String> roleIds = this.rbacRolePermissionDao.getRoleIds(permission.getId())
                .stream().distinct().filter(Objects::nonNull).map(String::valueOf).toList();
        String[] array = new String[roleIds.size()];
        for (int i = 0; i < roleIds.size(); i++) {
            array[i] = roleIds.get(i);
        }
        return array;
    }

}
