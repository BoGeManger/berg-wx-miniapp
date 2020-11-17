package com.berg.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.berg.dao.system.sys.entity.FileTbl;
import com.berg.dao.system.sys.service.FileTblDao;
import com.berg.vo.system.FilePathVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FileAsyncTask {

    @Autowired
    FileTblDao fileTblDao;

    /**
     * 上传文件
     * @param name
     * @param code
     * @param type
     */
    @Async
    public void  uploadFile(FilePathVo filevo, String name, String code, Integer type, String user){
        LocalDateTime now = LocalDateTime.now();
        Boolean isAdd = true;
        LambdaQueryWrapper query = new LambdaQueryWrapper<FileTbl>().eq(FileTbl::getName,name);
        FileTbl fileTbl = fileTblDao.getOne(query);
        if(fileTbl!=null){
            isAdd = false;
        }else{
            fileTbl = new FileTbl();
            fileTbl.setName(name);
            fileTbl.setCreateTime(now);
            fileTbl.setCreateUser(user);
        }
        fileTbl.setModifyTime(now);
        fileTbl.setModifyUser(user);
        fileTbl.setCode(code);
        fileTbl.setPath(filevo.getPath());
        fileTbl.setFullPath(filevo.getFullPath());
        fileTbl.setType(type);
        fileTbl.setIsdel(0);
        if(isAdd){
            fileTblDao.save(fileTbl);
        }else {
            fileTblDao.updateById(fileTbl);
        }
    }

}
