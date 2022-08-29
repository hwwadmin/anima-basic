package com.anima.basic.common.enums;

import com.anima.basic.common.exception.UtilsException;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * 字符集枚举
 *
 * @author hww
 */
public enum CharsetEnum {

    UTF8(1, "UTF-8"),
    GB2312(2, "GB2312"),
    GBK(3, "GBK"),
    ISO_8859_1(4, "ISO-8859-1"),
    GB18030(5, "GB18030"),
    ASCII(6, "US-ASCII"),
    BIG5(7, "BIG5"),
    ;

    final int key;
    final String value;

    CharsetEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * 转换成JAVA的 Charset
     */
    public static Charset getCharset(CharsetEnum charsetEnum) {
        if (Objects.isNull(charsetEnum)) {
            throw UtilsException.exception("要转换的字符集枚举不能为空");
        }
        try {
            return Charset.forName(charsetEnum.getValue());
        } catch (Exception e) {
            throw UtilsException.exception(String.format("找不到制定的编码集[%s]。", charsetEnum.getValue()));
        }
    }

}
