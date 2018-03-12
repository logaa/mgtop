package com.logaa.controller.biz.live;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logaa.domain.mongo.Lives;
import com.logaa.service.live.LivesService;
import com.logaa.view.BaseResponse;

@RestController
@RequestMapping("/lives")
public class LivesController {

	@Autowired
	LivesService livesService;
	
	@GetMapping(value = "/find/{page}/{size}")
	public BaseResponse<List<Lives>> find(@PathVariable(name = "page")int page, @PathVariable(name = "size")int size){
		List<Lives> lives = livesService.find(page, size);
		return new BaseResponse<List<Lives>>(lives);
	}
}
