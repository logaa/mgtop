package com.logaa.rest.love2io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.logaa.domain.po.love2io.ArchivesView;
import com.logaa.domain.po.love2io.PostsView;
import com.logaa.domain.po.love2io.SummaryView;
import com.logaa.service.love2io.Love2ioService;
import com.logaa.view.BaseResponse;

@RestController
@RequestMapping("/love2io")
public class Love2ioRest {

	@Autowired Love2ioService love2ioService;
	
	@RequestMapping("/posts/crawl")
	public String postsCrawl(){
		love2ioService.postsCrawl();
		return "posts";
	}
	
	@RequestMapping("/summary/crawl")
	public String summaryCrawl(){
		love2ioService.summaryCrawl();
		return "summary";
	}
	
	@RequestMapping("/posts/find-all/{size}/{page}")
	public String findAll(@PathVariable(name = "page")int page, @PathVariable(name = "size")int size){
		PostsView view = love2ioService.findAll(page, size);
		return new Gson().toJson(new BaseResponse<>(view));
	}
	
	@RequestMapping("/summary/{postsId}")
	public String findOne(@PathVariable(name = "postsId")long postsId, String name){
		SummaryView view = love2ioService.findOne(postsId, name);
		return new Gson().toJson(new BaseResponse<>(view));
	}

	@RequestMapping("/archives")
	public String archives(){
		//ArchivesView view = love2ioService.archives();
		return "archives";
	}
}
