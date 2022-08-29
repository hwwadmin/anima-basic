package com.anima.basic.common;

import cn.hutool.core.lang.Assert;
import com.anima.basic.common.exception.UtilsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * IOC帮助类
 * 依托SPRING实现基础性的IOC功能
 *
 * @author hww
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * spring上下文
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 根据BEAN的名称获取BEAN
     *
     * @param name bean的名称
     * @return 如果bean不存在会类型不正确，会抛出异常
     */
    public static <T> T getBean(String name) {
        Assert.isTrue(containsBean(name), "找不到名称为[%s]的IOC对象定义", name);
        try {
            return (T) applicationContext.getBean(name);
        } catch (Exception e) {
            throw UtilsException.exception(String.format("名称为[%s]的IOC对象转换错误", name), e);
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 获取SPRING的全局配置文件值
     * 如果key为空或key不存在，那么直接返回null
     *
     * @param key
     * @return
     */
    public static String getConfig(String key) {
        verifyApplicationContext();
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 根据关键字获取配置
     * 如果key为空或key不存在，那么直接返回指定的默认值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getConfig(String key, String defaultValue) {
        verifyApplicationContext();
        if (StringUtils.isBlank(key)) {
            return defaultValue;
        }
        if (!applicationContext.getEnvironment().containsProperty(key)) {
            return defaultValue;
        }
        try {
            return applicationContext.getEnvironment().getProperty(key);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    /**
     * 判断是否存在指定名称的BEAN
     *
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        verifyApplicationContext();
        Assert.isTrue(StringUtils.isNotBlank(name), "需要获取的对象名称不能为空");
        return applicationContext.containsBean(name);
    }

    /**
     * 校验上下文是否初始化
     */
    private static void verifyApplicationContext() {
        Assert.isTrue(Objects.nonNull(applicationContext), "上下文未初始化");
    }

}
