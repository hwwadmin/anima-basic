package com.anima.basic.boot.pojo.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 基础JPA DAO对象
 *
 * @author hww
 */
@NoRepositoryBean
public interface BaseRepository<T, ID>
        extends PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {

}
