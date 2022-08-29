package com.anima.basic.boot.core.pojo.entity;

import cn.hutool.core.util.IdUtil;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 表映射实体基础抽象类
 *
 * <p>
 * JPA实体基类 所有实体均需要继承该类 这意味着所有表必须包含该类的字段
 * </p>
 *
 * @author hww
 */
@SuperBuilder(toBuilder = true)
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Instant createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Instant updateTime;

    /**
     * 更新时间
     */
    @Column(name = "delete_time")
    private Instant deleteTime;

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(this.id)) {
            this.id = IdUtil.getSnowflakeNextId();
        }
        if (Objects.isNull(this.createTime)) {
            this.createTime = Instant.now();
        }
        this.updateTime = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
