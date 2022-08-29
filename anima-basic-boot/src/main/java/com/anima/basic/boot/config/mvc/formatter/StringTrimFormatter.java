package com.anima.basic.boot.config.mvc.formatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.util.Locale;

/**
 * 字符串Trim格式化
 *
 * @author hww
 */
public class StringTrimFormatter implements Formatter<String> {

    @Override
    public String parse(String s, Locale locale) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        return s.trim();
    }

    @Override
    public String print(String s, Locale locale) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        return s.trim();
    }

}
