package com.logaa.helper;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class SpiderHelper {

	static final String IPS = ""
			+ "{\"code\":\"0\",\"msg\":[{\"port\":\"38352\",\"ip\":\"183.144.219.244\"},{\"port\":\"47160\",\"ip\":\"222.85.50.56\"},{\"port\":\"24879\",\"ip\":\"222.85.19.66\"},{\"port\":\"31572\",\"ip\":\"117.95.83.102\"},{\"port\":\"41920\",\"ip\":\"120.33.24.20\"},{\"port\":\"25970\",\"ip\":\"180.104.62.40\"},{\"port\":\"33167\",\"ip\":\"27.157.128.26\"},{\"port\":\"34880\",\"ip\":\"180.122.150.210\"},{\"port\":\"33778\",\"ip\":\"106.111.245.245\"},{\"port\":\"42484\",\"ip\":\"121.234.101.58\"},{\"port\":\"48398\",\"ip\":\"115.213.225.107\"},{\"port\":\"46103\",\"ip\":\"123.53.118.108\"},{\"port\":\"35606\",\"ip\":\"123.55.93.49\"},{\"port\":\"37008\",\"ip\":\"113.124.92.66\"},{\"port\":\"22473\",\"ip\":\"110.189.207.13\"},{\"port\":\"29683\",\"ip\":\"144.255.15.116\"},{\"port\":\"22028\",\"ip\":\"123.169.34.39\"},{\"port\":\"24520\",\"ip\":\"123.53.118.249\"},{\"port\":\"48867\",\"ip\":\"180.122.146.248\"},{\"port\":\"23587\",\"ip\":\"183.158.6.182\"}]}"
			+ "";
	
	public static Site getSite(){
		return Site.me()
				.setCharset("utf-8")
				.setSleepTime(10)
				.setCycleRetryTimes(3)
				.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
	}
	
	public static SimpleProxyProvider getProxys(){
		Response response = new Gson().fromJson(IPS, Response.class);
		List<Proxy> proxys = new ArrayList<>();
		response.getMsg().forEach(e ->{
			proxys.add(new Proxy(e.getIp(), Integer.valueOf(e.getPort())));
		});
		return new SimpleProxyProvider(proxys);
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
		private String ip;
		private String port;
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
