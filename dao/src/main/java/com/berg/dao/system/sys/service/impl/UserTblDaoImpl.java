package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.constant.DataSource;
import com.berg.dao.system.sys.entity.UserTbl;
import com.berg.dao.system.sys.mapper.UserTblMapper;
import com.berg.dao.system.sys.service.UserTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
@DS(DataSource.SYSTEM)
@Repository("system.UserTblDaoImpl")
public class UserTblDaoImpl extends ServiceImpl<UserTblMapper, UserTbl> implements UserTblDao {

    @Override
    public UserTblMapper getMapper(){
      DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
      return this.getBaseMapper();
    }
}
