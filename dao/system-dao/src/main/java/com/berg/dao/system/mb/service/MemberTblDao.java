package com.berg.dao.system.mb.service;

import com.berg.dao.system.mb.entity.MemberTbl;
import com.berg.dao.base.IService;
import com.berg.dao.system.mb.mapper.MemberTblMapper;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 
 * @since 2020-11-09
 */
public interface MemberTblDao extends IService<MemberTbl> {
   MemberTblMapper getMapper();
}
