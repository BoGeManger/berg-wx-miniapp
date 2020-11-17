package com.berg.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.berg.dao.page.PageInfo;
import com.berg.dao.system.sys.entity.FileTbl;
import com.berg.dao.system.sys.service.FileTblDao;
import com.berg.exception.FailException;
import com.berg.file.MinioUtil;
import com.berg.system.auth.JWTUtil;
import com.berg.system.service.system.FileService;
import com.berg.vo.system.FilePathVo;
import com.berg.vo.system.FileVo;
import com.berg.vo.system.in.GetFilePageInVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class FileServiceImpl  implements FileService {

    @Autowired
    JWTUtil jWTUtil;
    @Autowired
    FileAsyncTask fileAsyncTask;

    @Autowired
    FileTblDao fileTblDao;

    /**
     * 获取文件列表
     * @return
     */
    @Override
    public PageInfo<FileVo> getFilePage(GetFilePageInVo input){
        PageInfo<FileVo> page = fileTblDao.page(input,()->{
            QueryWrapper query = new QueryWrapper<FileTbl>().eq("isdel",0);
            if(StringUtils.isNotBlank(input.getName())) {
                query.eq("name",input.getName());
            }
            if(StringUtils.isNotBlank(input.getCode())) {
                query.eq("code",input.getCode());
            }
            if(input.getType()!=null){
                if(input.getType()!=-1){
                    query.eq("type",input.getType());
                }
            }
            query.orderByDesc("create_time");
            return fileTblDao.list(query,FileVo.class);
        });
        return page;
    }

    /**
     * 上传文件
     * @param file
     * @param name
     * @param code
     * @return
     */
    @Override
    public FilePathVo uploadFile(MultipartFile file, String name, String code, Integer type){
        String operator = jWTUtil.getUsername();
        FilePathVo result = new FilePathVo();
        String url ="";
        try{
            url = MinioUtil.putByName(name,file);
        }catch (Exception ex){
            throw new FailException("上传文件失败："+ex.getMessage());
        }
        result.setFullPath(url);
        result.setPath(url.replace(MinioUtil.getMinioUrl(),""));
        result.setName(name);
        fileAsyncTask.uploadFile(result,name,code,type,operator);
        return  result;
    }

    /**
     * 删除文件
     * @param name 文件名称
     * @return
     */
    @Override
    public void delFileByName(String name){
        try{
            MinioUtil.removeByName(name);
        }catch (Exception ex){
            throw new FailException("删除文件失败："+ex.getMessage());
        }
        LocalDateTime now = LocalDateTime.now();
        String operator = jWTUtil.getUsername();
        FileTbl fileTbl = new FileTbl();
        fileTbl.setIsdel(1);
        fileTbl.setDelUser(operator);
        fileTbl.setDelTime(now);
        LambdaUpdateWrapper query = new LambdaUpdateWrapper<FileTbl>().eq(FileTbl::getName,name).eq(FileTbl::getIsdel,0);
        fileTblDao.update(fileTbl,query);
    }
}
