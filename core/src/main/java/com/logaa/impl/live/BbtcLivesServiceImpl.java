package com.logaa.impl.live;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.logaa.dao.MongoDao;
import com.logaa.domain.enumer.FromEnum;
import com.logaa.domain.mongo.Lives;
import com.logaa.helper.MogumiaoHelper;
import com.logaa.helper.SpiderHelper;
import com.logaa.service.live.BbtcLivesService;
import com.logaa.util.date.TimestampUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@Service
public class BbtcLivesServiceImpl implements BbtcLivesService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	final static String BBTC_LIVES_URL = "http://www.8btc.com/news/page/%s";

	@Autowired
	MogumiaoHelper mogumiaoHelper;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	
	@Override
	public void bbtcLivesCrawl() {
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
	    httpClientDownloader.setProxyProvider(mogumiaoHelper.getProxy());
		String url = String.format(BBTC_LIVES_URL, 1);
		Spider.create(new BbtcLivesProcessor())
			.addPipeline(new BbtcLivesPipeline())
			.setDownloader(httpClientDownloader)
			.addUrl(url)
			.thread(5)
			.run();
	}
	
	class BbtcLivesProcessor implements PageProcessor{

		AtomicInteger aInt = new AtomicInteger();
		
		@Override
		public void process(Page page) {
			String url = page.getUrl().get();
			String index = url.substring(url.lastIndexOf("/") + 1);
			if(mongoTemplate.findById(FromEnum.BBTC.getKey() + index, Lives.class) != null){
				aInt.incrementAndGet();
			}
			if(aInt.get() > 9) return;
			if(url.indexOf("/news/page/") > -1){
				page.getHtml().$("#list_content_all").xpath("//article").nodes().forEach(e -> {
					page.addTargetRequest(e.xpath("//div/div/a/@href").get());
				});
				page.addTargetRequest(String.format(BBTC_LIVES_URL, Integer.valueOf(index).intValue() + 1));
			}else{
				String title = page.getHtml().$(".single-article").xpath("//div[3]/h1/text()").get();
				String date = page.getHtml().$(".single-article").xpath("//div[4]/span[3]/time/text()").get();
				StringBuffer content = new StringBuffer("");
				page.getHtml().$(".single-article").xpath("//div[5]/p/text()").all().forEach(e -> content.append(e));
				page.getResultItems().put("bbtcLive", new Lives(FromEnum.BBTC.getKey() + index, title, date, content.toString(),
						FromEnum.BBTC.getKey(), null, null, TimestampUtils.getTimestamp()));
			}
		}

		@Override
		public Site getSite() {
			return SpiderHelper.getSite();
		}
	}
	
	class BbtcLivesPipeline implements Pipeline{

		@Override
		public void process(ResultItems resultItems, Task task) {
			Lives live = resultItems.get("bbtcLive");
			if(live != null){
				mongoTemplate.save(live);
				logger.info(new Gson().toJson(live));
			}
		}
		
	}
}
