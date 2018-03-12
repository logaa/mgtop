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
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@GetMapping(value = "/find/{page}/{size}")
	public BaseResponse<List<News>> find(@PathVariable(name = "page")int page, @PathVariable(name = "size")int size){
		List<News> news = newsService.find(page, size);
		return new BaseResponse<List<News>>(news);
	}
	
	/*@GetMapping(value = "/update")
	public String update(){
		List<News> news = mongoTemplate.findAll(News.class);
		if(!news.isEmpty()){
			news.forEach(e -> {
				String content = e.getContent();
				if(content.indexOf("<img") > -1){
					int startIndex = content.indexOf("<img");
					int endIndex = content.substring(startIndex, content.length()).indexOf(">");
					String img = content.substring(startIndex, startIndex + endIndex + 1);
					
					int si = img.indexOf("http");
					int ei = img.substring(si, img.length()).indexOf("\"");
					String src = img.substring(si, si + ei);
					
					e.setImg(src);
				}else{
					e.setImg("http://cdn.8btc.com/wp-content/uploads/2018/03/201803080304041603.jpg");
				}
				mongoTemplate.save(e);
			});
		}
		return null;
	}*/
}
