package com.berg.dao.system.sys.service;

import com.berg.dao.system.sys.entity.RoleTbl;
import com.berg.dao.base.IService;
import com.berg.dao.system.sys.mapper.RoleTblMapper;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
public interface RoleTblDao extends IService<RoleTbl> {
   RoleTblMapper getMapper();
}
