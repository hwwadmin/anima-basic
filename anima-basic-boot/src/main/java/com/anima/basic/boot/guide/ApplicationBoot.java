package com.anima.basic.boot.guide;

import com.anima.basic.boot.guide.listener.*;
import com.google.common.collect.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;

import java.util.Collection;

/**
 * 系统启动引导
 *
 * @author hww
 */
public abstract class ApplicationBoot {

    /**
     * 创建spring对象
     *
     * @return
     */
    public static SpringApplication boot(Class<?>... primarySources) {
        SpringApplication springApplication = new SpringApplication(primarySources);
        // 避免LINUX下文件名乱码
        System.setProperty("sun.jnu.encoding", "utf-8");
        // 不允许命令行设置属性
        springApplication.setAddCommandLineProperties(false);
        // 添加事件监听器
        Collection<ApplicationListener<?>> listeners = Lists.newArrayList();
        listeners.add(new ContextClosedEventListener());
        listeners.add(new ApplicationFailedEventListener());
        ApplicationListener<?>[] applicationListeners = new ApplicationListener[listeners.size()];
        listeners.toArray(applicationListeners);
        springApplication.addListeners(applicationListeners);
        return springApplication;
    }

}
