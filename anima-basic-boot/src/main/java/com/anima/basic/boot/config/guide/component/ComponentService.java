package com.anima.basic.boot.config.guide.component;

/**
 * 服务组件接口
 *
 * @author hww
 */
public interface ComponentService {

    /**
     * 启动组件
     */
    void start();

    /**
     * 停止组件
     */
    void stop();

    /**
     * 是否切换线程
     *
     * @return
     */
    boolean isSwitchThread();

    /**
     * 是否忽略异常
     *
     * @return
     */
    boolean ignoreError();

    /**
     * 获取组件名
     *
     * @return
     */
    String getName();

}
