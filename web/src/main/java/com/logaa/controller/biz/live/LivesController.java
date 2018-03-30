package com.logaa.controller.biz.live;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.logaa.dao.MongoDao;
import com.logaa.domain.mongo.Lives;
import com.logaa.domain.mongo.News;
import com.logaa.service.live.LivesService;
import com.logaa.view.BaseResponse;

@Controller
@RequestMapping("/lives")
public class LivesController {

	@Autowired
	LivesService livesService;
	@Autowired
	MongoDao mongoDao;
	
	@GetMapping(value = "/find/{page}/{size}")
	@ResponseBody
	public BaseResponse<List<Lives>> find(@PathVariable(name = "page")int page, @PathVariable(name = "size")int size){
		List<Lives> lives = livesService.find(page, size);
		return new BaseResponse<List<Lives>>(lives);
	}
	
	@GetMapping(value = "/{id}")
	public String findOne(@PathVariable(name = "id")String id, ModelMap model){
		model.put("result", mongoDao.findOne("id", id, Lives.class));
		return "live/live";
	}
}
