package com.anima.basic.framework.sa;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.anima.basic.framework.AnimaFrameworkConstants;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 * 主要是返回用户对应的API权限列表
 *
 * @author hww
 * @date 2022/8/30
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // RBAC不对具体权限鉴权
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 角色从token-session读取
        return (List<String>) StpUtil.getTokenSession().get(AnimaFrameworkConstants.role);
    }

}
