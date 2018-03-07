package com.logaa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logaa.service.live.JinseLivesService;

@RestController
public class IndexController {
	
	@Autowired
	JinseLivesService jinseLivesService;

	@RequestMapping(value = "/demo")
	public String index(){
		return "demo";
	}
	
	@RequestMapping(value = "/jinseLives")
	public String jinseLives(){
		jinseLivesService.crawlLives(null, null);
		return "demo1";
	}
}
