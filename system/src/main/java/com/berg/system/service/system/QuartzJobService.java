package com.berg.system.service.system;

import com.berg.dao.page.PageInfo;
import com.berg.vo.system.JobEditVo;
import com.berg.vo.system.JobVo;
import com.berg.vo.system.in.GetJobPageInVo;

public interface QuartzJobService {

    PageInfo<JobVo> getJobPage(GetJobPageInVo input);

    JobEditVo getJob(Integer id);

    Integer addJob(JobEditVo input);

    void delJob(Integer id);

    void pauseJob(Integer id);

    void resumeJob(Integer id);
}
