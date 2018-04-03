package com.logaa.job.live;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.logaa.helper.SpringHelper;
import com.logaa.quartz.BaseJob;
import com.logaa.service.news.BbtcNewsService;

@Component
public class BbtcNewsCrawlJob extends BaseJob{

	@Override
	public void executeJob(JobExecutionContext context) throws JobExecutionException {
		BbtcNewsService bbtcNewsService = SpringHelper.getBean(BbtcNewsService.class);
		bbtcNewsService.bbtcNewsCrawl();
	}

}
