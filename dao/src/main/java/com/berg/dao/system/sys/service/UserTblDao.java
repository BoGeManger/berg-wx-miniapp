package com.berg.dao.system.sys.service;

import com.berg.dao.system.sys.entity.UserTbl;
import com.berg.dao.base.IService;
import com.berg.dao.system.sys.mapper.UserTblMapper;

/**
 * <p>
 * 系统用户信息表 服务类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
public interface UserTblDao extends IService<UserTbl> {
   UserTblMapper getMapper();
}
