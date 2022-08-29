package com.anima.basic.boot.annotations;


import com.anima.basic.boot.config.mvc.validator.EnumValidator;
import com.anima.basic.common.validate.EnumValidate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举类校验的自定义注解
 *
 * @author hww
 * @date 2022/8/8
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
@Documented
public @interface EnumValid {

    String message() default "";

    Class<? extends EnumValidate<?>> enumClass();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?>[] target() default {};

}
