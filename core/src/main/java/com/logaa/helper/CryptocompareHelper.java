package com.logaa.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.logaa.domain.enumer.cryptocompare.Interval;
import com.logaa.domain.enumer.cryptocompare.Period;
import com.logaa.domain.po.cryptocompare.CoinListModel;
import com.logaa.domain.po.cryptocompare.CoinListResult;
import com.logaa.domain.po.cryptocompare.CryptoModel;
import com.logaa.domain.po.cryptocompare.CryptoResult;
import com.logaa.http.ApiRequest;
import com.logaa.http.ApiRequest.Verb;


@Component
public class CryptoCompareHelper {

Logger logger = LoggerFactory.getLogger(getClass());
	
	static final String BASE_V1_URL = "https://www.cryptocompare.com/api/data/";
	static final String BASE_V2_URL = "https://min-api.cryptocompare.com/data/";
	
	/**
	 * 获取全部货币信息
	 * @return
	 */
	public List<CoinListModel> getCoinList(){
		List<CoinListModel> coins = new ArrayList<>();
		String url = BASE_V1_URL + Constant.coinlist.name();
		logger.info("CryptoCompareHelper URL --> {}", url);
		String result = new ApiRequest(url, Verb.GET).send().getResult();
		logger.info("CryptoCompareHelper RESULT --> {}", result);
		CoinListResult clr = new Gson().fromJson(result, CoinListResult.class);
		if(clr != null && clr.getType().intValue() == 100){
			clr.getData().forEach((k, v) -> {
				if(StringUtils.isNotBlank(v.getUrl())){
					v.setUrl(clr.getBaseLinkUrl() + v.getUrl());
				}
				if(StringUtils.isNotBlank(v.getImageUrl())){
					v.setImageUrl(clr.getBaseImageUrl() + v.getImageUrl());
				}
				coins.add(v);
			});
		}
		return coins;
	}
	
	/**
	 * 获取指定货币行情
	 * @param fsym 指定货币
	 * @param tsym 转换货币
	 * @param interval 间隔
	 * @param period 周期
	 * @return
	 */
	public List<CryptoModel> getCryptoMarket(String fsym, String tsym, String interval, String period){
		List<CryptoModel> views = null;
		String url = getCryptoMarketUrl(fsym, tsym, interval, period);
		logger.info("CryptoCompareHelper URL --> {}", url);
		String result = new ApiRequest(url, Verb.GET).send().getResult();
		logger.info("CryptoCompareHelper RESULT --> {}", result);
		CryptoResult cryptoResult = new Gson().fromJson(result, CryptoResult.class);
		if(cryptoResult != null && cryptoResult.getType().intValue() == 100){
			views = cryptoResult.getData();
		}
		return views;
	}
	
	/**
	 * 获取指定货币历史行情（天）
	 * @param fsym 指定货币
	 * @param tsym 转换货币
	 * @param interval 间隔
	 * @param period 周期
	 * @return
	 */
	public List<CryptoModel> getCryptoMarketHistodayAll(String fsym, String tsym){
		List<CryptoModel> views = null;
		String url = String.format(BASE_V2_URL + "histoday?fsym=%s&tsym=%s&allData=true&e=CCCAGG", fsym, tsym);
		logger.info("CryptoCompareHelper URL --> {}", url);
		String result = new ApiRequest(url, Verb.GET).send().getResult();
		logger.info("CryptoCompareHelper RESULT --> {}", result);
		CryptoResult cryptoResult = new Gson().fromJson(result, CryptoResult.class);
		if(cryptoResult != null && cryptoResult.getType().intValue() == 100){
			views = cryptoResult.getData();
		}
		return views;
	}
	
	private String getCryptoMarketUrl(String fsym, String tsym, String interval, String period){
		return String.format(BASE_V2_URL + "%s?fsym=%s&tsym=%s&limit=%s&e=CCCAGG", Interval.getValue(interval), fsym, tsym, Period.getDay(period));
	}
	
	enum Constant{
		coinlist
	}
}
