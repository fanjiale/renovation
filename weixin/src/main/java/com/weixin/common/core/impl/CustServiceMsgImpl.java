package com.weixin.common.core.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.core.CustServiceMsg;
import com.weixin.common.core.WxService;
import com.weixin.common.model.config.CustServiceConfig;
import com.weixin.common.model.message.CustNewsMessage;
import com.weixin.common.model.message.CustTextMessage;
import com.weixin.common.util.CommonConstants;
import com.weixin.common.util.MessageUtil;
import com.weixin.common.util.MsgHandleUtil;

@Component("custServiceMsg")
public class CustServiceMsgImpl implements CustServiceMsg{

	@Autowired
	private WxService wxService;
	@Autowired
	private ConfigLoader configLoader;
	
	/**
	 * 发送客服消息 
	 * @param id：客户消息的key
	 * @param openId
	 */
	public Map<String,Object> sendCustServiceMsg(String id, String openId) {
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			String sendMessage = null;
			Map<String,CustServiceConfig> map = configLoader.getCustMsgConfigMap();
			CustServiceConfig custServiceConfig = map.get(id);
			
			//文本消息
			if(custServiceConfig.getMsgType().equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)){
				CustTextMessage custTextMessage = new CustTextMessage();
				custTextMessage.setMsgtype(custServiceConfig.getMsgType());
				custTextMessage.setTouser(openId);
				CustTextMessage.TextContent textContent = new CustTextMessage().new TextContent();
				textContent.setContent(custServiceConfig.getMsgContent());
				custTextMessage.setText(textContent);
				
				JSONObject jsonObject = JSONObject.fromObject(custTextMessage);
				sendMessage = jsonObject.toString();
				
			}else if(custServiceConfig.getMsgType().equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)){
				//图文消息
				CustNewsMessage custNewsMessage = new CustNewsMessage();
				custNewsMessage.setMsgtype(custServiceConfig.getMsgType());
				custNewsMessage.setTouser(openId);
				CustNewsMessage.NewsContent newsContent = new CustNewsMessage().new NewsContent();
				newsContent.setArticles(custServiceConfig.getArticles());
				custNewsMessage.setNews(newsContent);
				
				JSONObject jsonObject = JSONObject.fromObject(custNewsMessage);
				sendMessage = jsonObject.toString();
			}
			retMap = wxService.sendCustService(sendMessage);
		}catch(Exception e){
			retMap = null;
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 发送客服消息
	 * @param message：发送的消息内容
	 */
	public Map<String,Object> sendCustServiceMsg(String message){
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			retMap = wxService.sendCustService(message);
		}catch(Exception e){
			retMap.put("resultCode", CommonConstants.ERROR_CODE_1);
			retMap.put("resultMsg", MsgHandleUtil.getExceptionString(e, 400));
		}
		return retMap;
	}
	
	/**
	 * 发送客服消息
	 * @param map：发送的消息内容
	 */
	public Map<String,Object> sendCustServiceMsg(Map<String,Object> map){
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			String message = null;
			if(map != null && map.size() > 0){
				JSONObject jsonObject = JSONObject.fromObject(map);
				message = jsonObject.toString();
			}
			wxService.sendCustService(message);
		}catch(Exception e){
			retMap = null;
			e.printStackTrace();
		}
		return retMap;
	}
}
