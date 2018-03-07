package com.logaa.quartz.impl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.Trigger.TriggerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.logaa.helper.SpringHelper;
import com.logaa.quartz.BaseJob;
import com.logaa.quartz.JobPersistenceSupport;
import com.logaa.quartz.SchedulerService;
import com.logaa.quartz.entity.IJobDefPo;
import com.logaa.quartz.enumer.JobEnum;
import com.logaa.util.cron.CronUtils;

@Service
public class SchedulerServiceImpl implements SchedulerService, InitializingBean {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	private final static String LOCK = "lock";
	
	@Resource
	Scheduler scheduler;
    
	@Resource
	JobPersistenceSupport jobPersistenceSupport;	
	
    @Override
	public void afterPropertiesSet() throws Exception {
    	if(!scheduler.isStarted()) scheduler.start();
	}

	public boolean startJob(String jobDefId) {
		boolean result = false;
		if(jobPersistenceSupport != null) result = startJob(jobPersistenceSupport.getJobDefPo(jobDefId));
		return result;
	}
	
	public boolean startJob(IJobDefPo jobDefPo) {
		boolean isStart = false;
		if(jobDefPo != null && !StringUtils.isEmpty(jobDefPo.getBean()) && CronUtils.check(jobDefPo.getExpr())){
			synchronized (StringUtils.isEmpty(jobDefPo.getId()) ? LOCK : jobDefPo.getId()) {							
				logger.info("Enter start Job method，name=" + jobDefPo.getName());
				isStart = startPlan(jobDefPo.getId(), jobDefPo.getBean(), jobDefPo.getGroup());
				if(!isStart){
					JobDetail jobDetail = getJobDetail(getKey(jobDefPo), jobDefPo.getGroup());
					BaseJob jobBean = (BaseJob) SpringHelper.getBean(jobDefPo.getBean());	
					if(jobBean != null){
						jobDetail = newJob(jobBean.getClass())
								.withIdentity(getKey(jobDefPo), jobDefPo.getGroup()).storeDurably(false).build();
					}
					CronTrigger	trigger = buildCronTrigger(getKey(jobDefPo), jobDefPo.getGroup(), jobDefPo.getExpr());
					try {
						isStart = startPlan(jobDefPo.getId(),jobDefPo.getBean(),jobDefPo.getGroup());
						if(!isStart){				
							deleteJob(getKey(jobDefPo), jobDefPo.getGroup());
							scheduler.scheduleJob(jobDetail,trigger);
							logger.info("Start Job Success:"+jobDefPo.getName());
						}
						return true;
					} catch (SchedulerException e) {			
						e.printStackTrace();
						logger.error("Start Job Error:" + jobDefPo.getName() + " " + e.getMessage());
						return false;
					}
				}else {
					if(!CronUtils.check(jobDefPo.getExpr())){
						logger.info("Job Expr Error! :" + jobDefPo.getExpr());
					}else {
						logger.info("Job has run:" + jobDefPo.getName());	
					}				
				}
			}
		}
		return isStart;
	}

	@Override
	public boolean startJob(String bean, String group, String expr) {
		logger.info("startJob bean=" + bean + ";group=" + group + ";expr=" + expr);
		if(!CronUtils.check(expr)){
			logger.warn("expr error!!! : " + expr);
			return false;
		}
		BaseJob jobBean = (BaseJob) SpringHelper.getBean(bean);
		JobDetail job = getJobDetail(bean, group);
		if(job==null){
			job = newJob(jobBean.getClass()).withIdentity(bean + "_" + UUID.randomUUID(), group).storeDurably(false).build();
		}		
		CronTrigger	trigger = buildCronTrigger(UUID.randomUUID().toString(),group,expr);
		try {
			scheduler.scheduleJob(job,trigger);
			return true;
		} catch (SchedulerException e) {			
			e.printStackTrace();
			logger.error("startJob(String bean, String group, String expr,List<IJobParamPo> jobParamPos) error" + e.getMessage(),e);
			return false;
		}		
	}

