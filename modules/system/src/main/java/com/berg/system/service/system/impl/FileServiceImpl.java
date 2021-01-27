package com.berg.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.berg.common.MinioUtil;
import com.berg.common.constant.Bucket;
import com.berg.common.exception.FailException;
import com.berg.dao.page.PageInfo;
import com.berg.dao.system.sys.entity.FileTbl;
import com.berg.dao.system.sys.service.FileTblDao;
import com.berg.auth.system.service.AbstractService;
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
public class FileServiceImpl extends AbstractService implements FileService {

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
        String operator = super.getUsername();
        FilePathVo result = new FilePathVo();
        String url ="";
        try{
            url = MinioUtil.put(Bucket.MASTER,name,file);
        }catch (Exception ex){
            throw new FailException("上传文件失败："+ex.getMessage());
        }
        result.setFullPath(url);
        result.setPath(url.replace(MinioUtil.getMinioUrl(),""));
        result.setName(name);
        addOrUpdateFile(result,name,code,type,1,operator);
        return  result;
    }

    /**
     * 异步上传文件
     * @param file
     * @param name
     * @param code
     * @param type
     */
    @Override
    public void uploadFileAsync(MultipartFile file, String name, String code, Integer type){
        String operator = super.getUsername();
        Integer id = addOrUpdateFile(new FilePathVo(),name,code,type,0,operator);
        fileAsyncTask.uploadFile(file,name,id,operator);
    }

    /**
     * 新增或修改文件
     * @param filevo
     * @param name
     * @param code
     * @param type
     * @param user
     */
    Integer addOrUpdateFile(FilePathVo filevo, String name, String code, Integer type,Integer status, String user){
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
        fileTbl.setStatus(status);
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
        return fileTbl.getId();
    }

    /**
     * 删除文件
     * @param name 文件名称
     * @return
     */
    @Override
    public void delFileByName(String name){
        try{
            MinioUtil.remove(Bucket.MASTER,name);
        }catch (Exception ex){
            throw new FailException("删除文件失败："+ex.getMessage());
        }
        LocalDateTime now = LocalDateTime.now();
        String operator = super.getUsername();
        FileTbl fileTbl = new FileTbl();
        fileTbl.setIsdel(1);
        fileTbl.setDelUser(operator);
        fileTbl.setDelTime(now);
        LambdaUpdateWrapper query = new LambdaUpdateWrapper<FileTbl>().eq(FileTbl::getName,name).eq(FileTbl::getIsdel,0);
        fileTblDao.update(fileTbl,query);
    }
}
