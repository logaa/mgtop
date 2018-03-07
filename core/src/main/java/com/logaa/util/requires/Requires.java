package com.logaa.util.requires;

import java.util.List;
import java.util.Map;

public class Requires {

	public static <T> T notNull(T reference) {
		if (reference == null) {
			throw new NullPointerException();
		}
		return reference;
	}

	public static <T> T notNull(T reference, String errorMessage) {
		if (reference == null) {
			throw new NullPointerException(errorMessage);
		}
		return reference;
	}

	public static String hasText(String str) {
		if (str == null || str.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return str;
	}

	public static String hasText(String str, String errorMessage) {
		if (str == null || str.isEmpty()) {
			throw new IllegalArgumentException(errorMessage);
		}
		return str;
	}

	public static List<?> notEmpty(List<?> list) {
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return list;
	}

	public static List<?> notEmpty(List<?> list, String errorMessage) {
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException(errorMessage);
		}
		return list;
	}

	public static Map<?, ?> notEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return map;
	}

	public static Map<?, ?> notEmpty(Map<?, ?> map, String errorMessage) {
		if (map == null || map.isEmpty()) {
			throw new IllegalArgumentException(errorMessage);
		}
		return map;
	}

	public static void isTrue(boolean isTrue, String errorMessage) {
		if (!isTrue) {
			throw new IllegalArgumentException(errorMessage);
		}
	}
}
