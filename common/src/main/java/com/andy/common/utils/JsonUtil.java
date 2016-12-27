package com.andy.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class JsonUtil {
	
	/**
	 * 从一个JSON 对象字符格式中得到一个java对象，形如： {"id" : idValue, "name" : nameValue,
	 * "aBean" : {"aBeanId" : aBeanIdValue, ...}}
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T getObject(String jsonString, Class<T> clazz) {
		return JSON.parseObject(jsonString, clazz);
	}

	/**
	 * 从一个JSON数组得到一个java对象数组，形如： [{"id" : idValue, "name" : nameValue}, {"id" :
	 * idValue, "name" : nameValue}, ...]
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> getObjectArray(String jsonString, Class<T> clazz) {
		return JSON.parseArray(jsonString, clazz);
	}

	/**
	 * 从json HASH表达式中获取一个map，该map支持嵌套功能 形如：{"id" : "johncon", "name" : "小强"}
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> getMap(String jsonString) {
		return JSON.parseObject(jsonString);
	}

	
	/**
	 * 把对象转换为json字符串 
	 * 其中的日期为 yyyy-MM-dd HH:mm:ss
	 * @param obj
	 * @return
	 */
	public static String getJsonString(Object obj) {
		return JSON.toJSONStringWithDateFormat(obj, JSON.DEFFAULT_DATE_FORMAT);
	}
	
	/**
	 * 自定义日期格式转换
	 * @param obj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonString(Object obj, String dataFormat) {
		return JSON.toJSONStringWithDateFormat(obj, dataFormat);
	}
}