	@Override
	public boolean startOneTime(Long jobDefId, String beanId, String group) {
		logger.info("Enter startOneTime method，beanId=" + beanId + "; group=" + group);
		JobDetail job = getJobDetail(beanId + "_" + UUID.randomUUID(), group);
		boolean jobExist = true;
		if(job == null){
			jobExist = false;
			job = buildJobDetail(jobDefId, beanId, group, false);
		}
		try {		
			SimpleTrigger trigger = buildSimpleTrigger(job, null);
			if(jobExist){
				scheduler.scheduleJob(trigger);
			}else {
				scheduler.scheduleJob(job, trigger);	
			}	
			logger.info("startOneTime success!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("startOneTime error!" + e.getMessage(), e);
			return false;
		}		
	}
	
	@Override
	public boolean shutdownPlan(Long planId, String bean, String group) {
		TriggerKey triggerKey = new TriggerKey(getKey(bean,planId), group);
		try {
			boolean isStop = false;
			Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
			if(state == TriggerState.NORMAL 
					|| state == TriggerState.BLOCKED){
				scheduler.pauseTrigger(triggerKey);
				isStop = true;
			}	
			if(!isStop) deleteJob(getKey(bean, planId), group);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	private void deleteJob(String key, String group){
		JobKey jobKey = new JobKey(key, group);
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public boolean startPlan(Long planId, String bean, String group){
		TriggerKey triggerKey = new TriggerKey(getKey(bean,planId),group);
		try {
			Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
			if(state != null){
				if(state == TriggerState.PAUSED || state == TriggerState.BLOCKED){
					scheduler.resumeTrigger(triggerKey);
					return true;
				}else if(state == TriggerState.NONE || state == TriggerState.COMPLETE || state == TriggerState.ERROR){
					JobKey jobKey = new JobKey(getKey(bean, planId), group);
					scheduler.deleteJob(jobKey);					
				}
			}		
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;		
	}

	@Override
	public TriggerState getTriggerState(Long planId,String bean, String group) {
		TriggerKey triggerKey = new TriggerKey(getKey(bean,planId),group);
		try {
			return scheduler.getTriggerState(triggerKey);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void startScheduler() {
		try {
			init();
			if(!scheduler.isStarted()) scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void shutdownScheduler() {
		try {
			init();
			if(!scheduler.isShutdown()){
				scheduler.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void init(){
		if(scheduler == null ){
			scheduler = (Scheduler) SpringHelper.getBean("scheduler");
		}
		if(jobPersistenceSupport == null){
			jobPersistenceSupport = SpringHelper.getBean(JobPersistenceSupport.class);
		}
		if(scheduler == null){
			throw new RuntimeException("Can't get scheduler instance, please check service-quartz.xml file.");
		}
	}
	
	private JobDetail getJobDetail(String beanId,String group){
		JobKey jobKey = new JobKey(beanId, group);
		try {
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			if(jobDetail != null){
				return jobDetail;
			}
		} catch (SchedulerException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	private SimpleTrigger buildSimpleTrigger(JobDetail job,Date date){
		if(date == null) date = new Date();
		return (SimpleTrigger) newTrigger().forJob(job) 
                .startAt(date).withSchedule(simpleSchedule().withIntervalInSeconds(1) 
                        .withRepeatCount(0)).build();
	}	
	
	
	private CronTrigger buildCronTrigger(String planId,String group,String expr){
		CronTrigger	trigger = TriggerBuilder.newTrigger().withIdentity(planId, group)  
                .withSchedule(CronScheduleBuilder.cronSchedule(expr)).build();
		return trigger;
	}
	
	private JobDetail buildJobDetail(Long jobDefId, String beanId, String group, boolean isDurable){
		BaseJob jobBean = (BaseJob) SpringHelper.getBean(beanId);
		JobDetail jobDetail = newJob(jobBean.getClass()).withIdentity(getKey(beanId, jobDefId), group).storeDurably(isDurable).build();
		return jobDetail;
	}
	
	private void prepare(JobDetail jobDetail,IJobDefPo jobDefPo){
		jobDetail.getJobDataMap().put(JobEnum.EXECUTE_TASK_NAME.getValue(), jobDefPo.getName());
		jobDetail.getJobDataMap().put(JobEnum.EXECUTE_METHOD_NAME.getValue(), jobDefPo.getMethod());
		jobDetail.getJobDataMap().put(JobEnum.EXECUTE_CLASS_NAME.getValue(), jobDefPo.getBean());
	}	
	
	private String getKey(IJobDefPo jobDefPo){
		return jobDefPo.getBean() + "_" + jobDefPo.getId();
	}
	private String getKey(String bean,Long jobDefId){
		return bean + "_" + jobDefId;
	}

}
