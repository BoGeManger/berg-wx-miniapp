package com.berg.system.service.system;

import com.berg.vo.common.ListVo;
import com.berg.vo.system.OrganizationEditVo;
import com.berg.vo.system.OrganizationTreeVo;
import com.berg.vo.system.in.OperatorBatchOrganizationInVo;

public interface OrganizationService {

    ListVo<OrganizationTreeVo> getOrganizationTree();

    OrganizationEditVo getOrganization(Integer id);

    Integer addOrganization(OrganizationEditVo input);

    Integer updateOrganization(OrganizationEditVo input);

    void operatorBatchOrganization(OperatorBatchOrganizationInVo input);

}
