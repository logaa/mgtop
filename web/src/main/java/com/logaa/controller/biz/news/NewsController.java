package com.logaa.controller.biz.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.logaa.dao.MongoDao;
import com.logaa.domain.mongo.News;
import com.logaa.service.news.NewsService;
import com.logaa.view.BaseResponse;

@Controller
@RequestMapping("/news")
public class NewsController {

	@Autowired
	NewsService newsService;
	@Autowired
	MongoDao mongoDao;
	
	@GetMapping(value = "/find/{page}/{size}")
	@ResponseBody
	public BaseResponse<List<News>> find(@PathVariable(name = "page")int page, @PathVariable(name = "size")int size){
		List<News> news = newsService.find(page, size);
		return new BaseResponse<List<News>>(news);
	}
	
	@GetMapping(value = "/{id}")
	public String findOne(@PathVariable(name = "id")String id, ModelMap model){
		model.put("result", mongoDao.findOne("id", id, News.class));
		return "news/news";
	}
}
