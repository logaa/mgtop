package com.logaa.job.live;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.logaa.helper.SpringHelper;
import com.logaa.quartz.BaseJob;
import com.logaa.service.live.JinseLivesService;

@Component
public class JinseLivesCrawlJob extends BaseJob {
	
	@Override
	public void executeJob(JobExecutionContext context) throws JobExecutionException{
		JinseLivesService jinseLivesService = SpringHelper.getBean(JinseLivesService.class);
		jinseLivesService.crawlLives(null, null);
	}

}
