package com.example.quartz;

import com.example.quartz.model.ScheduleJob;
import com.example.quartz.service.ScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootQuartzApplicationTests {

	@Autowired
	ScheduleService scheduleService;

	@Test
	public void contextLoads() throws SchedulerException {
		//查询正在运行的job
		List<ScheduleJob> runningJob = scheduleService.getRunningJob();
		List<ScheduleJob> allJob = scheduleService.getAllJob();
		System.err.println(runningJob);
	}

}
