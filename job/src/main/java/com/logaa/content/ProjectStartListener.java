package com.logaa.content;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

import com.logaa.quartz.JobPersistenceSupport;
import com.logaa.quartz.SchedulerService;
import com.logaa.quartz.SchedulerStartupRegister;

@Component
public class ProjectStartListener implements ServletContextListener{

	final static String JOB_GROUP = "group1";
	
	@Resource
	SchedulerService schedulerService;
	
	@Resource
	SchedulerStartupRegister schedulerStartupRegister;
	
	@Resource
	JobPersistenceSupport jobPersistenceSupport;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		schedulerStartupRegister.registerJobs(JOB_GROUP);
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}
}
