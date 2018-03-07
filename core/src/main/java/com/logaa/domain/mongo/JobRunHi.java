package com.logaa.domain.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "job_run_hi")
public class JobRunHi {

	private Long id;
	
	private String key;
	
	private String group;
	
	private String log;
	
	private String status;
	
	private Long cTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getcTime() {
		return cTime;
	}

	public void setcTime(Long cTime) {
		this.cTime = cTime;
	}
	
}
