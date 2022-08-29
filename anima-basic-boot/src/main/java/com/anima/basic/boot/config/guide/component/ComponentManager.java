package com.anima.basic.boot.config.guide.component;

import com.anima.basic.common.SpringContextUtils;
import com.anima.basic.common.exception.UtilsException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 组件服务统一管理类
 * 由该管理类统一管理所有系统组件服务
 *
 * @author hww
 */
@Slf4j
public class ComponentManager {

    private boolean isRun;

    private final Lock lock;

    private final ComponentRegister register;

    private static class ComponentManagerHolder {
        private static final ComponentManager INSTANCE = new ComponentManager();
    }

    public static ComponentManager getInstance() {
        return ComponentManagerHolder.INSTANCE;
    }

    private ComponentManager() {
        this.isRun = false;
        this.lock = new ReentrantLock();
        this.register = SpringContextUtils.getBean(ComponentRegister.class);
    }

    /**
     * 启动组件管理
     */
    public void start() {
        this.lock.lock();
        try {
            if (this.isRun) {
                return;
            }
            this.isRun = true;
            log.info("开始启动系统服务组件，共需要启动[{}]个", this.register.getComponent().size());
            this.register.getComponent().forEach(t -> {
                if (t.isSwitchThread()) {
                    // 切换新线程
                    Thread thread = new Thread(() -> this.startComponent(t));
                    thread.setName("Thread-Component-" + t.getName());
                    thread.start();
                } else {
                    // 不切换新线程
                    this.startComponent(t);
                }
            });
        } catch (Exception e) {
            log.error("启动组件管理异常:{}", e.getMessage());
            throw UtilsException.exception(e);
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * 停止组件管理
     */
    public void stop() {
        this.lock.lock();
        try {
            if (!this.isRun) {
                return;
            }
            this.isRun = false;
            for (ComponentService componentService : this.register.getComponent()) {
                try {
                    componentService.stop();
                } catch (Exception e) {
                    // 停止失败打个日志忽略掉
                    log.error("停止组件[{}]失败", componentService.getName(), e);
                }
            }
        } catch (Exception e) {
            log.error("停止组件管理异常:{}", e.getMessage());
            throw UtilsException.exception(e);
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * 启动组件
     * 启动失败直接关停整个服务
     *
     * @param componentService
     */
    private void startComponent(ComponentService componentService) {
        try {
            long time = System.currentTimeMillis();
            log.info("####### ------- component start: [{}]  ------- #######", componentService.getName());
            componentService.start();
            log.info("####### ------- component end: [{}], 启动耗时：[{}ms] ------- #######",
                    componentService.getName(), System.currentTimeMillis() - time);
        } catch (Exception e) {
            log.error("服务组件出现异常", e);
            if (componentService.ignoreError()) {
                log.info("该服务组件配置了忽略异常，略过异常继续启动");
                return;
            }
            // 不忽略异常的话直接终止启动
            log.info("服务组件启动异常，系统关停");
            System.exit(-9);
        }
    }

}
