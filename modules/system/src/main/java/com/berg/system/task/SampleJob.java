package com.berg.system.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

/**
 * 示例不带参定时任务
 *
 * @Author Scott
 */
@Slf4j
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info(String.format("普通定时任务SampleJob 时间：%s",LocalDateTime.now()));
    }
}