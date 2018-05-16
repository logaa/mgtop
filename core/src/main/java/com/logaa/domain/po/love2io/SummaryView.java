package com.logaa.domain.po.love2io;

public class SummaryView {

	private Long postsId;
	
	private String summary;
	
	private String context;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Long getPostsId() {
		return postsId;
	}

	public void setPostsId(Long postsId) {
		this.postsId = postsId;
	}
	
}
