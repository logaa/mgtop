package com.logaa.controller.biz.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logaa.domain.mongo.News;
import com.logaa.service.news.NewsService;
import com.logaa.view.BaseResponse;

@RestController
@RequestMapping("/news")
public class NewsController {

	@Autowired
	NewsService newsService;
	
	@GetMapping(value = "/find/{page}/{size}")
	public BaseResponse<List<News>> find(@PathVariable(name = "page")int page, @PathVariable(name = "size")int size){
		List<News> news = newsService.find(page, size);
		return new BaseResponse<List<News>>(news);
	}
	
}
