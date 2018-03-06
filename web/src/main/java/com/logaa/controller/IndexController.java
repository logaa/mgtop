package com.logaa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@RequestMapping(value = "/demo")
	public String index(){
		return "demo";
	}
	
	@RequestMapping(value = "/demo1")
	public String index1(){
		return "demo1";
	}
}
