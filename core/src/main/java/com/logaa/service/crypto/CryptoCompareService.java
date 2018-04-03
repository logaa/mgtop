package com.logaa.service.crypto;

import java.util.Date;
import java.util.List;

import com.logaa.domain.po.cryptocompare.CoinListPo;

public interface CryptoCompareService {

	void updateCryptoCompareMarket();
	
	void updateChangeRank(Date date);

	List<CoinListPo> find(int page, int size);

	List<CoinListPo> rank(String direction, int top);

	List<Integer> updownWeight();

}
