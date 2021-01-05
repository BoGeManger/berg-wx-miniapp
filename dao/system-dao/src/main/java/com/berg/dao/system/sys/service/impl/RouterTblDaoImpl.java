package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.base.ServiceImpl;
import com.berg.dao.constant.DataSource;
import com.berg.dao.system.sys.entity.RouterTbl;
import com.berg.dao.system.sys.mapper.RouterTblMapper;
import com.berg.dao.system.sys.service.RouterTblDao;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统路由表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-12-29
 */
@DS(DataSource.SYSTEM)
@Repository("system.RouterTblDaoImpl")
public class RouterTblDaoImpl extends ServiceImpl<RouterTblMapper, RouterTbl> implements RouterTblDao {

    @Override
    public RouterTblMapper getMapper(){
      DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
      return this.getBaseMapper();
    }
}
