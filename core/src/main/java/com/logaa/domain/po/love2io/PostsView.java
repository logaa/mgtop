package com.logaa.domain.po.love2io;

import java.util.List;

import com.logaa.domain.po.BasePageView;
import com.logaa.domain.rdb.love2io.Posts;

public class PostsView extends BasePageView{

	private List<Posts> content;

	public List<Posts> getContent() {
		return content;
	}

	public void setContent(List<Posts> content) {
		this.content = content;
	}
}
