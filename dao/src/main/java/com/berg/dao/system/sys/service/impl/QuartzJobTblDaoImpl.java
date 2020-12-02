package com.berg.dao.system.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.berg.dao.base.ServiceImpl;
import com.berg.dao.constant.DataSource;
import com.berg.dao.system.sys.entity.QuartzJobTbl;
import com.berg.dao.system.sys.mapper.QuartzJobTblMapper;
import com.berg.dao.system.sys.service.QuartzJobTblDao;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统定时任务表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
@DS(DataSource.SYSTEM)
@Repository("system.QuartzJobTblDaoImpl")
public class QuartzJobTblDaoImpl extends ServiceImpl<QuartzJobTblMapper, QuartzJobTbl> implements QuartzJobTblDao {

    @Override
    public QuartzJobTblMapper getMapper(){
        DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
        return this.getBaseMapper();
    }
}
