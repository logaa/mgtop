package com.logaa.quartz;


import org.quartz.Trigger.TriggerState;

import com.logaa.quartz.entity.IJobDefPo;

public interface SchedulerService {

	public boolean startJob(String jobDefId);
	
	public boolean startJob(IJobDefPo jobDefPo);
	
	public boolean startJob(String beanId, String group, String expr);
	
	/**
	 * 启动任务一次。
	 */
	public boolean startOneTime(Long jobDefId,String beanId,String group);
	
	/**
	 * 停止计划
	 */
	public boolean shutdownPlan(Long jobDefId,String bean,String group);
		
	public boolean startPlan(Long planId,String bean, String group);
	
	/**
	 * 返回计划的执行状态
	 */
	public TriggerState getTriggerState(Long planId, String bean, String group); 
	
	/**
	 * 启动Scheduler
	 */
	public void startScheduler();
	
	/**
	 * 关闭Scheduler
	 */
	public void shutdownScheduler();
	
}
