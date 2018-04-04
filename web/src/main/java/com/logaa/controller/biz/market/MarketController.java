package com.logaa.controller.biz.market;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.logaa.domain.po.cryptocompare.CoinListPo;
import com.logaa.service.crypto.CryptoCompareService;
import com.logaa.view.BaseResponse;


@Controller
@RequestMapping("/market")
public class MarketController {

	@Autowired CryptoCompareService cryptoCompareService;
	
	@GetMapping("/index")
	public String index(){
		return "market/index";
	}
	
	@GetMapping(value = "/{page}/{size}")
	@ResponseBody
	public BaseResponse<List<CoinListPo>> find(@PathVariable(name = "page")int page, @PathVariable(name = "size")int size){
		List<CoinListPo> coins = cryptoCompareService.find(page, size);
		return new BaseResponse<List<CoinListPo>>(coins);
	}
	
	@GetMapping(value = "/rank/{direction}/{top}")
	@ResponseBody
	public BaseResponse<List<CoinListPo>> rank(@PathVariable(name = "direction")String direction, @PathVariable(name = "top")int top){
		List<CoinListPo> coins = cryptoCompareService.rank(direction, top);
		return new BaseResponse<List<CoinListPo>>(coins);
	}
	
	@GetMapping(value = "/updownWeight")
	@ResponseBody
	public BaseResponse<List<Integer>> updownWeight(){
		List<Integer> updown = cryptoCompareService.updownWeight();
		return new BaseResponse<List<Integer>>(updown);
	}
	
}
