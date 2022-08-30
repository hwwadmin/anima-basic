package com.anima.basic.framework.role.service.impl;

import com.anima.basic.boot.core.mvc.CrudServiceImpl;
import com.anima.basic.framework.role.dao.RoleDao;
import com.anima.basic.framework.role.model.RoleEntity;
import com.anima.basic.framework.role.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现
 *
 * @author hww
 * @date 2022/8/30
 */
@Service
public class RoleServiceImpl extends CrudServiceImpl<RoleEntity, RoleDao> implements RoleService {

    public RoleServiceImpl(RoleDao roleDao) {
        super(roleDao);
    }

}
