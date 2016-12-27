package com.weixin.common.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.weixin.common.model.other.Coordinate;
import com.weixin.util.Base64;

public class BaiduMapUtil {

	/**
	 * 
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	public static Coordinate convertCoord(String lng,String lat) {
		Coordinate c = new Coordinate();
		String Bd09Lng = "";
		String Bd09Lat = "";
		// 百度坐标转换接口
		String convertUrl = "http://api.map.baidu.com/ag/coord/convert";
		Map<String, String> map = new HashMap<String, String>();
		map.put("from", "2");
		map.put("to", "4");
		map.put("y", lat);
		map.put("x", lng);
		try {
			String str = HttpUtils.URLGet(convertUrl, map, "utf-8");
			// 对转换后的坐标进行Base64解码
			byte[] yBuffer = Base64.decode(JSONObject.fromObject(str)
					.getString("y"));
			byte[] xBuffer = Base64.decode(JSONObject.fromObject(str)
					.getString("x"));
			Bd09Lng = new String(xBuffer);
			Bd09Lat = new String(yBuffer);
			c.setLatitude(Bd09Lat);
			c.setLongitude(Bd09Lng);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
}

