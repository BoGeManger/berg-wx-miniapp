package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.constant.DataSource;
import com.berg.dao.system.sys.entity.FileTbl;
import com.berg.dao.system.sys.mapper.FileTblMapper;
import com.berg.dao.system.sys.service.FileTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统文件表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
@DS(DataSource.SYSTEM)
@Repository("system.FileTblDaoImpl")
public class FileTblDaoImpl extends ServiceImpl<FileTblMapper, FileTbl> implements FileTblDao {

    @Override
    public FileTblMapper getMapper(){
      DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
      return this.getBaseMapper();
    }
}
