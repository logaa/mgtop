package com.logaa.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.logaa.http.ApiRequest;
import com.logaa.http.ApiRequest.Verb;

import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

@Component
public class XdailiHelper {

	Logger logger = LoggerFactory.getLogger(XdailiHelper.class);
	
	final static String FREE_IPS = "FREE_IPS";
	final static String URL = "http://www.xdaili.cn/ipagent/freeip/getFreeIps";
	
	@Autowired
	ValueOperations<String, String> valueOperations;
	
	public void updateIps(){
		String result = new ApiRequest(URL, Verb.GET).send().getResult();
		Response response = new Gson().fromJson(result, Response.class);
		if(response != null && response.getResult() != null && !response.getResult().getRows().isEmpty()){
			List<Rows> rows = response.getResult().getRows();
			List<String> freeIps = new ArrayList<String>();
			rows.forEach(e -> freeIps.add(e.getIp() + ":" + e.getPort()));
			valueOperations.set(FREE_IPS, new Gson().toJson(freeIps), 10, TimeUnit.MINUTES);
		}
	}
	
	public String getIps(){
		String ips = valueOperations.get(FREE_IPS);	
		if(ips == null){
			updateIps();
			ips = valueOperations.get(FREE_IPS);
		}
		return ips;
	}
	
	@SuppressWarnings("unchecked")
	public SimpleProxyProvider getProxy(){
		List<Proxy> proxys = new ArrayList<>();
		new Gson().fromJson(getIps(), List.class).forEach(e -> {
			String ip = e.toString();
			proxys.add(new Proxy((ip.split(":"))[0], Integer.valueOf((ip.split(":"))[1])));
		});
		return new SimpleProxyProvider(proxys);
	}
	
	class Response{
		
		@SerializedName(value = "RESULT")
		Result result;
		
		public Result getResult() {
			return result;
		}
		public void setResult(Result result) {
			this.result = result;
		}
	}
	class Result{
		
		List<Rows> rows;

		public List<Rows> getRows() {
			return rows;
		}

		public void setRows(List<Rows> rows) {
			this.rows = rows;
		}
	}
	class Rows{
		
		String ip;
		
		String port;
		
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
	}
	
}
