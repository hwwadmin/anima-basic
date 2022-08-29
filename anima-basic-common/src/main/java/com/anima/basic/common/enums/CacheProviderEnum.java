package com.anima.basic.common.enums;

/**
 * 缓存提供商枚举
 *
 * @author hww
 */
public enum CacheProviderEnum {

    JVM(1, "jvm"),
    REDIS(2, "redis"),
    EHCACHE(3, "ehcache"),
    JCACHE(4, "jcache"),
    GUAVA(5, "guava"),
    ;

    final int key;
    final String value;

    CacheProviderEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

}
