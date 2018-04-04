package com.logaa.impl.live;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.logaa.dao.MongoDao;
import com.logaa.domain.mongo.Lives;
import com.logaa.service.live.LivesService;

@Service
public class LivesServiceImpl implements LivesService {

	@Autowired MongoDao mongoDao;
	
	@Override
	public List<Lives> find(int page, int size) {
		return mongoDao.find(page, size, Direction.DESC, "date", Lives.class);
	}

}
