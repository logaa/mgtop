package com.logaa.domain.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "news")
public class News {

	private String id;

	private String title;

	private String date;

	private String content;

	private String from;

	private Integer click = 0;

	private Long cTime;
	
	private String img;

	/**
	 * @param id
	 * @param title
	 * @param date
	 * @param content
	 * @param from
	 * @param cTime
	 */
	public News(String id, String title, String date, String content, String from, Long cTime, String img) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.content = content;
		this.from = from;
		this.cTime = cTime;
		this.img = img;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
