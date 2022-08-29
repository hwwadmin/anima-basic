package com.anima.basic.boot.mvc;

import com.anima.basic.boot.pojo.entity.BaseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * curd通用服务标准化接口
 *
 * @author hww
 * @date 2022/8/25
 */
public interface CrudService<T extends BaseEntity> {

    /**
     * 保存
     */
    T save(T entity);

    /**
     * 批量保存
     */
    List<T> batchSave(Iterable<T> entities);

    /**
     * 主键查询
     */
    T get(Long id);

    /**
     * 主键查询
     * 不抛出异常，必然会获取Optional对象
     */
    Optional<T> find(Long id);

    /**
     * 批量主键查询
     */
    List<T> list(Iterable<Long> ids);

    /**
     * key = id，value = entity
     */
    Map<Long, T> dict(Iterable<Long> ids);

    /**
     * 是否存在
     */
    boolean exists(Long id);

    /**
     * 软删除
     */
    void del(Long id);

    /**
     * 批量软删除
     */
    void batchDel(Iterable<Long> ids);

    /**
     * 物理删除
     */
    void physicalDel(Long id);

    /**
     * 批量物理删除
     */
    void batchPhysicalDel(Iterable<Long> ids);

}
