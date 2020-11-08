package com.example.quartz.jobs;

import com.example.quartz.service.BaseJob;
import com.example.quartz.service.ScheduleService;
import com.example.quartz.tool.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class JobThree implements BaseJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleService scheduleService = (ScheduleService)SpringUtil.getBean("scheduleService");
        scheduleService.springSchedule3();
    }
}
