package com.berg.dao.system.ma.service.impl;

import com.berg.dao.system.ma.entity.MsgRecordTbl;
import com.berg.dao.system.ma.mapper.MsgRecordTblMapper;
import com.berg.dao.system.ma.service.MsgRecordTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

/**
 * <p>
 * 小程序模板消息记录表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-11-26
 */
@DS("system")
@Repository("system.MsgRecordTblDaoImpl")
public class MsgRecordTblDaoImpl extends ServiceImpl<MsgRecordTblMapper, MsgRecordTbl> implements MsgRecordTblDao {

    @Override
    public MsgRecordTblMapper getMapper(){
      DynamicDataSourceContextHolder.push("system");
      return this.getBaseMapper();
    }
}
