package com.anima.basic.boot.utils;

import com.anima.basic.common.enums.DateFormatEnum;
import com.anima.basic.common.enums.DateUnitEnum;
import com.anima.basic.common.exception.UtilsException;
import com.google.common.collect.Maps;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 时间工具类
 * 主要是对JDK8的time包进行封装
 * 包含对日期、时刻、时间戳的相关操作
 *
 * @author hww
 * @date 2022/8/5
 */
public abstract class TimeUtils {

    // 默认使用中国时区UTC+8
    private static final ZoneOffset zone = ZoneOffset.of("+8");
    // DateTimeFormatter缓存集合，key是字符串pattern
    private static final Map<String, DateTimeFormatter> formatCache = Maps.newHashMap();
    private static final Object sync = new Object();

    static {
        // 初始化当前系统通常需要的一些日期格式
        putDateTimeFormatter2Cache(DateFormatEnum.YYMM.getValue());
        putDateTimeFormatter2Cache(DateFormatEnum.YYYYMM.getValue());
        putDateTimeFormatter2Cache(DateFormatEnum.YYYYMMDD.getValue());
        putDateTimeFormatter2Cache(DateFormatEnum.YYYYMMDDHHMMSS.getValue());
        putDateTimeFormatter2Cache(DateFormatEnum.YYYYVMMVDD.getValue());
        putDateTimeFormatter2Cache(DateFormatEnum.YYYYVMMVDDHHSS.getValue());
    }

    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        if (!formatCache.containsKey(pattern)) {
            putDateTimeFormatter2Cache(pattern);
        }
        return formatCache.get(pattern);
    }

    private static void putDateTimeFormatter2Cache(String pattern) {
        synchronized (sync) {
            if (formatCache.containsKey(pattern)) {
                return;
            }
            formatCache.put(pattern, DateTimeFormatter.ofPattern(pattern).withZone(zone));
        }
    }


    /**
     * 获取当前时间戳
     */
    public static Long getCurrentTimestamp() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 获取当前时刻
     */
    public static Instant getCurrentInstant() {
        return Instant.now();
    }

    /**
     * 获取当前日期
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }


    // ------------------------------------ transform start ----------------------------------------------

    /**
     * 时间戳转时刻
     */
    public static Instant timestamp2Instant(Long timeStamp) {
        return Instant.ofEpochMilli(timeStamp);
    }

    /**
     * 时间戳转日期
     */
    public static LocalDateTime timestamp2Date(Long timeStamp) {
        return instant2Date(timestamp2Instant(timeStamp));
    }

    /**
     * 时刻转时间戳
     */
    public static long instant2Timestamp(Instant instant) {
        return instant.toEpochMilli();
    }

    /**
     * 时刻转日期
     */
    public static LocalDateTime instant2Date(Instant instant) {
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 日期转时间戳
     */
    public static long date2Timestamp(LocalDateTime localDateTime) {
        return date2Instant(localDateTime).toEpochMilli();
    }

    /**
     * 日期转时刻
     */
    public static Instant date2Instant(LocalDateTime localDateTime) {
        return localDateTime.toInstant(zone);
    }

    // ------------------------------------ transform end ----------------------------------------------

    // ------------------------------------ format start ----------------------------------------------

    /**
     * 时间戳类型格式化
     */
    public static String format(Long timeStamp, DateFormatEnum pattern) {
        return format(timeStamp, pattern.getValue());
    }

    /**
     * 时间戳类型格式化
     */
    public static String format(Long timeStamp, String pattern) {
        return getDateTimeFormatter(pattern).format(timestamp2Instant(timeStamp));
    }

    /**
     * 时刻类型格式化
     */
    public static String format(Instant instant, DateFormatEnum pattern) {
        return format(instant, pattern.getValue());
    }

    /**
     * 时刻类型格式化
     */
    public static String format(Instant instant, String pattern) {
        return getDateTimeFormatter(pattern).format(instant);
    }

    /**
     * 日期类型格式化
     */
    public static String format(LocalDateTime localDateTime, DateFormatEnum pattern) {
        return format(localDateTime, pattern.getValue());
    }

    /**
     * 日期类型格式化
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        return getDateTimeFormatter(pattern).format(localDateTime);
    }

    /**
     * 使用当期时间格式化
     */
    public static String formatNow(DateFormatEnum pattern) {
        return formatNow(pattern.getValue());
    }

    /**
     * 使用当期时间格式化
     */
    public static String formatNow(String pattern) {
        return format(getCurrentInstant(), pattern);
    }

    // ------------------------------------ format end ----------------------------------------------


    // ------------------------------------ of start ----------------------------------------------

    /**
     * 设置时间
     * 参数需要符合时间规定，如果不符合会引发异常。
     *
     * @param year         年
     * @param month        月份，1-12
     * @param dayOfMonth   日期，31以内
     * @param hour         小时，0-23
     * @param minute       分钟，0-59
     * @param second       秒，0-59
     * @param nanoOfSecond 毫秒，0-999
     */
    public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond) {
        try {
            return LocalDateTime.of(year, Month.of(month), dayOfMonth, hour, minute, second, nanoOfSecond);
        } catch (Exception e) {
            throw UtilsException.exception(String.format("构建日期对象错误：%s %s %s %s %s %s %s", year, month, dayOfMonth, hour, minute, second, nanoOfSecond), e);
        }
    }

    /**
     * 设置时间
     */
    public static LocalDateTime of(int year, int month, int dayOfMonth) {
        return of(year, month, dayOfMonth, 0, 0, 0, 0);
    }

    /**
     * 设置时间
     */
    public static LocalDateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second) {
        return of(year, month, dayOfMonth, hour, minute, second, 0);
    }

    // ------------------------------------ of end ----------------------------------------------

    /**
     * 获取当前年份
     */
    public static int getYear() {
        return getCurrentDateTime().getYear();
    }

    /**
     * 获取当前月份
     */
    public static int getMonth() {
        return getCurrentDateTime().getMonth().getValue();
    }

    /**
     * 获取当前日期
     */
    public static int getDayOfMonth() {
        return getCurrentDateTime().getDayOfMonth();
    }

    /**
     * 获取当前小时
     */
    public static int getHour() {
        return getCurrentDateTime().getHour();
    }

    /**
     * 获取当前分钟
     */
    public static int getMinute() {
        return getCurrentDateTime().getMinute();
    }

    /**
     * 指定时间偏移
     *
     * @param time   起点时间
     * @param unit   偏移的时间单位
     * @param offset 偏移量
     */
    public static LocalDateTime offset(LocalDateTime time, DateUnitEnum unit, int offset) {
        if (offset == 0) {
            // 偏移量为0直接返回也不需要去抛异常
            return time;
        }
        switch (unit) {
            case YEAR:
                return offset > 0 ? time.plusYears(offset) : time.minusYears(-offset);
            case MONTH:
                return offset > 0 ? time.plusMonths(offset) : time.minusMonths(-offset);
            case DAY:
                return offset > 0 ? time.plusDays(offset) : time.minusDays(-offset);
            case HOUR:
                return offset > 0 ? time.plusHours(offset) : time.minusHours(-offset);
            case MINUTE:
                return offset > 0 ? time.plusMinutes(offset) : time.minusMinutes(-offset);
            case SECOND:
                return offset > 0 ? time.plusSeconds(offset) : time.minusSeconds(-offset);
            case NANOSECOND:
                return offset > 0 ? time.plusNanos(offset) : time.minusNanos(-offset);
            default:
                return time;
        }
    }

    /**
     * 判断两个日期相差的时长
     *
     * @param begin 起始日期
     * @param end   结束日期
     * @param unit  相差的单位
     * @return 日期差
     */
    public static long between(LocalDateTime begin, LocalDateTime end, DateUnitEnum unit) {
        switch (unit) {
            case YEAR:
                return end.getYear() - begin.getYear();
            case MONTH:
                long years = between(begin, end, DateUnitEnum.YEAR);
                return years * 12 + (end.getMonthValue() - begin.getMonthValue());
            case DAY:
                return Duration.between(begin, end).toDays();
            case HOUR:
                return Duration.between(begin, end).toHours();
            case MINUTE:
                return Duration.between(begin, end).toMinutes();
            case SECOND:
                return Duration.between(begin, end).toMillis() / 1000;
            case NANOSECOND:
                return Duration.between(begin, end).toNanos();
            default:
                return 0L;
        }
    }

    /**
     * 判断两个日期相差的时长
     *
     * @param begin 起始日期
     * @param end   结束日期
     * @param unit  相差的单位
     * @return 日期差
     */
    public static long between(Instant begin, Instant end, DateUnitEnum unit) {
        return between(instant2Date(begin), instant2Date(end), unit);
    }

}
