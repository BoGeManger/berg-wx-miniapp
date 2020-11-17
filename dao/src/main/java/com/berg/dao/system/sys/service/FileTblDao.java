package com.berg.dao.system.sys.service;

import com.berg.dao.system.sys.entity.FileTbl;
import com.berg.dao.base.IService;
import com.berg.dao.system.sys.mapper.FileTblMapper;

/**
 * <p>
 * 系统文件表 服务类
 * </p>
 *
 * @author 
 * @since 2020-06-18
 */
public interface FileTblDao extends IService<FileTbl> {
   FileTblMapper getMapper();
}
