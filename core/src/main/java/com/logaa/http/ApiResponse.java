package com.logaa.http;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ApiResponse {

	private static Logger LOG = LoggerFactory.getLogger(ApiResponse.class);
	private HttpResponse httpResponse;
	private HttpClientContext context;
	private String body;
	private final byte[] content;

	ApiResponse(HttpResponse httpResponse, HttpClientContext context, byte[] content) {
		this.httpResponse = httpResponse;
		this.content = content;
		this.context = context;
		try {
			this.body = new String(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public boolean isOk() {
		return getStatusCode() == 200 || getStatusCode() == 201;
	}

	public int getStatusCode() {
		return httpResponse.getStatusLine().getStatusCode();
	}

	public HttpClientContext getContext() {
		return this.context;
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public Header[] getAllHeaders() {
		if (this.httpResponse == null)
			return null;
		return this.httpResponse.getAllHeaders();
	}

	public Header[] getHeaders(String param) {
		if (this.httpResponse == null)
			return null;
		return this.getHttpResponse().getHeaders(param);
	}

	public byte[] getContent() {
		return this.content;
	}

	public <T> List<T> getResultList(Class<T> type) {
		return ApiHttpCodec.convertToList(this.body, type);
	}

	public <T> T getResult(Class<T> type) {
		return ApiHttpCodec.convertAsType(this.body, type);
	}

	public String getResult() {
		return this.body;
	}

	public String toString() {
		return this.body;
	}
}
