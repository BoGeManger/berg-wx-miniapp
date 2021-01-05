package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.base.ServiceImpl;
import com.berg.dao.constant.DataSource;
import com.berg.dao.system.sys.entity.UserOrganizationTbl;
import com.berg.dao.system.sys.mapper.UserOrganizationTblMapper;
import com.berg.dao.system.sys.service.UserOrganizationTblDao;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户组织表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-12-24
 */
@DS(DataSource.SYSTEM)
@Repository("system.UserOrganizationTblDaoImpl")
public class UserOrganizationTblDaoImpl extends ServiceImpl<UserOrganizationTblMapper, UserOrganizationTbl> implements UserOrganizationTblDao {

    @Override
    public UserOrganizationTblMapper getMapper(){
      DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
      return this.getBaseMapper();
    }
}
