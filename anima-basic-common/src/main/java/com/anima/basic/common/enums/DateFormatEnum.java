package com.anima.basic.common.enums;

/**
 * 日期类型枚举
 *
 * @author hww
 */
public enum DateFormatEnum {

    YYYY(0, "yyyy"),
    YYYYMM(1, "yyyyMM"),
    YYYYMMDD(2, "yyyyMMdd"),
    YYYYMMDDHH(3, "yyyyMMdd HH"),
    YYYYMMDDHHMM(4, "yyyyMMdd HH:mm"),
    YYYYMMDDHHMMSS(5, "yyyyMMdd HH:mm:ss"),
    YYYYMMDDHHMMSSS(6, "yyyyMMdd HH:mm:ss:SS"),
    YYYYVMMVDD(7, "yyyy-MM-dd"),
    YYYYVMMVDDHH(8, "yyyy-MM-dd HH"),
    YYYYVMMVDDHHMM(9, "yyyy-MM-dd HH:mm"),
    YYYYVMMVDDHHSS(10, "yyyy-MM-dd HH:mm:ss"),
    YYYYVMMVDDHHSSS(11, "yyyy-MM-dd HH:mm:ss:SS"),
    YYYYLMMLDD(12, "yyyy/MM/dd"),
    YYYYLMMLDDHH(13, "yyyy/MM/dd HH"),
    YYYYLMMLDDHHMM(14, "yyyy/MM/dd HH:mm"),
    YYYYLMMLDDHHSS(15, "yyyy/MM/dd HH:mm:ss"),
    YYYYLMMLDDHHSSS(16, "yyyy/MM/dd HH:mm:ss:SS"),
    YMDHMS(17, "yyyyMMddHHmmss"),
    YMDHMSS(18, "yyyyMMddHHmmssSS"),
    Y(19, "yyyy"),
    M(20, "MM"),
    D(21, "dd"),
    H(22, "HH"),
    m(23, "mm"),
    YYVMMVDDHHMM(24, "yy-MM-dd HH:mm"),
    YYMM(25, "yyMM"),
    YYMMDD(25, "yyMMdd"),
    ;

    final int key;
    final String value;

    DateFormatEnum(int key, String value) {
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
