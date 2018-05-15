package com.logaa.impl.crypto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.logaa.dao.MongoDao;
import com.logaa.domain.constanst.CacheKey;
import com.logaa.domain.enumer.Symbol;
import com.logaa.domain.enumer.cryptocompare.Interval;
import com.logaa.domain.enumer.cryptocompare.Period;
import com.logaa.domain.mongo.Market;
import com.logaa.domain.po.cryptocompare.CoinListPo;
import com.logaa.domain.po.cryptocompare.CryptoModel;
import com.logaa.domain.rdb.crypto.CoinChangeRank;
import com.logaa.domain.rdb.crypto.CoinList;
import com.logaa.helper.CryptoCompareHelper;
import com.logaa.repository.rdb.crypto.CoinChangeRankRepository;
import com.logaa.repository.rdb.crypto.CoinListRepository;
import com.logaa.service.crypto.CryptoCompareService;
import com.logaa.util.date.TimestampUtils;
import com.logaa.util.math.BigDecimalUtils;

@Service
public class CryptoCompareServiceImpl implements CryptoCompareService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	enum Constant{
		time, id, change
	}

	@Autowired MongoDao mongoDao;
	@Autowired CoinListRepository coinListRepository;
	@Autowired CryptoCompareHelper cryptoCompareHelper;
	@Autowired CoinChangeRankRepository coinChangeRankRepository;
	
	@Override
	public void updateCryptoCompareMarket() {
		List<CoinList> coins = coinListRepository.findAll();
		if(coins == null) return;
		saveCryptoCompareMarket(coins);
	}

	private void saveCryptoCompareMarket(List<CoinList> coins) {
		coins.forEach(e -> {
			String collection = CacheKey.CRYPTOCOMPARE_MARKET + e.getName();
			boolean collectionExists = mongoDao.collectionExists(collection);
			List<CryptoModel> cryptoModel = null;
			if(!collectionExists){
				cryptoModel = cryptoCompareHelper.getCryptoMarketHistodayAll(e.getName(), Symbol.USD.name());
			}else {
				cryptoModel = cryptoCompareHelper.getCryptoMarket(e.getName(), Symbol.USD.name(), Interval.D.getKey(), Period.W1.name());
				if(cryptoModel != null) cryptoModel.forEach(x -> mongoDao.remove(Constant.id.name(), x.getTime(), collection));
			}
			if(cryptoModel != null){
				cryptoModel.forEach(x -> {
					Market market = new Market();
					BeanUtils.copyProperties(x, market);
					market.setId(x.getTime());
					double change = BigDecimalUtils.div(market.getClose().doubleValue() - market.getOpen().doubleValue(),
								market.getOpen().doubleValue()) * 100;
					market.setChange(change);
					mongoDao.save(market, collection);
				});
			}
		});
	}

	@Override
	public List<CoinListPo> find(int page, int size) {
		List<CoinListPo> pos = new ArrayList<>();
		Page<CoinList> coins = coinListRepository.findAll(new PageRequest(page, size));
		coins.getContent().forEach(e -> {
			CoinListPo po = new CoinListPo();
			String collection = CacheKey.CRYPTOCOMPARE_MARKET + e.getName();
			Market market = mongoDao.findOneOrderBy(Direction.DESC, Constant.id.name(), Market.class, collection);
			if(market != null) BeanUtils.copyProperties(market, po);
			po.setName(e.getName());
			po.setFullName(e.getFullName());
			po.setImageUrl(e.getImageUrl());
			List<Market> markets = mongoDao.find(0, 7, Direction.DESC, Constant.id.name(), Market.class, collection);
			Map<String, String> trend = new HashMap<String, String>();
			if(markets != null) markets.forEach(x -> trend.put(x.getId().toString(), x.getClose().toString()));
			po.setTrend(trend);
			pos.add(po);
		});
		return pos;
	}

	@Override
	public List<CoinListPo> rank(String direction, int top) {
		List<CoinListPo> pos = new ArrayList<>();
		Long date = coinChangeRankRepository.findTopDate();
		List<CoinChangeRank> coinChangeRanks = null;
		if(Direction.fromString(direction).isAscending()){
			coinChangeRanks = coinChangeRankRepository.findTopByDateOrderByRankAsc(date, top);
		}else{
			coinChangeRanks = coinChangeRankRepository.findTopByDateOrderByRankDesc(date, top);
		}
		if(coinChangeRanks != null && !coinChangeRanks.isEmpty()){
			coinChangeRanks.forEach(e -> {
				CoinListPo po = new CoinListPo();
				CoinList coin = coinListRepository.findByName(e.getName());
				BeanUtils.copyProperties(coin, po);
				po.setClose(e.getPrice());
				po.setChange(e.getChange());
				pos.add(po);
			});
		}
		return pos;
	}

	@Override
	public void updateChangeRank(Date date) {
		List<CoinList> coins = coinListRepository.findAll();
		if(coins == null) return;
		List<Market> markets = new ArrayList<>();
		coins.forEach(e -> {
			String collection = CacheKey.CRYPTOCOMPARE_MARKET + e.getName();
			Long timestamp = TimestampUtils.getTimestamp(date, 8, 0 ,0);
			Long id = Long.valueOf(timestamp.toString().substring(0, 10));
			Market market = mongoDao.findOne(Constant.id.name(), id, Market.class, collection);
			if(market != null){
				market.setName(e.getName());
				markets.add(market);
			} 
		});
		AtomicInteger rank = new AtomicInteger(0);
		markets.stream().sorted((x, y) -> y.getChange().compareTo(x.getChange())).forEachOrdered(e -> {
			CoinChangeRank coinChangeRank = new CoinChangeRank();
			coinChangeRank.setChange(e.getChange());
			coinChangeRank.setDate(e.getId());
			coinChangeRank.setName(e.getName());
			coinChangeRank.setRank(rank.incrementAndGet());
			coinChangeRank.setPrice(e.getClose());
			coinChangeRankRepository.save(coinChangeRank);
		});
	}

	@Override
	public List<Integer> updownWeight() {
		List<Integer> updown = new ArrayList<>();
		Long date = coinChangeRankRepository.findTopDate();
		List<CoinChangeRank> coinChangeRanks = coinChangeRankRepository.findByDate(date);
		if(coinChangeRanks != null && !coinChangeRanks.isEmpty()){
			AtomicInteger up = new AtomicInteger(0);
			AtomicInteger down = new AtomicInteger(0);
			coinChangeRanks.forEach(e -> {
				if(e.getChange().doubleValue() > 0){
					up.incrementAndGet();
				}else if(e.getChange().doubleValue() < 0){
					down.incrementAndGet();
				}
			});
			updown = Arrays.asList(up.get(), down.get());
		}
		return updown;
	}
	
}
