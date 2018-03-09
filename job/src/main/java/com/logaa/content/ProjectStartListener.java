package com.logaa.content;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.logaa.quartz.JobPersistenceSupport;
import com.logaa.quartz.SchedulerService;
import com.logaa.quartz.SchedulerStartupRegister;

public class ProjectStartListener implements ApplicationListener<ContextRefreshedEvent>{

	final static String JOB_GROUP = "group1";
	
	@Resource
	SchedulerStartupRegister schedulerStartupRegister;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() != null) return;
		schedulerStartupRegister.registerJobs(JOB_GROUP);
	}
}
