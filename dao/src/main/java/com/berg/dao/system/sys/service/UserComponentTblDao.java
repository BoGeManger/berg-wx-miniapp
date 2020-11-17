package com.berg.dao.system.sys.service;

import com.berg.dao.system.sys.entity.UserComponentTbl;
import com.berg.dao.base.IService;
import com.berg.dao.system.sys.mapper.UserComponentTblMapper;

/**
 * <p>
 * 系统用户组件表 服务类
 * </p>
 *
 * @author 
 * @since 2020-09-23
 */
public interface UserComponentTblDao extends IService<UserComponentTbl> {
   UserComponentTblMapper getMapper();
}
