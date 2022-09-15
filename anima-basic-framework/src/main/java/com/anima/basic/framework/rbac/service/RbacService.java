package com.anima.basic.framework.rbac.service;

import java.util.List;

/**
 * RBAC服务
 *
 * @author hww
 * @date 2022/8/30
 */
public interface RbacService {

    /**
     * 根据用户编号获取角色
     */
    List<String> getRoleIdsByUserId(Long userId);

    /**
     * 根据请求获取具有改请求权限的所有角色列表
     */
    String[] getRoleIds4Request(String uri, String method);

}
