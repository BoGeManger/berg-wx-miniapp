package com.berg.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.berg.common.MinioUtil;
import com.berg.common.constant.Bucket;
import com.berg.common.exception.FailException;
import com.berg.dao.system.sys.entity.FileTbl;
import com.berg.dao.system.sys.service.FileTblDao;
import com.berg.vo.system.FilePathVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Component
public class FileAsyncTask {

    @Autowired
    FileTblDao fileTblDao;

    /**
     * 上传文件
     * @param file
     * @param name
     * @param id
     * @param user
     */
    @Async
    public void  uploadFile(MultipartFile file,String name,Integer id, String user){
        FilePathVo result = new FilePathVo();
        String url ="";
        try{
            url = MinioUtil.put(Bucket.MASTER,name,file);
        }catch (Exception ex){
            throw new FailException("上传文件失败："+ex.getMessage());
        }
        LocalDateTime now = LocalDateTime.now();
        FileTbl fileTbl = fileTblDao.getById(id);
        fileTbl.setModifyTime(now);
        fileTbl.setModifyUser(user);
        fileTbl.setPath(result.getPath());
        fileTbl.setFullPath(result.getFullPath());
        fileTbl.setStatus(1);
        fileTblDao.updateById(fileTbl);
    }

}
