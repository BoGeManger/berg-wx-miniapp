package com.berg.dao.system.sys.service;

import com.berg.dao.system.sys.entity.RoleComponentTbl;
import com.berg.dao.base.IService;
import com.berg.dao.system.sys.mapper.RoleComponentTblMapper;

/**
 * <p>
 * 系统角色组件表 服务类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
public interface RoleComponentTblDao extends IService<RoleComponentTbl> {
   RoleComponentTblMapper getMapper();
}
