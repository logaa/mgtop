package com.logaa.impl.live;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.logaa.dao.MongoDao;
import com.logaa.domain.enumer.FromEnum;
import com.logaa.domain.mongo.Lives;
import com.logaa.http.ApiRequest;
import com.logaa.service.live.JinseLivesService;
import com.logaa.util.date.TimestampUtils;

@Service
public class JinseLivesServiceImpl implements JinseLivesService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	final static String JINSE_LIVES_URL = "http://www.jinse.com/ajax/lives/getList?id=%s&flag=%s";
	
	@Autowired
	MongoDao mongoDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void crawlLives(Integer topId, Integer currId) {
		if(topId == null){
			ApiRequest api = new ApiRequest(String.format(JINSE_LIVES_URL, 0, "down"), ApiRequest.Verb.GET);
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) api.send().getResult(LinkedHashMap.class).get("data");
			List<LinkedHashMap<String, Object>> jinseLivesModels = (List<LinkedHashMap<String, Object>>) 
					map.get(map.keySet().stream().sorted((x, y) -> y.compareTo(x)).collect(Collectors.toList()).get(0));
			topId = Integer.valueOf(jinseLivesModels.get(0).get("id").toString());
		}
		if(currId == null){
			Lives live = mongoDao.findOne("from", FromEnum.JINSE.getKey(), Direction.DESC, "id", Lives.class);
			if(live != null) currId = Integer.valueOf(live.getId().replaceFirst(FromEnum.JINSE.getKey(), ""));
		}
		if(currId == null || currId < 172) currId = 172;
		saveJinseLives(topId + 1, currId);
		logger.info("{} 数据抓取结束，本次数据抓取量：{}", FromEnum.JINSE.getValue(), topId - currId);
	}

	@SuppressWarnings("unchecked")
	private void saveJinseLives(int topId, int min) {
		try {
			while (topId > min) {
				long cTime = TimestampUtils.getTimestamp();
				ApiRequest api = new ApiRequest(String.format(JINSE_LIVES_URL, topId, "down"), ApiRequest.Verb.GET);
				LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) api.send().getResult(LinkedHashMap.class).get("data");
				map.keySet().forEach(e -> {
					List<LinkedHashMap<String, Object>> jinseLivesModels = (List<LinkedHashMap<String, Object>>) map.get(e);
					jinseLivesModels.forEach(x -> {
						Lives live = new Lives(FromEnum.JINSE.getKey() + x.get("id").toString(), null, null, null, 
								FromEnum.JINSE.getKey(), x.get("link").toString(),
								x.get("link_name").toString(), cTime);
						String content = x.get("content") == null ? "" : x.get("content").toString();
						int index = content.indexOf("】") + 1;
						live.setTitle(content.substring(0, index).replace("【", "").replace("】", ""));
						live.setContent(content.substring(index));
						String time = x.get("created_at") == null ? null : x.get("created_at").toString();
						live.setDate(String.format("%s %s", e, time));
						mongoDao.save(live);
						logger.info(new Gson().toJson(live));
					});
				});
				topId -= 10;
				Thread.sleep(5_000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
