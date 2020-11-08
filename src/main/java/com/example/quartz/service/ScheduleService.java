package com.example.quartz.service;

import com.example.quartz.model.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleService {


    @Autowired
    @Qualifier("Scheduler")
    Scheduler scheduler;


    private int count1 = 1;
    private int count2 = 1;


    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getRunningJob() throws SchedulerException {
        //SchedulerFactory sf = new StdSchedulerFactory();
        //Scheduler scheduler = sf.getScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getAllJob() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        //SchedulerFactory sf = new StdSchedulerFactory();
        //Scheduler scheduler = sf.getScheduler();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }



    @Scheduled(cron="0/3 * * * * *")
    //@Async
    public void springSchedule(){ //11
        //if(count1>10){return;}else {
            int seconds = new Date().getSeconds();
            sleep(5);
            System.out.println("1a开始在"+seconds+" id: "+Thread.currentThread().getId()+" "+ Thread.currentThread().getName()+" " +" "+count1);
            count1++;
        //}
    }

    @Scheduled(cron="0/2 * * * * *")
    //@Async
    public void springSchedule2(){ //10
        //if(count2<10){
            int seconds = new Date().getSeconds();
            sleep(5);
            System.out.println("5b开始在"+seconds+" id: "+Thread.currentThread().getId()+" "+ Thread.currentThread().getName()+" " + count2);
            count2++;
        //}
    }

    @Scheduled(cron="0/1 * * * * *")
    //@Async
    public void springSchedule3(){ //20
        //if(count2<20){
            int seconds = new Date().getSeconds();
            sleep(2);
            System.out.println("333开始在"+seconds+" id: "+Thread.currentThread().getId()+" "+ Thread.currentThread().getName()+" " + count2);
            count2++;
        //}
    }



    public void sleep(int second){
        try {
            Thread.sleep(second*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
