package com.anima.basic.boot.core.pojo.entity;

import com.anima.basic.common.constants.WebFrameworkConstants;
import com.anima.basic.common.enums.StatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
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
@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class BaseSystemEntity extends BaseEntity implements Serializable {

    /**
     * UNAVAILABLE(0, "不可用"),
     * AVAILABLE(1, "可用的"),
     * DELETED(2, "软删除"),
     * {@link com.anima.basic.common.enums.StatusEnum}
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 记录创建用户编号
     */
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     * 记录最后修改用户编号
     */
    @Column(name = "modifier_id")
    private Long modifierId;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        if (Objects.isNull(this.creatorId)) {
            this.creatorId = WebFrameworkConstants.SYSTEM_USER_ID;
        }
        this.modifierId = WebFrameworkConstants.SYSTEM_USER_ID;
        if (Objects.isNull(this.status)) {
            this.status = StatusEnum.AVAILABLE.getKey();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseSystemEntity that = (BaseSystemEntity) o;
        return super.getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
