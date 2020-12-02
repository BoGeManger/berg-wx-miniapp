package com.berg.dao.system.mb.service.impl;

import com.berg.dao.constant.DataSource;
import com.berg.dao.system.mb.entity.MaBindTbl;
import com.berg.dao.system.mb.mapper.MaBindTblMapper;
import com.berg.dao.system.mb.service.MaBindTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

/**
 * <p>
 * 会员小程序绑定表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-11-09
 */
@DS(DataSource.SYSTEM)
@Repository("system.MaBindTblDaoImpl")
public class MaBindTblDaoImpl extends ServiceImpl<MaBindTblMapper, MaBindTbl> implements MaBindTblDao {

    @Override
    public MaBindTblMapper getMapper(){
      DynamicDataSourceContextHolder.push(DataSource.SYSTEM);
      return this.getBaseMapper();
    }
}
