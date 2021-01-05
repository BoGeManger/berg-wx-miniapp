package com.berg.dao.system.sys.service;

import com.berg.dao.base.IService;
import com.berg.dao.system.sys.entity.UserOrganizationTbl;
import com.berg.dao.system.sys.mapper.UserOrganizationTblMapper;

/**
 * <p>
 * 系统用户组织表 服务类
 * </p>
 *
 * @author 
 * @since 2020-12-24
 */
public interface UserOrganizationTblDao extends IService<UserOrganizationTbl> {
   UserOrganizationTblMapper getMapper();
}
