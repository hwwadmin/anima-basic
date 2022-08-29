package com.anima.basic.framework.crashLog.dao;

import com.anima.basic.boot.core.pojo.dao.BaseRepository;
import com.anima.basic.framework.crashLog.model.CrashLogEntity;
import org.springframework.stereotype.Repository;

/**
 * 崩溃日志表DAO
 *
 * @author hww
 */
@Repository
public interface CrashLogDao extends BaseRepository<CrashLogEntity, Long> {

}
