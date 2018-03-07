package com.logaa.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.logaa.helper.SpringHelper;

@Configuration
public class SchedledConfig implements ApplicationContextAware {
	
	@Bean(name = "scheduler")
	public Object getScheduler() {
		return new SchedulerFactoryBean();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringHelper.setContext(applicationContext);
	}

}
