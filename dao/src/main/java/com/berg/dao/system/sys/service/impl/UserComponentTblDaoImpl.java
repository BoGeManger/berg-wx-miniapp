package com.berg.dao.system.sys.service.impl;

import com.berg.dao.system.sys.entity.UserComponentTbl;
import com.berg.dao.system.sys.mapper.UserComponentTblMapper;
import com.berg.dao.system.sys.service.UserComponentTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

/**
 * <p>
 * 系统用户组件表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-09-23
 */
@DS("system")
@Repository("system.UserComponentTblDaoImpl")
public class UserComponentTblDaoImpl extends ServiceImpl<UserComponentTblMapper, UserComponentTbl> implements UserComponentTblDao {

    @Override
    public UserComponentTblMapper getMapper(){
      DynamicDataSourceContextHolder.push("system");
      return this.getBaseMapper();
    }
}
