package com.logaa.impl.news;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.logaa.domain.enumer.FromEnum;
import com.logaa.domain.mongo.News;
import com.logaa.helper.MogumiaoHelper;
import com.logaa.helper.SpiderHelper;
import com.logaa.service.news.BbtcNewsService;
import com.logaa.util.date.TimestampUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;


@Service
public class BbtcNewsServiceImpl implements BbtcNewsService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	final static String BBTC_BASE_URL = "http://www.8btc.com";
	final static String BBTC_NEWS_URL = "http://www.8btc.com/sitemap?newPost=1&pg=%s";
	
	@Autowired
	MogumiaoHelper mogumiaoHelper;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void bbtcNewsCrawl() {
		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		httpClientDownloader.setProxyProvider(mogumiaoHelper.getProxy());
		String url = String.format(BBTC_NEWS_URL, 1);
		Spider.create(new BbtcNewsProcessor())
			.addPipeline(new BbtcNewsPipeline())
			.setDownloader(httpClientDownloader)
			.addUrl(url)
			.thread(5)
			.run();
	}

	class BbtcNewsProcessor implements PageProcessor{
		
		AtomicInteger aInt = new AtomicInteger();

		@Override
		public void process(Page page) {
			if(aInt.get() > 19) return;
			String url = page.getUrl().get();
			if(url.indexOf("/sitemap?newPost=1") > -1){
				List<String> hrefs = page.getHtml().$(".u-txtlst").xpath("//li/a/@href").all();
				if(hrefs == null || hrefs.size() == 0) return;
				page.getHtml().$(".u-txtlst").xpath("//li/a/@href").all().forEach(e -> {
					page.addTargetRequest(BBTC_BASE_URL + e);
				});
				page.addTargetRequest(String.format(BBTC_NEWS_URL, Integer.valueOf(url.substring(url.lastIndexOf("=") + 1)).intValue() + 1));
			}else{
				String index = url.substring(url.lastIndexOf("/") + 1);
				if(mongoTemplate.findById(FromEnum.BBTC.getKey() + index, News.class) != null){
					aInt.incrementAndGet();
				}
				String title = page.getHtml().$(".single-article").xpath("//div[3]/h1/text()").get();
				String date = page.getHtml().$(".single-article").xpath("//div[4]/span[3]/time/text()").get();
				Document doc = new Html(page.getHtml().$(".single-article").$(".article-content").get()).getDocument();
				doc.getElementsByClass("content-bottom").remove();
				doc.getElementsByClass("akp-adv").remove();
				doc.getElementsByClass("content-source-info").remove();
				String content = doc.getElementsByClass("article-content").get(0).toString();
				page.getResultItems().put("bbtcNews", new News(FromEnum.BBTC.getKey() + index, title, date, content,
						FromEnum.BBTC.getKey(), TimestampUtils.getTimestamp()));
			}
		}

		@Override
		public Site getSite() {
			return SpiderHelper.getSite();
		}
	}
	
	class BbtcNewsPipeline implements Pipeline{

		@Override
		public void process(ResultItems resultItems, Task task) {
			News news = resultItems.get("bbtcNews");
			if(news != null){
				mongoTemplate.save(news);
				logger.info(new Gson().toJson(news));
			}
		}
		
	}
}
