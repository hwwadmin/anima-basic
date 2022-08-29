package com.anima.basic.boot.core.pojo.vo;

import com.anima.basic.boot.utils.JsonUtils;
import com.anima.basic.common.exception.UtilsException;

import java.util.Map;
import java.util.function.Function;

/**
 * 通用的分页查询结果map集合转bean对象
 *
 * @author hww
 * @date 2022/8/2
 */
public class Page4MapConverter<V> implements Function<Map<String, Object>, V> {

    private final Class<V> vClass;

    public Page4MapConverter(Class<V> voClass) {
        this.vClass = voClass;
    }

    @Override
    public V apply(Map<String, Object> t) {
        try {
            return JsonUtils.map2bean(t, vClass);
        } catch (Exception e) {
            throw UtilsException.exception(e);
        }
    }

}
