package com.logaa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
	
	@GetMapping(value = "/news")
	public String find(){
		return "index/index_v0";
	}
}
