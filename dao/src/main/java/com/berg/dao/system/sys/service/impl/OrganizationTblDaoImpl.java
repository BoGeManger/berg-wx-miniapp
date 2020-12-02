package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.constant.DataSource;
import com.berg.dao.system.sys.entity.OrganizationTbl;
import com.berg.dao.system.sys.mapper.OrganizationTblMapper;
import com.berg.dao.system.sys.service.OrganizationTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统组织表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
@DS(DataSource.SYSTEM)
@Repository("system.OrganizationTblDaoImpl")
public class OrganizationTblDaoImpl extends ServiceImpl<OrganizationTblMapper, OrganizationTbl> implements OrganizationTblDao {

    @Override
    public OrganizationTblMapper getMapper(){
      DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
      return this.getBaseMapper();
    }
}
