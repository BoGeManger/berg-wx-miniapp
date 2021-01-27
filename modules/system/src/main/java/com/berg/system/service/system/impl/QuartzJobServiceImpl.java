package com.berg.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berg.dao.base.DSTransactional;
import com.berg.dao.page.PageInfo;
import com.berg.dao.system.sys.entity.QuartzJobTbl;
import com.berg.dao.system.sys.service.QuartzJobTblDao;
import com.berg.common.exception.FailException;
import com.berg.common.exception.UserFriendException;
import com.berg.auth.system.service.AbstractService;
import com.berg.system.service.system.QuartzJobService;
import com.berg.vo.system.JobEditVo;
import com.berg.vo.system.JobVo;
import com.berg.vo.system.in.GetJobPageInVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class QuartzJobServiceImpl extends AbstractService implements QuartzJobService {

    @Autowired
    Scheduler scheduler;
    @Autowired
    QuartzJobTblDao quartzJobTblDao;

    /**
     * 获取定时任务分页
     * @param input
     * @return
     */
    @Override
    public PageInfo<JobVo> getJobPage(GetJobPageInVo input){

        PageInfo<JobVo> page = quartzJobTblDao.page(input, ()->{
            QueryWrapper query = new QueryWrapper<QuartzJobTbl>().eq("isdel",0);
            if(StringUtils.isNotBlank(input.getName())){
                query.like("name",input.getName());
            }
            if(StringUtils.isNotBlank(input.getJobClassName())){
                query.like("job_class_name",input.getJobClassName());
            }
            if(input.getStatus()!=null && input.getStatus()!=-1){
                query.eq("status",input.getStatus());
            }
            query.orderByDesc("create_time");
            return quartzJobTblDao.list(query,JobVo.class);
        });

        return page;
    }

    /**
     * 获取定时任务
     * @param id
     * @return
     */
    @Override
    public JobEditVo getJob(Integer id){
        JobEditVo result = quartzJobTblDao.getById(id,JobEditVo.class);
        return result;
    }

    /**
     * 新增定时任务
     * @param input
     * @return
     */
    @DSTransactional
    @Override
    public Integer addJob(JobEditVo input){
        LambdaQueryWrapper queryName = new LambdaQueryWrapper<QuartzJobTbl>()
                .eq(QuartzJobTbl::getName,input.getName())
                .eq(QuartzJobTbl::getIsdel,0);
        if(quartzJobTblDao.count(queryName)>0){
            throw new UserFriendException("任务名称已存在");
        }
        input.setJobClassName(input.getJobClassName().trim());
        LambdaQueryWrapper queryClass = new LambdaQueryWrapper<QuartzJobTbl>()
                .eq(QuartzJobTbl::getJobClassName,input.getJobClassName())
                .eq(QuartzJobTbl::getIsdel,0);
        if(quartzJobTblDao.count(queryClass)>0){
            throw new UserFriendException("任务类名已存在");
        }
        String operator = getUsername();
        LocalDateTime now = LocalDateTime.now();
        QuartzJobTbl quartzJobTbl = new QuartzJobTbl();
        BeanUtils.copyProperties(input, quartzJobTbl);
        quartzJobTbl.setStatus(0);
        quartzJobTbl.setCreateUser(operator);
        quartzJobTbl.setCreateTime(now);
        quartzJobTbl.setModifyUser(operator);
        quartzJobTbl.setModifyTime(now);
        quartzJobTbl.setIsdel(0);
        quartzJobTblDao.save(quartzJobTbl);
        schedulerAdd(input.getJobClassName(),input.getCronExpression(),input.getParameter());
        return quartzJobTbl.getId();
    }

    /**
     * 添加定时任务
     *
     * @param jobClassName
     * @param cronExpression
     * @param parameter
     */
    void schedulerAdd(String jobClassName, String cronExpression, String parameter) {
        try {
            // 启动调度器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName).usingJobData("parameter", parameter).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception ex) {
            throw new FailException("创建定时任务失败："+ex.getMessage());
        }
    }

    Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }

    /**
     * 删除定时任务
     * @param id
     */
    @DSTransactional
    @Override
    public void delJob(Integer id){
        String operator = getUsername();
        LocalDateTime now = LocalDateTime.now();
        QuartzJobTbl quartzJobTbl = quartzJobTblDao.getById(id);
        quartzJobTbl.setIsdel(1);
        quartzJobTbl.setDelTime(now);
        quartzJobTbl.setDelUser(operator);
        quartzJobTbl.setStatus(1);
        quartzJobTblDao.updateById(quartzJobTbl);
        schedulerDelete(quartzJobTbl.getJobClassName());
    }

    /**
     * 删除定时任务
     *
     * @param jobClassName
     */
    void schedulerDelete(String jobClassName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName));
        } catch (Exception ex) {
            throw new FailException("删除定时任务失败："+ex.getMessage());
        }
    }

    /**
     * 暂停定时任务
     * @param id
     */
    @DSTransactional
    @Override
    public void pauseJob(Integer id){
        String operator = getUsername();
        LocalDateTime now = LocalDateTime.now();
        QuartzJobTbl quartzJobTbl = quartzJobTblDao.getById(id);
        if(quartzJobTbl.getIsdel().equals(1)){
            throw new UserFriendException("定时任务已删除");
        }
        if(quartzJobTbl.getStatus().equals(1)){
            throw new UserFriendException("定时任务已暂停");
        }
        quartzJobTbl.setStatus(1);
        quartzJobTbl.setModifyTime(now);
        quartzJobTbl.setModifyUser(operator);
        quartzJobTblDao.updateById(quartzJobTbl);
        try{
            scheduler.pauseJob(JobKey.jobKey(quartzJobTbl.getJobClassName()));
        }catch (Exception ex){
            throw new FailException("暂停定时任务失败："+ex.getMessage());
        }
    }

    /**
     * 启动定时任务
     * @param id
     */
    @DSTransactional
    @Override
    public void resumeJob(Integer id){
        String operator = getUsername();
        LocalDateTime now = LocalDateTime.now();
        QuartzJobTbl quartzJobTbl = quartzJobTblDao.getById(id);
        if(quartzJobTbl.getIsdel().equals(1)){
            throw new UserFriendException("定时任务已删除");
        }
        if(quartzJobTbl.getStatus().equals(0)){
            throw new UserFriendException("定时任务未暂停");
        }
        quartzJobTbl.setStatus(0);
        quartzJobTbl.setModifyTime(now);
        quartzJobTbl.setModifyUser(operator);
        quartzJobTblDao.updateById(quartzJobTbl);
        try{
            schedulerDelete(quartzJobTbl.getJobClassName());
            schedulerAdd(quartzJobTbl.getJobClassName(), quartzJobTbl.getCronExpression(), quartzJobTbl.getParameter());
        }catch (Exception ex){
            throw new FailException("启动定时任务失败："+ex.getMessage());
        }
    }

}
