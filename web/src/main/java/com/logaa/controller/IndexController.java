package com.logaa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logaa.domain.rdb.JobDef;
import com.logaa.repository.rdb.JobDefRepository;
import com.logaa.service.live.JinseLivesService;

@RestController
public class IndexController {
	
	@Autowired
	JinseLivesService jinseLivesService;
	@Autowired
	JobDefRepository jobDefRepository;

	@RequestMapping(value = "/demo")
	public String index(){
		List<JobDef> jobs = jobDefRepository.findByGroupAndStatus("group1", "run");
		jobs.forEach(e -> System.out.println(e.getName()));
		return "demo";
	}
	
	@RequestMapping(value = "/jinseLives")
	public String jinseLives(){
		jinseLivesService.crawlLives(null, null);
		return "demo1";
	}
}
