package com.logaa.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.logaa.helper.SpringHelper;
import com.logaa.quartz.enumer.JobEnum;
import com.logaa.util.date.DateUtils;
import com.logaa.util.exception.ExceptionUtils;

@Component
public abstract class BaseJob implements Job {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	final static String TRIGGER_NAME = "defaultDirectExec";
	
	public abstract void executeJob(JobExecutionContext context) throws JobExecutionException;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobPersistenceSupport jobPersistenceSupport = SpringHelper.getBean(JobPersistenceSupport.class);
		String jobName = context.getJobDetail().getKey().getName();	
		Long jobDefId = Long.parseLong(jobName.split("_")[1]);
		String triggerName = getTriggerName(context);
		Date now = Calendar.getInstance().getTime();
		String nowString = DateUtils.date2String(now);
		long ltime = now.getTime();
		try {
			StringBuffer log = new StringBuffer("开始执行任务，任务名：" + jobName + "；触发器名：" + triggerName + "，开始时间 " + nowString);  
			logger.warn("开始执行任务，任务名：{}；触发器名：{}，开始时间 {}", new Object[]{jobName, triggerName, nowString});			
			executeJob(context);
			long ms = Calendar.getInstance().getTimeInMillis() - ltime;
			logger.warn("结束执行任务，执行时间是：{} 毫秒", new Object[]{String.valueOf(ms)});
			log.append("结束执行任务，执行时间是：" + ms + " 毫秒");
			jobPersistenceSupport.saveRunHistory(context.getJobDetail().getKey().getName(),
					context.getJobDetail().getKey().getGroup(),
					JobEnum.LOG_STATUS_SUCCESS.getValue(), log.toString());
			jobPersistenceSupport.updateLastRunTime(jobDefId);
		} catch (Exception e) {
			e.printStackTrace();
			jobPersistenceSupport.saveRunHistory(context.getJobDetail().getKey().getName(),
					context.getJobDetail().getKey().getGroup(),
					JobEnum.LOG_STATUS_FAILURE.getValue(), ExceptionUtils.getExceptionMessage(e));
			logger.error("执行任务出错，{}", new Object[]{e.getMessage()}, e);			
		}
	}
	
	protected JobDataMap getJobDataMap(JobExecutionContext context) {
		return context.getJobDetail().getJobDataMap();
	}
	
	private String getTriggerName(JobExecutionContext context){
		String triggerName = TRIGGER_NAME;
		Trigger trigger = context.getTrigger();
		if(trigger != null)triggerName = trigger.getKey().getName();
		return triggerName;
	}
}
