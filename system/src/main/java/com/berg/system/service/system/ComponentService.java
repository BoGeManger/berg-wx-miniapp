package com.berg.system.service.system;

import com.berg.vo.common.ListVo;
import com.berg.vo.system.ComponentEditVo;
import com.berg.vo.system.ComponentTreeVo;
import com.berg.vo.system.in.OperatorBatchComInVo;

import java.util.List;

public interface ComponentService {

    ListVo<ComponentTreeVo> getComTree();

    ComponentEditVo getCom(Integer id);

    void operatorBatchCom(OperatorBatchComInVo input);

}
