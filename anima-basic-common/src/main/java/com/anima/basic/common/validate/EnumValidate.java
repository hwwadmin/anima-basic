package com.anima.basic.common.validate;

/**
 * 枚举值校验
 *
 * @author hww
 * @date 2022/8/8
 */
public interface EnumValidate<T> {

    /**
     * 校验枚举值是否存在
     */
    boolean existValidate(T parameter);

}
