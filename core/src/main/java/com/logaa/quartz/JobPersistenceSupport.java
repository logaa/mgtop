package com.logaa.quartz;

import java.util.List;

import com.logaa.quartz.entity.IJobDefPo;

public interface JobPersistenceSupport {
	/**
	 * 更新该计划的最后执行时间。
	 * @param jobDefId
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateLastRunTime(Long jobDefId);

	
	/**
	 * 查询所有激活的任务，根据组值。
	 * @param group
	 * @return 
	 * List<IJobDefPo>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<IJobDefPo> findActivedJobDefPos(String group);
	
	public IJobDefPo getJobDefPo(String jobDefId);
	
	/**
	 * 保存执行历史到相应的数据表中
	 * @param jobId
	 * @param status
	 * @param log 
	 * @since  1.0.0
	 */
	public void saveRunHistory(String key,String group,String status,String log);

}
