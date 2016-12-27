package com.weixin.common.core.impl;

import java.util.Map;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.core.AsyncReplyManage;
import com.weixin.common.core.CustServiceMsg;
import com.weixin.common.mapper.MessageLogMapper;
import com.weixin.common.model.config.ReplyConfig;
import com.weixin.common.model.log.MessageLog;
import com.weixin.common.model.message.CustTextMessage;
import com.weixin.common.model.request.UserRequest;
import com.weixin.common.thread.AsyncReplyQueue;
import com.weixin.common.util.CommonConstants;
import com.weixin.common.util.HttpUtils;
import com.weixin.common.util.MsgHandleUtil;


/**
 * 异步消息回复处理实现类
 * @author XMZ
 */

@Component("asyncReplyManage")
public class AsyncReplyManageImpl implements AsyncReplyManage{

	private static Logger log = LoggerFactory.getLogger(AsyncReplyManageImpl.class);

	@Autowired
	private AsyncReplyQueue asyncReplyQueue;
	@Autowired
	private ConfigLoader configLoader;
	@Autowired
	private CustServiceMsg custServiceMsg;
	@Autowired
	private MessageLogMapper messageLogMapper;
	
	/**
	 * 处理异步消息
	 * @param userRequest ： 用户请求消息对象
	 */
	public void dealAsyncMessage(UserRequest userRequest){
		boolean flag = AsyncReplyQueue.add(userRequest);
		if(flag){
			log.info("请求消息存储成功，openId为" + userRequest.getFromUserName());
		}else{
			log.info("请求消息存储失败，openId为" + userRequest.getFromUserName());
		}
	}
	
	/**
	 * 从队列中读取消息处理并发送
	 * @param userRequest：用户请求消息对象
	 */
	public void sendAsyncMessage(UserRequest userRequest){
		//获取消息中的内容
		String content = userRequest.getRequestMap().get("Content");
		String openId = userRequest.getFromUserName();
		try{
			//获取缓存中的配置文件信息
			Map<String,ReplyConfig> configMap = configLoader.getTextMsgReply();
			//根据消息中的内容为key，获取ReplyConfig对象
			ReplyConfig replyConfig = configMap.get(content);
			//根据ReplyConfig对象中的信息，处理请求消息，并组装回复内容
			String retMsg = callRealizationMethod(userRequest, replyConfig);
			
			//调用客服接口发送
			Map<String,Object> retMap = custServiceMsg.sendCustServiceMsg(retMsg);
			if(retMap != null){
				String resultCode = (String) retMap.get("resultCode");
				if(resultCode.equals("0")){
					log.info("调用客服接口成功，发送消息到openId为" + userRequest.getFromUserName() + "的用户");
				}else{
					MessageLog messageLog = new MessageLog();
					//封装错误日志内容
					String logId = messageLogMapper.getMessageLogId();
					messageLog.setLogId(logId);
					messageLog.setObjectId(openId);
					messageLog.setObjectType(CommonConstants.OBJECT_TYPE_1);
					messageLog.setReqContent(content);
					messageLog.setResultCode(resultCode);
					messageLog.setResultDesc(retMap.get("resultMsg").toString());
					messageLog.setLogType(CommonConstants.LOG_TYPE_1);
					messageLogMapper.insertMessageLog(messageLog);
					log.info("调用客服接口失败，未发送消息到openId为" + userRequest.getFromUserName() + "的用户");
				}
			}
		}catch(Exception e){
			MessageLog messageLog = new MessageLog();
			//封装错误日志内容
			String logId = messageLogMapper.getMessageLogId();
			messageLog.setLogId(logId);
			messageLog.setObjectId(openId);
			messageLog.setObjectType(CommonConstants.OBJECT_TYPE_1);
			messageLog.setReqContent(content);
			messageLog.setResultCode(CommonConstants.ERROR_CODE_1);
			messageLog.setResultDesc(MsgHandleUtil.getExceptionString(e, 400));
			messageLog.setLogType(CommonConstants.LOG_TYPE_1);
			messageLogMapper.insertMessageLog(messageLog);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 调用处理的方法，节点未定待开发，暂且模拟数据返回
	 * @param userRequest：用户请求对象
	 * @param replyConfig：处理地址
	 * @return
	 */
	private String callRealizationMethod(UserRequest userRequest, ReplyConfig replyConfig){
		String retMsg = null;
		try{
			String url = replyConfig.getAsyncUrl();
			if(url != null && !url.equals("")){
				String requestJson = JSONObject.toJSONString(userRequest);
				JSONObject jsonObject = HttpUtils.httpRequest(url, "POST", requestJson);
				// 如果请求成功
				if (null != jsonObject) {
					
				}
			}
			
			CustTextMessage custTextMessage = new CustTextMessage();
			custTextMessage.setMsgtype(userRequest.getMsgType());
			custTextMessage.setTouser(userRequest.getFromUserName());
			CustTextMessage.TextContent textContent = new CustTextMessage().new TextContent();
			textContent.setContent(replyConfig.getMsgContent());
			custTextMessage.setText(textContent);

			retMsg = JSONObject.toJSONString(custTextMessage);
		}catch(Exception e){
			e.printStackTrace();
		}
		return retMsg;
	}
}

