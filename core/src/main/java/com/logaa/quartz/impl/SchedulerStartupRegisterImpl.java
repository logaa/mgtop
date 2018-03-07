package com.logaa.quartz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.logaa.quartz.JobPersistenceSupport;
import com.logaa.quartz.SchedulerService;
import com.logaa.quartz.SchedulerStartupRegister;
import com.logaa.quartz.entity.IJobDefPo;
import com.logaa.util.cron.CronUtils;

@Service
public class SchedulerStartupRegisterImpl implements SchedulerStartupRegister {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@Resource
	JobPersistenceSupport jobPersistenceSupport;
	@Resource
	SchedulerService schedulerService;
	
	@Override
	public void registerJobs(String group) {
		logger.info("Enter SchedulerStartupRegisterImpl, register all jobs");
		int registerJobCount = 0;
		List<IJobDefPo> jobDefPos = jobPersistenceSupport.findActivedJobDefPos(group);
		if(jobDefPos != null){
			for(IJobDefPo jobDefPo : jobDefPos){
				if(!StringUtils.isEmpty(jobDefPo.getExpr())){
					if(CronUtils.check(jobDefPo.getExpr())){
						schedulerService.startJob(jobDefPo);
						logger.info("register "+ jobDefPo.getName());
					}else {
						logger.warn("error expr + " + jobDefPo.getExpr());
					}
				}
				registerJobCount++;
			}
		}
		logger.info("Complete SchedulerStartupRegisterImpl, register: " + registerJobCount + " Jobs");
	}
}
