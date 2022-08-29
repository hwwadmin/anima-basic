package com.anima.basic.boot.config.db;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class CustomIdentityGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
        if (id == null) {
            return super.generate(s, obj);
        }
        if (!(id instanceof Long)) {
            throw new IllegalArgumentException("使用 CustomIdentityGenerator 时，id 必须为 Long 类型");
        }
        return id;
    }
}
