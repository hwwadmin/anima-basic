package com.anima.basic.common.enums;

import com.anima.basic.common.validate.EnumValidate;

import java.util.Objects;

/**
 * 是否枚举
 *
 * @author hww
 */
public enum YesNoEnum implements EnumValidate<Integer> {

    NO(0, "否"),
    YES(1, "是"),
    ;

    final int key;
    final String value;

    YesNoEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public static boolean isYes(int value) {
        return Objects.equals(value, YES.getKey());
    }

    @Override
    public boolean existValidate(Integer parameter) {
        if (Objects.isNull(parameter)) {
            return true;
        }
        for (YesNoEnum enums : YesNoEnum.values()) {
            if (Objects.equals(enums.getKey(), parameter)) {
                return true;
            }
        }
        return false;
    }

}
