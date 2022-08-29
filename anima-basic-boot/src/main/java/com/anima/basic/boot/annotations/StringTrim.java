package com.anima.basic.boot.annotations;

import java.lang.annotation.*;

/**
 * 字符串Trim格式化注解
 *
 * @author hww
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StringTrim {

}
