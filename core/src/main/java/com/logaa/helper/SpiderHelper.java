package com.logaa.helper;

import us.codecraft.webmagic.Site;

public class SpiderHelper {

	public static Site getSite(){
		return Site.me()
				.setCharset("utf-8")
				.setSleepTime(10)
				.setCycleRetryTimes(3)
				.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
	}
}
