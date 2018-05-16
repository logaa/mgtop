package com.logaa.service.love2io;


import com.logaa.domain.po.love2io.ArchivesView;
import com.logaa.domain.po.love2io.PostsView;
import com.logaa.domain.po.love2io.SummaryView;

public interface Love2ioService {

	void postsCrawl();
	
	void summaryCrawl();

	PostsView findAll(int page, int size);

	SummaryView findOne(long postsId, String name);

	ArchivesView archives();
}
