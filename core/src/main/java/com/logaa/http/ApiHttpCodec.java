package com.logaa.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

public final class ApiHttpCodec {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		OBJECT_MAPPER.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				jgen.writeString("");
			}
		});
	}

	static byte[] writeAsBytes(Object entity) {
		try {

			return OBJECT_MAPPER.writeValueAsBytes(entity);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readBytesAsType(byte[] val, Class<T> type) {
		try {
			return OBJECT_MAPPER.readValue(val, type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJsonString(Object bean) {
		if (null == bean) {
			return null;
		}

		try {
			return OBJECT_MAPPER.writeValueAsString(bean);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T convertAsType(String from, Class<T> clsType) {
		try {
			return OBJECT_MAPPER.readValue(from, clsType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> readBytesAsListType(byte[] val, Class<T> eleType) {
		try {
			JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, eleType);
			return OBJECT_MAPPER.readValue(val, javaType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> convertAsListType(Object val, Class<T> eleType) {
		JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, eleType);
		return OBJECT_MAPPER.convertValue(val, javaType);
	}

	public static <T> List<T> convertToList(String json, Class<T> clazz) {
		if (StringUtils.isBlank(json))
			return Collections.emptyList();
		try {
			return OBJECT_MAPPER.readValue(json,
					OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object convertAsParametricType(Object val, Class<?> parametrized, Class<?>[] parameterClasses) {
		JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
		return OBJECT_MAPPER.convertValue(val, javaType);
	}
}
