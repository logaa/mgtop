package com.logaa.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.logaa.util.convert.BeanMapConvert;

final class ApiHttpRequestUrlBuilder {

	public static HttpGet buildHttpGet(String baseurl, Object getEntity) {
		Map<String, String> params = BeanMapConvert.objectToMap(getEntity);
		return buildHttpGet(baseurl, params);
	}

	public static HttpGet buildHttpGet(String baseurl, String charset, Object getEntity) {
		Map<String, String> params = BeanMapConvert.objectToMap(getEntity);
		return buildHttpGet(baseurl, charset, params);
	}

	public static HttpGet buildHttpGet(String baseurl, Map<String, String> params) {
		return new HttpGet(buildQueryUrl(baseurl, params));
	}

	public static HttpGet buildHttpGet(String baseurl, String charset, Map<String, String> params) {
		return new HttpGet(buildQueryUrl(baseurl, charset, params));
	}

	public static HttpPost buildHttpPost(String baseurl, Object postEntity) {
		Map<String, String> params = BeanMapConvert.objectToMap(postEntity);
		return buildHttpPost(baseurl, params);
	}

	public static HttpPost buildHttpPost(String baseurl, String charset, Object postEntity) {
		Map<String, String> params = BeanMapConvert.objectToMap(postEntity);
		return buildHttpPost(baseurl, charset, params);
	}

	public static HttpPost buildHttpPost(String baseurl, String charset, Map<String, String> params) {
		HttpPost httpRequst = new HttpPost(baseurl);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				nameValuePair.add(new BasicNameValuePair(key, params.get(key)));
			}
			try {
				httpRequst.setEntity(new UrlEncodedFormEntity(nameValuePair, charset));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return httpRequst;
	}

	public static HttpPost buildHttpPost(String baseurl, Map<String, String> params) {
		HttpPost httpRequst = new HttpPost(baseurl);// 创建HttpPost对象
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				nameValuePair.add(new BasicNameValuePair(key, params.get(key)));
			}
			try {
				httpRequst.setEntity(new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return httpRequst;
	}

	public static String buildQueryUrl(String baseurl, Map<String, String> params) {
		return ApiHttpRequestUrlBuilder.buildQueryUrl(baseurl, HTTP.UTF_8, params);
	}

	public static String buildQueryUrl(String baseurl, String charset, Map<String, String> params) {

		String url = transformBaseUrl(baseurl, charset);
		if (params.isEmpty()) {
			return url;
		}
		if (url.indexOf("#") > 0) {
			url = url.substring(0, url.indexOf("#"));
		}
		int qp = url.indexOf("?");
		if (qp == -1) {
			url = url + "?";
		} else if (qp < url.length() - 1 && !url.endsWith("&")) {
			url = url + "&";
		}
		StringBuilder buf = new StringBuilder().append(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			buf.append(urlEncode(entry.getKey(), charset)).append("=");
			buf.append(urlEncode(entry.getValue(), charset)).append("&");
		}
		return buf.toString();
	}

	private static String transformBaseUrl(String baseurl, String charset) {
		Matcher m = Pattern.compile("[^\\x00-\\x7f[\\s]]").matcher(baseurl);
		StringBuffer buf = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(buf, urlEncode(m.group(), charset));
		}
		m.appendTail(buf);
		return buf.toString();
	}

	private static String urlEncode(String param, String charset) {
		try {
			return URLEncoder.encode(param, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private ApiHttpRequestUrlBuilder() {
	}
}
