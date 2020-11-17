package com.berg.system.service.system;

import com.berg.dao.page.PageInfo;
import com.berg.vo.system.FilePathVo;
import com.berg.vo.system.FileVo;
import com.berg.vo.system.in.GetFilePageInVo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    PageInfo<FileVo> getFilePage(GetFilePageInVo input);

    FilePathVo uploadFile(MultipartFile file, String name, String code,Integer type);

    void delFileByName(String name);
}
