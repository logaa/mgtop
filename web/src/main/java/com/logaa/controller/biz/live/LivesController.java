package com.logaa.controller.biz.live;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logaa.service.live.LivesService;

@RestController
@RequestMapping("/live")
public class LivesController {

	@Autowired
	LivesService livesService;
	
	@GetMapping(value = "find")
	public String find(){
		return "";
	}
}
