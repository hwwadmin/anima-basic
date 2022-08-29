package com.anima.basic.common.enums;

/**
 * 状态类型枚举
 *
 * @author hww
 */
public enum StatusEnum {

    UNAVAILABLE(0, "不可用"),
    AVAILABLE(1, "可用的"),
    DELETED(2, "软删除"),
    ;

    final int key;
    final String value;

    StatusEnum(int key, String value) {
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
