package com.logaa.quartz;

public interface SchedulerStartupRegister {

	/**
	 * 将该应用下面的所有任务加到容器里，根据状态进行处理
	 */
	public void registerJobs(String group);
}
