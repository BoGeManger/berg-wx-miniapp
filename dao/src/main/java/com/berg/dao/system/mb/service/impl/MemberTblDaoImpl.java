package com.berg.dao.system.mb.service.impl;

import com.berg.dao.system.mb.entity.MemberTbl;
import com.berg.dao.system.mb.mapper.MemberTblMapper;
import com.berg.dao.system.mb.service.MemberTblDao;
import com.berg.dao.base.ServiceImpl;
import org.springframework.stereotype.Repository;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-11-09
 */
@DS("system")
@Repository("system.MemberTblDaoImpl")
public class MemberTblDaoImpl extends ServiceImpl<MemberTblMapper, MemberTbl> implements MemberTblDao {

    @Override
    public MemberTblMapper getMapper(){
      DynamicDataSourceContextHolder.push("system");
      return this.getBaseMapper();
    }
}
