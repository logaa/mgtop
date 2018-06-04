package com.logaa.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequestLogController {

	@GetMapping(value = "/log")
	public String index(){
		return "log";
	}
}
