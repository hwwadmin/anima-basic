package com.anima.basic.boot.core.mvc;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReflectUtil;
import com.anima.basic.boot.core.lock.DistributedLock;
import com.anima.basic.boot.core.pojo.entity.BaseEntity;
import com.anima.basic.boot.core.redis.RedisSupport;
import com.anima.basic.common.exception.UtilsException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 基于缓存的curd通用服务抽象实现
 * 可和CrudServiceImpl简单切换，通用缓存基本不需要服务类去操作，如果服务层需要特殊格式的缓存自行定制化实现
 *
 * @author hww
 * @date 2022/8/25
 */
@Slf4j
public abstract class CrudCacheServiceImpl<T extends BaseEntity, DAO extends JpaRepository<T, Long>> extends CrudServiceImpl<T, DAO> {

    @Resource
    protected RedisSupport redisSupport;
    @Resource
    protected DistributedLock lock;
    protected String cacheKey;
    protected String lockPrefix;

    public CrudCacheServiceImpl(DAO dao) {
        super(dao);
        this.cacheKey = String.format("crud:c:%s:", tableName);
        this.lockPrefix = String.format("crud:l:%s:", tableName);
    }

    /**
     * 获取缓存过期时间 毫秒
     * 这个时间需要是在一个区间里面的随机数,不然同一时间key集体失效可能会导致缓存雪崩
     * 需要自行设置的时候重写该方法
     */
    protected long getCacheSurvivalTime() {
        // 默认存活20~30天
        return RandomUtil.randomLong(1728000000L, 2592000000L);
    }

    protected String getKey(Long id) {
        return this.cacheKey + id;
    }

    protected void delCache(Long... id) {
        delCache(Lists.newArrayList(id));
    }

    protected void delCache(Iterable<Long> ids) {
        this.redisSupport.del(Lists.newArrayList(ids).stream().map(this::getKey).collect(Collectors.toList()));
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public T save(T entity) {
        T t = super.save(entity);
        delCache(t.getId());
        return t;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public List<T> batchSave(Iterable<T> entities) {
        List<T> list = super.batchSave(entities);
        List<Long> ids = Lists.newArrayList(entities).stream().map(T::getId).collect(Collectors.toList());
        delCache(ids);
        return list;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public Optional<T> find(Long id) {
        T entity = (T) this.redisSupport.getOps4str().get(getKey(id));
        if (Objects.isNull(entity)) {
            // 去数据库同步的过程进行上锁，防止缓存击穿
            String key = lockPrefix + id;
            try {
                if (lock.tryLock(key, 100)) {
                    // 再去读取一次缓存，如果有读取到直接返回
                    entity = (T) this.redisSupport.getOps4str().get(getKey(id));
                    if (Objects.nonNull(entity)) {
                        // 查到了说明已经重新写过缓存了不需要再去查数据库直接返回
                        return Optional.of(entity);
                    }
                    Optional<T> optional = super.find(id);
                    if (!optional.isPresent()) {
                        // 数据不存在的时候写一个空对象到缓存，这个缓存持续5s，防止缓存穿透
                        this.redisSupport.getOps4str().set(getKey(id), ReflectUtil.newInstance(clazz), 5L, TimeUnit.SECONDS);
                        return optional;
                    }
                    entity = optional.get();
                    // 写缓存
                    this.redisSupport.getOps4str().set(getKey(id), entity, getCacheSurvivalTime(), TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                log.error("[{}]缓存进行数据库同步失败, id:[{}]", serviceName, id);
                throw UtilsException.exception(String.format("[%s]缓存同步查询失败，id:[%s]", serviceName, id));
            } finally {
                assert lock != null;
                lock.unlock(key);
            }
        } else {
            // 存在的时候需要校验id，如果id是个空的说明是个null数据,防止缓存穿透
            if (Objects.isNull(entity.getId())) {
                return Optional.empty();
            }
        }
        return Optional.of(entity);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public List<T> list(Iterable<Long> ids) {
        List<String> keys = Lists.newArrayList();
        ids.forEach(t -> keys.add(getKey(t)));
        List<T> data = this.redisSupport.getOps4str().batchGet(keys).stream()
                .filter(Objects::nonNull)
                .map(t -> (T) t)
                .collect(Collectors.toList());
        if (Objects.equals(keys.size(), data.size())) {
            // 数据长度一样的话说明都从缓存查询到直接返回
            return data;
        }
        // 获取未查询到数据的id列表
        Map<Long, T> map = data.stream().collect(Collectors.toMap(BaseEntity::getId, t -> t));
        List<Long> noFindIds = Lists.newArrayList();
        ids.forEach(t -> {
            if (!map.containsKey(t)) {
                noFindIds.add(t);
            }
        });
        noFindIds.stream().filter(Objects::nonNull).map(id -> {
            Optional<T> optional = find(id);
            return optional.orElse(null);
        }).filter(Objects::nonNull).forEach(data::add);
        return data;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public void del(Long id) {
        super.del(id);
        delCache(id);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public void batchDel(Iterable<Long> ids) {
        super.batchDel(ids);
        delCache(ids);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public void physicalDel(Long id) {
        super.physicalDel(id);
        delCache(id);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public void batchPhysicalDel(Iterable<Long> ids) {
        super.batchPhysicalDel(ids);
        delCache(ids);
    }

}
