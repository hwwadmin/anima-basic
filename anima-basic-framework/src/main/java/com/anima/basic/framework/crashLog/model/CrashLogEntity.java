package com.anima.basic.framework.crashLog.model;

import com.anima.basic.boot.core.pojo.entity.BaseEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * 崩溃日志表
 *
 * @author hww
 */
@Entity
@Table(name = "s_crash_log")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CrashLogEntity extends BaseEntity {

    /**
     * 崩溃操作
     */
    @Column(name = "operation")
    private String operation;

    /**
     * 异常描述
     */
    @Column(name = "message")
    private String message;

    /**
     * 异常栈信息
     */
    @Column(name = "stack_exception")
    private String stackException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CrashLogEntity that = (CrashLogEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
