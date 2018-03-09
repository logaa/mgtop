package com.logaa.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.logaa.http.ApiRequest;
import com.logaa.http.ApiRequest.Verb;

import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

@Component
public class MogumiaoHelper {

	final static String MOGUMIAO_FREE_IP = "MOGUMIAO_FREE_IP";
	
	final static String URL = "http://www.mogumiao.com/proxy/free/listFreeIp";
	
	@Autowired
	ValueOperations<String, String> valueOperations;
	
	public void updateIps(){
		Response response = new Gson().fromJson(new ApiRequest(URL, Verb.GET).send().getResult(), Response.class);
		if(response != null && "0".equals(response.getCode())){
			List<String> freeIps = new ArrayList<String>();
			response.getMsg().forEach(e -> freeIps.add(e.getIp() + ":" + e.getPort()));
			valueOperations.set(MOGUMIAO_FREE_IP, new Gson().toJson(freeIps), getTimeOut(response.getMsg().get(0).getCreateTime()), TimeUnit.MILLISECONDS);
		}
	}
	
	public String getIps(){
		String ips = valueOperations.get(MOGUMIAO_FREE_IP);	
		if(ips == null){
			updateIps();
			ips = valueOperations.get(MOGUMIAO_FREE_IP);
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
	
	private long getTimeOut(long time){
		Calendar calendar = Calendar.getInstance();
		long currTime = calendar.getTimeInMillis();
		calendar.setTimeInMillis(time);
		calendar.add(Calendar.MINUTE, 5);
		return  calendar.getTimeInMillis() - currTime;
	}
	
	class Response{
		private String code;
		private List<Result> msg;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public List<Result> getMsg() {
			return msg;
		}
		public void setMsg(List<Result> msg) {
			this.msg = msg;
		}
	}
	
	class Result{
		private Integer id;
		private String ip;
		private Integer port;
		private Integer responseTime;
		private Long createTime;
		private Long updateTime;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public Integer getPort() {
			return port;
		}
		public void setPort(Integer port) {
			this.port = port;
		}
		public Integer getResponseTime() {
			return responseTime;
		}
		public void setResponseTime(Integer responseTime) {
			this.responseTime = responseTime;
		}
		public Long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}
		public Long getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Long updateTime) {
			this.updateTime = updateTime;
		}
	}
}
