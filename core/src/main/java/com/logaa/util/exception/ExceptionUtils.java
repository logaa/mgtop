package com.logaa.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
	
	private ExceptionUtils(){};
	
	/**
	 * 获取exception的详细错误信息。
	 * @param e
	 * @return
	 */
	public static String getExceptionMessage(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}
}
