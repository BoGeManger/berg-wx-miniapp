package com.berg.dao.system.sys.service;

import com.berg.dao.base.IService;
import com.berg.dao.system.sys.entity.RouterTbl;
import com.berg.dao.system.sys.mapper.RouterTblMapper;

/**
 * <p>
 * 系统路由表 服务类
 * </p>
 *
 * @author 
 * @since 2020-12-29
 */
public interface RouterTblDao extends IService<RouterTbl> {
   RouterTblMapper getMapper();
}
