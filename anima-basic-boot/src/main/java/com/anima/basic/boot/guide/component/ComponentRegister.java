package com.anima.basic.boot.guide.component;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 组件自动注册
 *
 * @author hww
 * @date 2022/4/14
 */
@Component
public class ComponentRegister {

    @Autowired(required = false)
    private List<ComponentService> componentServices;

    public List<ComponentService> getComponent() {
        if (CollectionUtils.isEmpty(componentServices)) {
            return Lists.newArrayList();
        }
        return componentServices;
    }

}
