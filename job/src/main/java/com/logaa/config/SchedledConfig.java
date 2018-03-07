package com.logaa.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.logaa.helper.SpringHelper;
import com.logaa.quartz.JobPersistenceSupport;
import com.logaa.quartz.SchedulerService;
import com.logaa.quartz.SchedulerStartupRegister;
import com.logaa.quartz.impl.JobPersistenceSupportImpl;
import com.logaa.quartz.impl.SchedulerServiceImpl;
import com.logaa.quartz.impl.SchedulerStartupRegisterImpl;

@Configuration
public class SchedledConfig implements ApplicationContextAware {

	@Bean(name = "scheduler")
	public Object getScheduler() {
		return new SchedulerFactoryBean();
	}
	
	@Bean(name = "schedulerService")
	public SchedulerService getSchedulerService() {
		return new SchedulerServiceImpl();
	}

	@Bean(name = "schedulerStartupRegister")
	public SchedulerStartupRegister getSchedulerStartupRegister() {
		return new SchedulerStartupRegisterImpl();
	}

	@Bean(name = "jobPersistenceSupport")
	public JobPersistenceSupport getJobPersistenceSupport() {
		return new JobPersistenceSupportImpl();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringHelper.setContext(applicationContext);
	}

}
