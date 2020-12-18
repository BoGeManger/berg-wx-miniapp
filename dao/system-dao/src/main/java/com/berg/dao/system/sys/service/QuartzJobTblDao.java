package com.berg.dao.system.sys.service;

import com.berg.dao.base.IService;
import com.berg.dao.system.sys.entity.QuartzJobTbl;
import com.berg.dao.system.sys.mapper.QuartzJobTblMapper;

/**
 * <p>
 * 系统定时任务表 服务类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
public interface QuartzJobTblDao extends IService<QuartzJobTbl> {
   QuartzJobTblMapper getMapper();
}
