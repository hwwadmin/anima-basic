package com.anima.basic.boot.core.mvc;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import com.anima.basic.boot.core.pojo.entity.BaseEntity;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * curd通用服务抽象实现
 *
 * @author hww
 * @date 2022/8/25
 */
public abstract class CrudServiceImpl<T extends BaseEntity, DAO extends JpaRepository<T, Long>> implements CrudService<T> {

    protected final DAO dao;
    protected final String serviceName;
    protected final Class<T> clazz;
    protected final String tableName;

    public CrudServiceImpl(DAO dao) {
        this.dao = dao; // dao由实现类构造函数注入，保证依赖
        this.serviceName = this.getClass().getSimpleName();
        this.clazz = (Class<T>) TypeUtil.getTypeArgument(this.getClass());
        this.tableName = AnnotationUtil.getAnnotationValue(clazz, javax.persistence.Table.class, "name");
    }

    @Override
    public T save(T entity) {
        return this.dao.save(entity);
    }

    @Override
    public List<T> batchSave(Iterable<T> entities) {
        return this.dao.saveAll(entities);
    }

    @Override
    public T get(Long id) {
        Optional<T> optional = this.find(id);
        Preconditions.checkArgument(optional.isPresent(), "[{}]主键编号查询缺失, id:[{}]", serviceName, id);
        return optional.get();
    }

    @Override
    public Optional<T> find(Long id) {
        return this.dao.findById(id).filter(t -> Objects.isNull(t.getDeleteTime()));
    }

    @Override
    public List<T> list(Iterable<Long> ids) {
        return this.dao.findAllById(ids).stream().filter(t -> Objects.isNull(t.getDeleteTime())).distinct().collect(Collectors.toList());
    }

    private T entity(T t) {
        return t;
    }

    @Override
    public Map<Long, T> dict(Iterable<Long> ids) {
        return list(ids).stream().collect(Collectors.toMap(T::getId, this::entity));
    }

    @Override
    public boolean exists(Long id) {
        return this.find(id).isPresent();
    }

    @Override
    public void del(Long id) {
        Optional<T> optional = this.find(id);
        if (optional.isEmpty()) {
            return;
        }
        T entity = optional.get();
        entity.setDeleteTime(Instant.now());
        this.dao.save(entity);
    }

    @Override
    public void batchDel(Iterable<Long> ids) {
        List<T> entities = this.list(ids);
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        Instant time = Instant.now();
        entities.forEach(t -> t.setDeleteTime(time));
        this.dao.saveAll(entities);
    }

    @Override
    public void physicalDel(Long id) {
        this.dao.deleteById(id);
    }

    @Override
    public void batchPhysicalDel(Iterable<Long> ids) {
        ArrayList<T> entities = Lists.newArrayList();
        for (Long id : ids) {
            T entity = ReflectUtil.newInstance(clazz);
            entity.setId(id);
            entities.add(entity);
        }
        this.dao.deleteAll(entities);
    }

}
