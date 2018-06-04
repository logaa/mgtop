package com.logaa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.logaa.domain.es.RequestLog;
import com.logaa.domain.po.RequestLogView;
import com.logaa.repository.es.RequestLogRepository;
import com.logaa.util.date.TimestampUtils;
import com.logaa.view.BaseResponse;

@RestController
@RequestMapping("/request-log")
public class RequestLogRest {

	@Autowired RequestLogRepository requestLogRepository;
	
	@RequestMapping("/find/{size}/{page}")
	public String find(@PathVariable(name = "page")int page, @PathVariable(name = "size")int size,
			@RequestParam(defaultValue = "desc")String direction, @RequestParam(defaultValue = "id")String properties){
		Page<RequestLog> requestLog = requestLogRepository.findAll(
				new PageRequest(page, size, Direction.fromString(direction), properties));
		RequestLogView view = new RequestLogView();
		view.setPage(requestLog);
		requestLog.getContent().forEach(e -> e.setTimeDis(TimestampUtils.timestamp2String(e.getId(), null)));
		view.setContent(requestLog.getContent());
		return new Gson().toJson(new BaseResponse<RequestLogView>(view));
	}
}
