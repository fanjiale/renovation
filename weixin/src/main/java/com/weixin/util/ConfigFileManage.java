package com.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 控制文件管理
 * @author XMZ
 * @date：2015-6-18
 */
public class ConfigFileManage {

	/**
	 * 根据键名获取配置的值
	 * @param key
	 * @return String
	 */
	public static String getConfigValue(String key) {
		String result = null;
		Properties prop = new Properties();
		try {
			InputStream inputStream  = ConfigFileManage.class.getClassLoader().getResourceAsStream("webServiceUrl.properties");
			prop.load(inputStream);
			result = prop.getProperty(key).trim();
			inputStream.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
