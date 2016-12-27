package com.weixin.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.dom4j.Node;

import com.weixin.common.config.load.xml.XMLConfigLoader;

public class DataUtil {

	/**
	 * dom4j节点文本获取
	 * @param node
	 * @return
	 */
	public static String getNodeValue(Node node){
		if(node!=null){
			return node.getText();
		}else{
			return "";
		}
	}
	
	/**
	 * 生成指定位数的随机验证码
	 * @param length
	 * @return
	 */
	public static String generateValidateCode(int length){
		Random r = new Random();
		StringBuffer code = new StringBuffer();
		for (int i = 0; i < length; i++) {
			code.append(r.nextInt(length));
		}
		return code.toString();
	}
	
	/**
	 * 生成随机验证码默认6位
	 * @return
	 */
	public static String generateValidateCode(){
		return generateValidateCode(6);
	}
	
	public static Map<String, String> getSuccessRetMap(){
		Map<String, String> retMap = new HashMap<String, String>();
		retMap.put(CommonConstants.ERR_CODE, "0");
		retMap.put(CommonConstants.ERR_MSG, "成功");
		return retMap;
	}
	
	
	public static String getFilePathByType(String fname, int msgType, String createFileDate) {
		String filePath = "";
		String year = createFileDate.substring(0, 4);
		String month = createFileDate.substring(4, 6);
		if(msgType == CommonConstants.MSG_TYPE_IMAGE){
			filePath = XMLConfigLoader.getMediaFileLocationStatic().getImage() + year + "/" + month + "/" + fname;
		}else if(msgType == CommonConstants.MSG_TYPE_MUSIC){
			filePath = XMLConfigLoader.getMediaFileLocationStatic().getMusic() + year + "/" + month + "/"+ fname;
		}else if(msgType == CommonConstants.MSG_TYPE_VIDEO){
			filePath = XMLConfigLoader.getMediaFileLocationStatic().getVideo() + year + "/" + month + "/"+ fname;
		}else if(msgType == CommonConstants.MSG_TYPE_NEWS){
			if(fname.endsWith("html") || fname.endsWith("htm")){
				filePath = XMLConfigLoader.getMediaFileLocationStatic().getNewsHtml() + year + "/" + month + "/"+ fname;
			}else{
				filePath = XMLConfigLoader.getMediaFileLocationStatic().getNewsImage() + year + "/" + month + "/"+ fname;
			}
		}
		return filePath;
	}
}
