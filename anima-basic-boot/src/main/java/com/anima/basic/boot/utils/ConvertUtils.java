package com.anima.basic.boot.utils;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 对象转换工具类
 *
 * <p>
 * 简单的对象转换，主要用于一些RO、VO和DTO的转换
 * 复杂对象转换不要使用该工具类
 * </p>
 *
 * @author hww
 */
public abstract class ConvertUtils {

    public static <T, V> V convert(T source, Class<V> clazz, BiConsumer<T, V> function) {
        V target = ReflectUtil.newInstance(clazz);
        return convertObj(source, target, function);
    }

    public static <T, V> V convert(T source, Class<V> clazz, Consumer<V> function) {
        V target = ReflectUtil.newInstance(clazz);
        return convertObj(source, target, (s, t) -> function.accept(t));
    }

    public static <T, V> V convertObj(T source, V target, BiConsumer<T, V> function) {
        if (Objects.isNull(source)) {
            return target;
        }
        // 浅克隆属性
        BeanUtils.copyProperties(source, target);
        if (Objects.nonNull(function)) {
            // 自定义转换
            function.accept(source, target);
        }
        return target;
    }

    public static <T, V> V convert(T source, Class<V> clazz) {
        return convert(source, clazz, (t) -> {
        });
    }

    public static <T, V> List<V> convertBatch(List<T> source, Class<V> clazz, BiConsumer<T, V> function) {
        List<V> target = Lists.newArrayList();
        if (CollectionUtils.isEmpty(source)) {
            return target;
        }
        for (T t : source) {
            target.add(convert(t, clazz, function));
        }
        return target;
    }

    public static <T, V> List<V> convertBatch(List<T> source, Class<V> clazz) {
        return convertBatch(source, clazz, null);
    }

    public static <T, V> Page<V> convertPage(Page<T> page, Class<V> clazz, BiConsumer<T, V> function) {
        return page.map(t -> convert(t, clazz, function));
    }

    public static <T, V> Page<V> convertPage(Page<T> page, Class<V> clazz) {
        return convertPage(page, clazz, null);
    }

}
