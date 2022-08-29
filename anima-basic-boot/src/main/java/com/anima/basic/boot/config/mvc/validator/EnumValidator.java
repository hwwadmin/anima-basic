package com.anima.basic.boot.config.mvc.validator;

import com.anima.basic.boot.annotations.EnumValid;
import com.anima.basic.common.validate.EnumValidate;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 枚举校验
 *
 * @author hww
 * @date 2022/8/8
 */
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {

    private EnumValid valid;

    @Override
    public void initialize(EnumValid enumValid) {
        this.valid = enumValid;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 不校验空值直接允许通过，如果需要判空的话请结合使用其他相关注解
        if (Objects.isNull(value)) {
            return true;
        }
        if ((value instanceof String) && StringUtils.isBlank((String) value)) {
            return true;
        }

        // 校验
        EnumValidate[] enums = valid.enumClass().getEnumConstants();
        if (enums == null || enums.length == 0) {
            return false;
        }
        return enums[0].existValidate(value);
    }

}
