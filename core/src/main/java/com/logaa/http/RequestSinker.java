package com.logaa.http;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

final class RequestSinker {

	private static CloseableHttpClient HTTP_CLIENT;
	private static PoolingHttpClientConnectionManager CONNECTION_MANAGER;

	static {
		CONNECTION_MANAGER = new PoolingHttpClientConnectionManager();
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
				.setMaxLineLength(2000).build();
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
		CONNECTION_MANAGER.setMaxTotal(100);
		CONNECTION_MANAGER.setDefaultMaxPerRoute(50);
		CONNECTION_MANAGER.setDefaultSocketConfig(socketConfig);
		CONNECTION_MANAGER.setDefaultConnectionConfig(connectionConfig);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(20000)
				.setConnectionRequestTimeout(80000).setStaleConnectionCheckEnabled(true).build();
		HTTP_CLIENT = HttpClients.custom().setConnectionManager(CONNECTION_MANAGER)
				.setDefaultRequestConfig(requestConfig).build();
	}

	static ApiResponse sink(ApiRequest request) {
		HttpUriRequest httpRequest = UriRequest(request);
		return RequestSinker.doSink(httpRequest, request);
	}

	private static ApiResponse doSink(HttpUriRequest httpRequest, ApiRequest request) {
		HttpEntity entity = null;
		CloseableHttpResponse httpResponse = null;
		try {
			HttpClientContext context = HttpClientContext.create();
			httpResponse = HTTP_CLIENT.execute(httpRequest, context);
			entity = httpResponse.getEntity();
			if (null == entity) {
				return new ApiResponse(httpResponse, context, new byte[0]);
			}
			byte[] content = EntityUtils.toByteArray(entity);
			ApiResponse response = new ApiResponse(httpResponse, context, content);
			return response;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			responseClose(httpResponse);
			EntityUtils.consumeQuietly(entity);
		}
	}

	private static HttpPost resolveHttpPost(ApiRequest request) {
		HttpPost httpPost = null;
		if (null != request.getPostEntity()) {
			if (null != request.getCharset()) {
				httpPost = ApiHttpRequestUrlBuilder.buildHttpPost(request.getUrl(), request.getCharset(),
						request.getPostEntity());
			} else {
				httpPost = ApiHttpRequestUrlBuilder.buildHttpPost(request.getUrl(), request.getPostEntity());
			}
			return httpPost;
		}
		if (null != request.getEntity()) {
			if (null != request.getCharset()) {
				httpPost = new HttpPost(ApiHttpRequestUrlBuilder.buildQueryUrl(request.getUrl(), request.getCharset(),
						request.getPostParams()));
			} else {
				httpPost = new HttpPost(
						ApiHttpRequestUrlBuilder.buildQueryUrl(request.getUrl(), request.getPostParams()));
			}
			httpPost.setEntity(request.getEntity());
			return httpPost;
		}
		if (null != request.getCharset()) {
			httpPost = ApiHttpRequestUrlBuilder.buildHttpPost(request.getUrl(), request.getCharset(),
					request.getPostParams());
		} else {
			httpPost = ApiHttpRequestUrlBuilder.buildHttpPost(request.getUrl(), request.getPostParams());
		}
		return httpPost;
	}

	private static HttpGet resolveHttpGet(ApiRequest request) {
		HttpGet httpGet = null;
		if (null != request.getQueryEntity()) {
			if (null != request.getCharset()) {
				httpGet = ApiHttpRequestUrlBuilder.buildHttpGet(request.getUrl(), request.getCharset(),
						request.getQueryEntity());
			} else {
				httpGet = ApiHttpRequestUrlBuilder.buildHttpGet(request.getUrl(), request.getQueryEntity());
			}
			return httpGet;
		}

		if (null != request.getCharset()) {
			httpGet = ApiHttpRequestUrlBuilder.buildHttpGet(request.getUrl(), request.getCharset(),
					request.getQueryParams());
		} else {
			httpGet = ApiHttpRequestUrlBuilder.buildHttpGet(request.getUrl(), request.getQueryParams());
		}
		return httpGet;
	}

	private static HttpUriRequest UriRequest(ApiRequest request) {
		HttpUriRequest httpUriRequest = null;
		switch (request.getVerb()) {
		case GET:
			httpUriRequest = resolveHttpGet(request);
			break;
		case POST:
			HttpPost httpPost = resolveHttpPost(request);
			httpUriRequest = httpPost;
			break;
		default:
			throw new UnsupportedOperationException("Only supported GET or POST verb.");
		}
		for (String key : request.getHeaders().keySet()) {
			httpUriRequest.setHeader(key, request.getHeaders().get(key));
		}
		return httpUriRequest;
	}

	private static void responseClose(CloseableHttpResponse httpResponse) {
		if (httpResponse != null) {
			try {
				httpResponse.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
