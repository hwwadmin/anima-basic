package com.anima.basic.boot.annotations;

import java.lang.annotation.*;

/**
 * 耗时计算注解
 *
 * @author hww
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CostTime {
    String value() default "";
}
