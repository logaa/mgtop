package com.logaa.job.crypto;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.logaa.helper.SpringHelper;
import com.logaa.quartz.BaseJob;
import com.logaa.service.crypto.CryptoCompareService;

@Component
public class GetCryptoCompareMarketJob extends BaseJob{

	@Override
	public void executeJob(JobExecutionContext context) throws JobExecutionException {
		CryptoCompareService cryptoCompareService = SpringHelper.getBean(CryptoCompareService.class);
		cryptoCompareService.updateCryptoCompareMarket();
	}

}
