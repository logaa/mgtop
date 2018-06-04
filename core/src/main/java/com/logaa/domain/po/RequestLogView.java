package com.logaa.domain.po;

import java.util.List;

import com.logaa.domain.es.RequestLog;

public class RequestLogView extends BasePageView{

	private List<RequestLog> content;

	public List<RequestLog> getContent() {
		return content;
	}

	public void setContent(List<RequestLog> content) {
		this.content = content;
	}
}
