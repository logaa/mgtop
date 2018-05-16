package com.logaa.domain.rdb.love2io;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "t_love2io_summary", indexes={@Index(name = "love2io_summary_postsId", columnList = "postsId")})
public class Summary {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private Long postsId;
	
	private String title;
	
	private String name;
	
	private String href;
	
	@Column(columnDefinition="MEDIUMTEXT")
	private String text;
	
	public Summary() {
		super();
	}

	public Summary(Long id, Long postsId, String title, String name, String href, String text) {
		super();
		this.id = id;
		this.postsId = postsId;
		this.title = title;
		this.href = href;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPostsId() {
		return postsId;
	}

	public void setPostsId(Long postsId) {
		this.postsId = postsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
