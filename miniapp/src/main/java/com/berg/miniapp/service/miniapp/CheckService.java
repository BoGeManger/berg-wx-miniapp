package com.berg.miniapp.service.miniapp;

import com.berg.vo.miniapp.in.MaMsgSecCheckInVo;
import org.springframework.web.multipart.MultipartFile;

public interface CheckService {

    Boolean imgSecCheck(MultipartFile multipartFile);

    Boolean msgSecCheck(MaMsgSecCheckInVo input);
}
