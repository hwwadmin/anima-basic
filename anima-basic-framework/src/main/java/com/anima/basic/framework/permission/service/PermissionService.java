package com.anima.basic.framework.permission.service;

import com.anima.basic.boot.core.mvc.CrudService;
import com.anima.basic.framework.permission.model.PermissionEntity;

/**
 * 权限定义表服务
 *
 * @author hww
 * @date 2022/8/30
 */
public interface PermissionService extends CrudService<PermissionEntity> {

    PermissionEntity findByUriAndMethod(String uri, String method);

}
