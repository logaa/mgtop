package com.logaa.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.logaa.helper.SpringHelper;
import com.logaa.quartz.BaseJob;
import com.logaa.service.live.BbtcLivesService;

@Component
public class BbtcLivesCrawlJob extends BaseJob{

	@Override
	public void executeJob(JobExecutionContext context) throws JobExecutionException {
		BbtcLivesService bbtcLivesService = SpringHelper.getBean(BbtcLivesService.class);
		bbtcLivesService.bbtcLivesCrawl();
	}

}
