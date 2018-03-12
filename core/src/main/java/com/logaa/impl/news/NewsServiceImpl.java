package com.logaa.impl.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.logaa.dao.MongoDao;
import com.logaa.domain.mongo.News;
import com.logaa.service.news.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	MongoDao mongoDao;
	
	@Override
	public List<News> find(int page, int size) {
		List<News> news = mongoDao.find(page, size, Direction.DESC, "date", News.class);
		if(!news.isEmpty()){
			news.forEach(e -> {
				String content = e.getContent();
				e.setContent(content.substring(0, content.indexOf("</p>")));
			});
		}
		return news;
	}

}
