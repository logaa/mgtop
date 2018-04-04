package com.logaa.job.crypto;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.logaa.domain.rdb.crypto.CoinList;
import com.logaa.helper.CryptoCompareHelper;
import com.logaa.helper.SpringHelper;
import com.logaa.quartz.BaseJob;
import com.logaa.repository.rdb.CoinListRepository;

@Component
public class GetCryptoCompareCoinsJob extends BaseJob{

	@Override
	public void executeJob(JobExecutionContext context) throws JobExecutionException {
		CoinListRepository coinListRepository = SpringHelper.getBean(CoinListRepository.class);
		/*CryptoCompareHelper cryptoCompareHelper = SpringHelper.getBean(CryptoCompareHelper.class);
		List<CoinList> coins = new ArrayList<>();
		cryptoCompareHelper.getCoinList().forEach(e -> {
			CoinList coin = coinListRepository.findByName(e.getName());
			if(coin == null) coin = new CoinList();
			BeanUtils.copyProperties(e, coin);
			coins.add(coin);
		});
		coinListRepository.save(coins);*/
	}

}
