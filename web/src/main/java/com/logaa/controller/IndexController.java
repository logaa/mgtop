package com.logaa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.logaa.helper.SpringHelper;
import com.logaa.service.crypto.CryptoCompareService;


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
	
	@Autowired CryptoCompareService cryptoCompareService;
	
	@GetMapping(value = "/market")
	@ResponseBody
	public String market(){
		cryptoCompareService.updateCryptoCompareMarket();
		return "imarket";
	}
}
