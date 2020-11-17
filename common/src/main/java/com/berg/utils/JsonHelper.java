package com.berg.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {

	public static String toJson(Object object,JsonInclude.Include include) throws JsonProcessingException {
		ObjectMapper om = getObjectMapper(include);
		return om.writeValueAsString(object);
	}

	public static <T> T fromJson(String json, Class<T> cls,JsonInclude.Include include) throws IOException {
		ObjectMapper om = getObjectMapper(include);
		return om.readValue(json, cls);
	}

	public static <T> T fromJson(String json, TypeReference<T> valueTypeRef, JsonInclude.Include include) throws IOException {
		ObjectMapper om = getObjectMapper(include);
		return om.readValue(json, valueTypeRef);
	}
	
	public static JsonNode json2JsonNode(String json) throws IOException,JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return om.readTree(json);		
	}
	
	private static ObjectMapper getObjectMapper(JsonInclude.Include include){
		ObjectMapper om = new ObjectMapper();
		if(include!=null){
			om.setSerializationInclusion(include);
		}else{
			om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;
	}

}
