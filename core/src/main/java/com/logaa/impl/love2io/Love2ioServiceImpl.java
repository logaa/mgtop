package com.logaa.impl.love2io;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.logaa.domain.po.love2io.ArchivesView;
import com.logaa.domain.po.love2io.PostsResult;
import com.logaa.domain.po.love2io.PostsView;
import com.logaa.domain.po.love2io.SummaryView;
import com.logaa.domain.rdb.love2io.Posts;
import com.logaa.domain.rdb.love2io.Summary;
import com.logaa.http.ApiRequest;
import com.logaa.http.ApiRequest.Verb;
import com.logaa.repository.rdb.love2io.PostsRepository;
import com.logaa.repository.rdb.love2io.SummaryRepository;
import com.logaa.service.love2io.Love2ioService;
import com.logaa.util.Base64;
import com.youbenzi.mdtool.tool.MDTool;

import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

@Service
public class Love2ioServiceImpl implements Love2ioService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String POST_URL = "https://love2.io/get/posts?filter=all&sort=hot&p=%s"; // page
	private static final String SUMMARY_URL = "https://raw.love2.io/%s/%s/%s/%s"; // {author}/{name}/{sha}/{md}
	private static final String SUMMARY_MD = "SUMMARY.md";
	
	@Autowired PostsRepository postsRepository;
	@Autowired SummaryRepository summaryRepository;
	
	@Override
	public void postsCrawl() {
		boolean next = true;
		Integer nextPage = 1;
		while (next) {
			String result = new ApiRequest(String.format(POST_URL, nextPage), Verb.GET).send().getResult();
			logger.info("RESULT ---> " + result);
			PostsResult postResult = new Gson().fromJson(result, PostsResult.class);
			postResult.getResult().forEach(e -> e.setCover("https://oss.love2.io/" + e.getCover()));
			postsRepository.save(postResult.getResult());
			nextPage = postResult.getPage().getNextPage();
			if(nextPage == null) next = false;
		}
	}
	
	@Override
	public void summaryCrawl() {
		postsRepository.findAll().forEach(e -> {
			if(e.getId() > 180){
				// summary node
				String summaryHref = String.format(SUMMARY_URL, e.getAuthorUsername(), e.getName(), e.getSha(), SUMMARY_MD);
				// summary nodes
				String result = new ApiRequest(String.format(SUMMARY_URL, e.getAuthorUsername(), e.getName(), e.getSha(), SUMMARY_MD), 
						Verb.GET).send().getResult();
				logger.info("RESULT ---> " + result);
				summaryRepository.save(new Summary(null, e.getId(), SUMMARY_MD, SUMMARY_MD, summaryHref, Base64.getBASE64(result)));
				Html html = new Html(MDTool.markdown2Html(result));
				List<Selectable> selectable = html.$("li").nodes();
			    selectable.forEach(x -> {
			    	String title = x.xpath("//a/@title").get();
			    	if(StringUtils.isNotBlank(title)){
			    		String name = x.xpath("//a/@href").get();
			    		String href = String.format(SUMMARY_URL, e.getAuthorUsername(), e.getName(), e.getSha(), name);
			    		String text = new ApiRequest(href, Verb.GET).send().getResult();
						logger.info("RESULT ---> " + text);
						if(text != null && !text.contains("404: Not Found")){
							summaryRepository.save(new Summary(null, e.getId(), title, name, href, Base64.getBASE64(text)));
						}
			    	}
			    });
			}
		});
	}

	@Override
	public PostsView findAll(int page, int size) {
		PostsView view = new PostsView();
		Page<Posts> posts = postsRepository.findAll(new PageRequest(page, size, Direction.DESC, "starNum"));
		view.setPage(page);
		view.setContent(posts.getContent());
		return view;
	}

	@Override
	public SummaryView findOne(long postsId, String name) {
		SummaryView view = new SummaryView();
		view.setPostsId(postsId);
		List<Summary> summary = summaryRepository.findByPostsIdAndName(postsId, SUMMARY_MD);
		if(summary != null && !summary.isEmpty()){
			view.setSummary(Base64.getFromBASE64(summary.get(0).getText()));
		}
		List<Summary> context = summaryRepository.findByPostsIdAndName(postsId, name);
		if(context != null && !context.isEmpty()){
			view.setContext(Base64.getFromBASE64(context.get(0).getText()));
		}
		return view;
	}

	@Override
	public ArchivesView archives() {
		List<Posts> posts = postsRepository.findAll();
		//posts.stream().
		return null;
	}
	
}
