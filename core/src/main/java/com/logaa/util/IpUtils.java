package com.logaa.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpUtils {
	
	private static Logger logger = LoggerFactory.getLogger(IpUtils.class);
	
	/**
	 * 获取 request Ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getHeader("Proxy-Client-IP");
		if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					logger.error(e.getMessage(), e);
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress.indexOf(",") > -1 && ipAddress.length() > 15)
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		return ipAddress;
	}
}
