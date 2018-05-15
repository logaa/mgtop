package com.logaa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.logaa.service.gitee.GiteeService;


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
	
	@Autowired GiteeService giteeService;
	
	@GetMapping(value = "/gitee")
	@ResponseBody
	public String gitee(){
		giteeService.getProject();
		return "gitee";
	}
}
