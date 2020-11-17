package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.system.sys.entity.ComponentTbl;
import com.berg.dao.system.sys.mapper.ComponentTblMapper;
import com.berg.dao.system.sys.service.ComponentTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统组件表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
@DS("system")
@Repository("system.ComponentTblDaoImpl")
public class ComponentTblDaoImpl extends ServiceImpl<ComponentTblMapper, ComponentTbl> implements ComponentTblDao {

    @Override
    public ComponentTblMapper getMapper(){
      DynamicDataSourceContextHolder.push("system");
      return this.getBaseMapper();
    }
}
