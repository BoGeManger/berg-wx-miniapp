package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.constant.DataSource;
import com.berg.dao.system.sys.entity.UserRoleTbl;
import com.berg.dao.system.sys.mapper.UserRoleTblMapper;
import com.berg.dao.system.sys.service.UserRoleTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户角色表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
@DS(DataSource.SYSTEM)
@Repository("system.UserRoleTblDaoImpl")
public class UserRoleTblDaoImpl extends ServiceImpl<UserRoleTblMapper, UserRoleTbl> implements UserRoleTblDao {

    @Override
    public UserRoleTblMapper getMapper(){
      DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
      return this.getBaseMapper();
    }
}
