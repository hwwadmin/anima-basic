package com.anima.basic.framework.secret;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.anima.basic.boot.config.interceptor.InterceptorInfo;
import com.anima.basic.boot.config.interceptor.InterceptorRegister;
import com.anima.basic.common.exception.IllegalValidatedException;
import com.anima.basic.framework.rbac.service.RbacService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SaToken拦截器注册
 *
 * @author hww
 * @date 2022/8/30
 */
@Component
public class SaTokenInterceptorRegister implements InterceptorRegister {

    @Autowired
    private RbacService rbacService;

    private static final List<String> notMatchList = ImmutableList.<String>builder()
            // static
            .add("*.js")
            .add("*.css")
            .add("*.html")
            .add("*.png")
            .add("*.jpg")
            .add("*.jpeg")
            // open api
            .add("/**/open/**")
            .add("/**/**/open/**")
            .build();

    @Override
    public List<InterceptorInfo> getInterceptorInfos() {
        List<InterceptorInfo> interceptorInfos = Lists.newArrayList();
        interceptorInfos.add(InterceptorInfo.builder()
                .name("SaRouteInterceptor")
                .instance(new SaRouteInterceptor((req, res, handler) -> SaRouter.match("/**")
                        .notMatch(notMatchList)
                        .check(r -> {
                            try {
                                // 用户认证
                                StpUtil.checkLogin();
                                // 角色鉴权
                                StpUtil.hasRoleOr(rbacService.getRoleIdsByRequest(req));
                            } catch (Exception e) {
                                // 异常包装
                                throw IllegalValidatedException.exception(e);
                            }
                        })))
                .order(1)
                .pathPatterns(Lists.newArrayList("/**"))
                .build());
        return interceptorInfos;
    }

}
