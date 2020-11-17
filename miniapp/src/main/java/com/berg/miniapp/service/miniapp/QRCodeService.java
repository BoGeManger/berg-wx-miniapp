package com.berg.miniapp.service.miniapp;

import com.berg.vo.miniapp.in.MaCreateQRCodeInVo;
import com.berg.vo.miniapp.in.MaQRCodeGetInVo;
import com.berg.vo.miniapp.in.MaQRCodeGetUnlimitedInVo;

public interface QRCodeService {

    byte[] createQRCode(MaCreateQRCodeInVo input);

    byte[]  get(MaQRCodeGetInVo input);

    byte[]  getUnlimited(MaQRCodeGetUnlimitedInVo input);
}
