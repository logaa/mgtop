package com.logaa.service.news;

import java.util.List;

import com.logaa.domain.mongo.News;

public interface NewsService {

	List<News> find(int page, int size);

}
