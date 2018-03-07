package com.logaa.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;

import com.logaa.util.requires.Requires;

public class ApiRequest {

	public enum Verb {
		GET, POST, PUT, DELETE, PATCH
	}

	private final String url;
	private final Verb verb;
	private String charset;
	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> queryParams = new HashMap<>();
	private Map<String, String> postParams = new HashMap<>();
	private Object postEntity;
	private Object queryEntity;
	private HttpEntity entity;
	private ContentType contentType = null;

	public ApiRequest(String url, Verb verb) {
		this.url = url;
		this.verb = verb;
	}

	public ApiRequest header(String headerName, String headerVal) {
		Requires.hasText(headerName, "headerName must not be null or empty.");
		this.headers.put(headerName, headerVal == null ? "" : headerVal);
		return this;
	}

	public ApiRequest headers(Map<String, String> headers) {
		Requires.notEmpty(headers, "headers must not be null or empty.");
		this.headers.putAll(headers);
		return this;
	}

	public ApiRequest postParam(String postParam, String paramVal) {
		Requires.hasText(postParam, "paramName must not be null or empty.");
		this.postParams.put(postParam, paramVal == null ? "" : paramVal);
		return this;
	}

	public ApiRequest postParams(Map<String, String> postParams) {
		Requires.notEmpty(postParams, "queryParams must not be null or empty.");
		this.postParams.putAll(postParams);
		return this;
	}

	public ApiRequest queryParam(String paramName, String paramVal) {
		Requires.hasText(paramName, "paramName must not be null or empty.");
		this.queryParams.put(paramName, paramVal == null ? "" : paramVal);
		return this;
	}

	public ApiRequest queryParams(Map<String, String> queryParams) {
		Requires.notEmpty(queryParams, "queryParams must not be null or empty.");
		this.queryParams.putAll(queryParams);
		return this;
	}

	public ApiRequest entity(HttpEntity entity) {
		Requires.isTrue(verb != Verb.GET, "Get verb cann't accept entity.");
		Requires.notNull(entity, "entity must not be null.");
		this.entity = entity;
		return this;
	}

	public ApiRequest postEntity(Object postEntity) {
		Requires.isTrue(verb != Verb.GET, "Get verb cann't accept entity.");
		Requires.notNull(postEntity, "entity must not be null.");
		this.postEntity = postEntity;
		return this;
	}

	public ApiRequest queryEntity(Object queryEntity) {
		Requires.isTrue(verb != Verb.POST, "Post verb cann't accept entity.");
		Requires.notNull(queryEntity, "entity must not be null.");
		this.queryEntity = queryEntity;
		return this;
	}

	public ApiRequest setContentType(ContentType contentType) {
		this.contentType = contentType;
		return this;
	}

	public ApiRequest charset(String charset) {
		this.charset = charset;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Verb getVerb() {
		return verb;
	}

	public Map<String, String> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}

	public Map<String, String> getQueryParams() {
		return Collections.unmodifiableMap(queryParams);
	}

	public Map<String, String> getPostParams() {
		return Collections.unmodifiableMap(postParams);
	}

	public HttpEntity getEntity() {
		return entity;
	}

	public Object getPostEntity() {
		return postEntity;
	}

	public Object getQueryEntity() {
		return queryEntity;
	}

	public void setQueryEntity(Object queryEntity) {
		this.queryEntity = queryEntity;
	}

	public String getCharset() {
		return charset;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public ApiResponse send() {
		return RequestSinker.sink(this);
	}

}
