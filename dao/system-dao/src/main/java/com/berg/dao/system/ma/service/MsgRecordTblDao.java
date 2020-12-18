package com.berg.dao.system.ma.service;

import com.berg.dao.system.ma.entity.MsgRecordTbl;
import com.berg.dao.base.IService;
import com.berg.dao.system.ma.mapper.MsgRecordTblMapper;

/**
 * <p>
 * 小程序模板消息记录表 服务类
 * </p>
 *
 * @author 
 * @since 2020-11-26
 */
public interface MsgRecordTblDao extends IService<MsgRecordTbl> {
   MsgRecordTblMapper getMapper();
}
