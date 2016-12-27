package com.weixin.common.model.mass.gson;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GsonHelper {

	public static String getAsString(JsonElement jsonElement) {
		if(jsonElement == null){
			return "";
		}
		return jsonElement.getAsString();
	}

	public static long getAsPrimitiveLong(JsonElement jsonElement) {
		if(jsonElement == null){
			return 0;
		}else{
			return jsonElement.getAsLong();
		}
	}

	public static Object getInteger(JsonObject o, String string) {
		if(o.get(string) == null){
			return 0;
		}else{
			return o.get(string).getAsInt();
		}
	}

	public static Object getString(JsonObject o, String string) {
		if(o.get(string) == null){
			return "";
		}else{
			return o.get(string).getAsString();
		}
	}

}
