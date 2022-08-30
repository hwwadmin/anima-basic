package com.anima.basic.framework.permission.service.impl;

import cn.hutool.core.lang.Assert;
import com.anima.basic.boot.core.mvc.CrudServiceImpl;
import com.anima.basic.common.enums.YesNoEnum;
import com.anima.basic.framework.permission.dao.PermissionDao;
import com.anima.basic.framework.permission.model.PermissionEntity;
import com.anima.basic.framework.permission.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 权限定义表服务实现
 *
 * @author hww
 * @date 2022/8/30
 */
@Service
public class PermissionServiceImpl extends CrudServiceImpl<PermissionEntity, PermissionDao> implements PermissionService {

    public PermissionServiceImpl(PermissionDao permissionDao) {
        super(permissionDao);
    }

    @Override
    public PermissionEntity findByUriAndMethod(String uri, String method) {
        // todo 后面在改造成缓存
        PermissionEntity permission = this.dao.findByUriAndMethodAndDeleteTimeIsNull(uri, method.toLowerCase());
        Assert.isTrue(Objects.nonNull(permission), "未知接口");
        Assert.isTrue(YesNoEnum.isYes(permission.getState()), "接口未启用");
        return permission;
    }

}
