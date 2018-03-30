package com.logaa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
	
	@GetMapping(value = "/")
	public String defaultIndex(){
		return "index/index_v0";
	}
	
	@GetMapping(value = "/index")
	public String index(){
		return "index/index_v0";
	}
}
