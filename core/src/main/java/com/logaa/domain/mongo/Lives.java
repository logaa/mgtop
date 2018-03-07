package com.logaa.domain.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="lives")
public class Lives {

	private String id;

	private String title;

	private String date;

	private String content;

	private String from;

	private String link;

	private String linkName;

	private Integer click = 0;

	private Long cTime;
	
	public Lives(String id, String title, String date, String content, String from, String link, String linkName, Long cTime){
		this.id = id;
		this.title = title;
		this.date = date;
		this.content = content;
		this.from = from;
		this.link = link;
		this.linkName = linkName;
		this.cTime = cTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public Integer getClick() {
		return click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}

	public Long getcTime() {
		return cTime;
	}

	public void setcTime(Long cTime) {
		this.cTime = cTime;
	}

}
