package com.logaa.domain.es;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "mgtop", type = "requestLog", shards = 5, replicas = 1, indexStoreType = "fs", refreshInterval = "-1")
public class RequestLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Field(type = FieldType.Long)
	private Long id;
	
	@Field(type = FieldType.String)
	private String ip;
	
	@Field(type = FieldType.String)
	private String url;
	
	@Field(type = FieldType.String)
	private String httpMethod;
	
	@Field(type = FieldType.String)
	private String classMethod;
	
	@Field(type = FieldType.String)
	private String args;
	
	@Field(type = FieldType.Long)
	private Long proceedTime;
	
	@Field(type = FieldType.String)
	private String returning;
	
	@Field(type = FieldType.String)
	private String exception;
	
	/*  辅助字段   */
	private String timeDis;
	
	public RequestLog() {
		super();
	}

	public RequestLog(Long id, String ip, String url, String httpMethod, String classMethod, String args,
			Long proceedTime, String returning, String exception) {
		super();
		this.id = id;
		this.ip = ip;
		this.url = url;
		this.httpMethod = httpMethod;
		this.classMethod = classMethod;
		this.args = args;
		this.proceedTime = proceedTime;
		this.returning = returning;
		this.exception = exception;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getClassMethod() {
		return classMethod;
	}
	public void setClassMethod(String classMethod) {
		this.classMethod = classMethod;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public Long getProceedTime() {
		return proceedTime;
	}
	public void setProceedTime(Long proceedTime) {
		this.proceedTime = proceedTime;
	}
	public String getReturning() {
		return returning;
	}
	public void setReturning(String returning) {
		this.returning = returning;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getTimeDis() {
		return timeDis;
	}

	public void setTimeDis(String timeDis) {
		this.timeDis = timeDis;
	}
	
}
