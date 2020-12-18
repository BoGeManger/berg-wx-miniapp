package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.constant.DataSource;
import com.berg.dao.system.sys.entity.RoleComponentTbl;
import com.berg.dao.system.sys.mapper.RoleComponentTblMapper;
import com.berg.dao.system.sys.service.RoleComponentTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统角色组件表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
@DS(DataSource.SYSTEM)
@Repository("system.RoleComponentTblDaoImpl")
public class RoleComponentTblDaoImpl extends ServiceImpl<RoleComponentTblMapper, RoleComponentTbl> implements RoleComponentTblDao {

    @Override
    public RoleComponentTblMapper getMapper(){
      DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
      return this.getBaseMapper();
    }
}
