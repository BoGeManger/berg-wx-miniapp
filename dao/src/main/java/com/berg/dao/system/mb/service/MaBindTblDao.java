package com.berg.dao.system.mb.service;

import com.berg.dao.system.mb.entity.MaBindTbl;
import com.berg.dao.base.IService;
import com.berg.dao.system.mb.mapper.MaBindTblMapper;

/**
 * <p>
 * 会员小程序绑定表 服务类
 * </p>
 *
 * @author 
 * @since 2020-11-09
 */
public interface MaBindTblDao extends IService<MaBindTbl> {
   MaBindTblMapper getMapper();
}
