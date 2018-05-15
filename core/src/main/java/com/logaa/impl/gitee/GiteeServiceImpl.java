package com.logaa.impl.gitee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.logaa.domain.rdb.gitee.Project;
import com.logaa.helper.SpiderHelper;
import com.logaa.repository.rdb.gitee.ProjectRepository;
import com.logaa.service.gitee.GiteeService;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

@Service
public class GiteeServiceImpl implements GiteeService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static String GITEE = "https://gitee.com";
	private final static String GITEE_EXPLORE = "https://gitee.com/explore";
	
	
	@Autowired ProjectRepository projectRepository;
	
	@Override
	public void getProject() {
		Spider
			.create(new GiteeProjectPageProcessor())
			.addPipeline(new GiteeProjectPipeline())
			.addUrl(GITEE_EXPLORE).thread(5).run();
	}

	class GiteeProjectPageProcessor implements PageProcessor{

		Map<String, Project> projects = new HashMap<>();
		
		@Override
		public void process(Page page) {
			String url = page.getUrl().get();
			if(url.indexOf("gitee.com/explore") > -1){
				List<Selectable> selectable = page.getHtml().$("#git-discover-list").$(".item").nodes();
				if(selectable.isEmpty() || url.indexOf("gitee.com/explore?page=275") > -1) return;
				selectable.forEach(e -> {
					String href = GITEE + e.xpath("//div/div[1]/h3/a/@href").get();
					String title = e.xpath("//div/div[1]/h3/a/text()").get();
					String watch = e.xpath("//div/div[1]/div/a[1]/span/text()").get();
					String star = e.xpath("//div/div[1]/div/a[2]/span/text()").get();
					String fork = e.xpath("//div/div[1]/div/a[3]/span/text()").get();
					List<String> labels = e.xpath("//div/div[3]/a/text()").all();
					Project project = new Project();
					project.setTitle(title);
					project.setWatch(watch);
					project.setStar(star);
					project.setFork(fork);
					project.setLabel(labels.toString());
					project.setHref(href);
					projects.put(href, project);
					page.addTargetRequest(href);
				});
				int existsPage = url.indexOf("?page=");
				if(existsPage > -1){
					String pageIndex = url.substring(existsPage + 6, url.length());
					page.addTargetRequest(GITEE_EXPLORE + "?page=" + (Integer.valueOf(pageIndex).intValue() + 1));
				}else{
					page.addTargetRequest(GITEE_EXPLORE + "?page=" + 2);
				}
			}else{
				String httpsUrl = page.getHtml().xpath("//*[@id=\"git-project-bread\"]/div[3]/div[1]/a[1]/@data-url").get();
				String sshUrl = page.getHtml().xpath("//*[@id=\"git-project-bread\"]/div[3]/div[1]/a[2]/@data-url").get();
				String description = page.getHtml().xpath("//*[@id=\"git-project-content\"]/div[1]/div[1]/span[1]/text()").get();
				Project project = projects.get(url);
				project.setDescription(description);
				project.setHttpsUrl(httpsUrl);
				project.setSshUrl(sshUrl);
				page.getResultItems().put("project", project);
				projects.remove(url);
			}
		}

		@Override
		public Site getSite() {
			return SpiderHelper.getSite();
		}
		
	}
	
	class GiteeProjectPipeline implements Pipeline{

		@Override
		public void process(ResultItems resultItems, Task task) {
			Project project = resultItems.get("project");
			if(project != null){
				projectRepository.save(project);
				logger.info(new Gson().toJson(project));
			}
		}
		
	}
	
}
