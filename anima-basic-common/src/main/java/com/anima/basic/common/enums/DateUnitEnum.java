package com.anima.basic.common.enums;

/**
 * 日期类型枚举
 *
 * @author hww
 * @date 2022/8/5
 */
public enum DateUnitEnum {

    YEAR(0, "年"),
    MONTH(1, "月"),
    DAY(2, "日"),
    HOUR(3, "时"),
    MINUTE(4, "分"),
    SECOND(5, "秒"),
    NANOSECOND(6, "毫秒"),
    ;

    final int key;
    final String value;

    DateUnitEnum(int key, String value) {
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
