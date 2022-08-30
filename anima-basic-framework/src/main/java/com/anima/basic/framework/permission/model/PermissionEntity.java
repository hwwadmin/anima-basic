package com.anima.basic.framework.permission.model;

import com.anima.basic.boot.core.pojo.entity.BaseEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * 权限定义表表实体
 *
 * @author hww
 */
@Entity
@Table(name = "s_permission")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PermissionEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "uri")
    private String uri;

    @Column(name = "method")
    private String method;

    @Column(name = "state")
    private Integer state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PermissionEntity apiEntity = (PermissionEntity) o;
        return getId() != null && Objects.equals(getId(), apiEntity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
