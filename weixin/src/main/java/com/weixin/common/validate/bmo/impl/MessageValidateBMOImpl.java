package com.weixin.common.validate.bmo.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.mapper.MessageValidateMapper;
import com.weixin.common.model.validate.ValidateMessage;
import com.weixin.common.util.DataUtil;
import com.weixin.common.validate.bmo.MessageValidateBMO;
import com.weixin.common.validate.send.MessageSend;
import com.weixin.webservice.util.CallWebServiceUtil;
import com.weixin.webservice.util.CommonConstants;
import com.weixin.webservice.util.MsgHandleUtil;
@Component("messageValidateBMO")
public class MessageValidateBMOImpl implements MessageValidateBMO {
	private static Logger log = Logger.getLogger(MessageValidateBMOImpl.class);
	private CallWebServiceUtil callWebServiceUtil = new CallWebServiceUtil();
	
	@Autowired
	ConfigLoader configLoader;
	@Autowired
	MessageValidateMapper messageValidateMapper;
	
	public ConfigLoader getConfigLoader() {
		return configLoader;
	}

	public void setConfigLoader(ConfigLoader configLoader) {
		this.configLoader = configLoader;
	}

	@Override
	public Map<String, Object> getVercodeByAccNbr(String openId, String accNbr) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String resultCode = CommonConstants.EXTRACT_SUCESS;
		String resultMsg = "获取验证码成功";
		try{
			boolean send = true;
			if(configLoader.getValidateMessageConfig() != null
					&& configLoader.getValidateMessageConfig().getLimit() != 0
					&& configLoader.getValidateMessageConfig().getLimitTime() != 0){
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("openId", openId);
				paramMap.put("limitTime", configLoader.getValidateMessageConfig().getLimitTime()/1440.0);
				Long c = messageValidateMapper.countOpenIdValidateTimes(paramMap);
				if(c > configLoader.getValidateMessageConfig().getLimit()){
					resultCode = CommonConstants.EXTRACT_FAIL;
					resultMsg = "您获取校验码过于频繁，请"+ 
					configLoader.getValidateMessageConfig().getLimitTime()
						+"分钟后再试";
					send = false;
				}
			}
			
			if(send){
				String validateCode = DataUtil.generateValidateCode();
				String msgSendResult = null;
				if(com.weixin.common.util.CommonConstants.SEND_MODE_HTTP
						.equalsIgnoreCase(configLoader.getValidateMessageConfig().getSendMode())){
					MessageSend.sendMsgHttp(validateCode, accNbr, configLoader.getValidateMessageConfig().getUrl());
				}else{
					MessageSend.sendMsgWebservice(validateCode, accNbr,configLoader.getValidateMessageConfig().getUrl());
				}
				
				if(CommonConstants.EXTRACT_SUCESS.equals(msgSendResult)){
					ValidateMessage vm = new ValidateMessage();
					vm.setPhone_num(accNbr);
					vm.setOpen_id(openId);
					vm.setVer_code(validateCode);
					int expTime = configLoader.getValidateMessageConfig()==null?0:configLoader.getValidateMessageConfig().getExpTime();
					if(expTime <=0 ){
						vm.setExpires_in(1440);
					}else{
						vm.setExpires_in(expTime);
					}
					messageValidateMapper.insertValidateMessage(vm);
					retMap.put("msgId", vm.getId());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			resultCode = CommonConstants.EXTRACT_FAIL;
			resultMsg = "获取验证码失败";
		}
		
		retMap.put("resultCode", resultCode);
		retMap.put("resultMsg", resultMsg);		
		
		return retMap;
	}

	@Override
	public Map<String,Object> checkVercode(String openId,String accNbr,Long msgId ,String vCode) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String resultCode = CommonConstants.EXTRACT_SUCESS;
		String resultMsg = "二次检验成功";
		try {
			ValidateMessage vm = new ValidateMessage();
			vm.setPhone_num(accNbr);
			vm.setOpen_id(openId);
			vm.setId(msgId);
			vm.setVer_code(vCode);
			ValidateMessage s = messageValidateMapper.queryValidateMessage(vm);
			if(s == null || s.getId() <= 0){
				resultCode = CommonConstants.EXTRACT_FAIL;
				resultMsg = "二次检验失败";
			}
		} catch (Exception e) {
			log.error("-----------二次检验异常： " + MsgHandleUtil.getExceptionString(e) + "-----------");
			resultCode = CommonConstants.EXTRACT_FAIL;
			resultMsg = "二次检验失败";
		}
		retMap.put("resultCode", resultCode);
		retMap.put("resultMsg", resultMsg);		
		return retMap;
	}
	
	/**
	 * 调用UAM获取验证码  
	 * @param accNbr：用户号码
	 * @return Map
	 */
	public Map<String,Object> getVercodeFromUAM(String accNbr){

		Map<String, Object> retMap = new HashMap<String, Object>();
		String resultCode = CommonConstants.EXTRACT_SUCESS;
		String resultMsg = "获取验证码成功";
		try {
			StringBuffer vercodeInfo = new StringBuffer();
			String reqTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String verTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String transactionSeq = messageValidateMapper.getUamTransactionSeq();
			
			if(resultCode.equals(CommonConstants.EXTRACT_SUCESS)){
				//拼接验证码获取的报文
				vercodeInfo.append("<CAPRoot>");
				vercodeInfo.append("<SessionHeader>");
				vercodeInfo.append("<ServiceCode>" + CommonConstants.SERVICE_CODE + "</ServiceCode>");
				vercodeInfo.append("<Version>" + CommonConstants.SERVICE_CODE + verTime + "</Version>");
				vercodeInfo.append("<ActionCode>" + CommonConstants.ACTION_CODE + "</ActionCode>");
				vercodeInfo.append("<TransactionID>" + CommonConstants.VSOP_CODE + transactionSeq + "</TransactionID>");
				vercodeInfo.append("<SrcSysID>" + CommonConstants.VSOP_CODE + "</SrcSysID>");
				vercodeInfo.append("<DstSysID>" + CommonConstants.DSTSYSID + "</DstSysID>");
				vercodeInfo.append("<ReqTime>" + reqTime + "</ReqTime>");
				vercodeInfo.append("</SessionHeader>");
				vercodeInfo.append("<SessionBody>");
				vercodeInfo.append("<Action>" + CommonConstants.ACTION_GENERATE + "</Action>");
				vercodeInfo.append("<AccountID>" + accNbr + "</AccountID>");
				vercodeInfo.append("<AccountType>" + CommonConstants.ACCOUNT_TYPE + "</AccountType>");
				vercodeInfo.append("<InputRandomPwd></InputRandomPwd>");
				vercodeInfo.append("</SessionBody>");
				vercodeInfo.append("</CAPRoot>");
				
				String serviceId = "UAM_checkVerCode";
				log.debug("验证码获取请求 === " + vercodeInfo.toString());
				String retXml = callWebServiceUtil.callWebService(serviceId, vercodeInfo.toString());
				log.debug("验证码获取响应 === " + retXml);
				 
				Document doc = DocumentHelper.parseText(retXml);
				Node parentNode = doc.selectSingleNode("CAPRoot");
				Element headElement = (Element) parentNode.selectNodes("./SessionHeader").get(0);	
				resultCode = headElement.selectSingleNode("./RspCode").getText(); 
				if(resultCode.equals("0000")){
					resultCode = CommonConstants.EXTRACT_SUCESS;
				}else{
					resultCode = CommonConstants.EXTRACT_FAIL;
				}
				resultMsg =  headElement.selectSingleNode("./RspDesc").getText();	
			}
		} catch (Exception e) {
			log.error("-----------获取验证码异常： " + MsgHandleUtil.getExceptionString(e) + "-----------");
			resultCode = CommonConstants.EXTRACT_FAIL;
			resultMsg = "获取验证码失败";
		}
		retMap.put("resultCode", resultCode);
		retMap.put("resultMsg", resultMsg);		
		return retMap;
	}

	/**
	 * 调用UAM二次校验  
	 * @param accNbr：用户号码
	 * @param verCode：验证码
	 * @return Map
	 */
	public Map<String,Object> checkVercodeFromUAM(String accNbr, String verCode){

		Map<String, Object> retMap = new HashMap<String, Object>();
		String resultCode = CommonConstants.EXTRACT_SUCESS;
		String resultMsg = "二次检验成功";
		try {
			StringBuffer vercodeInfo = new StringBuffer();
			String reqTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String verTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String transactionSeq = messageValidateMapper.getUamTransactionSeq();
			
			if(resultCode.equals(CommonConstants.EXTRACT_SUCESS)){
				vercodeInfo.append("<CAPRoot>");
				vercodeInfo.append("<SessionHeader>");
				vercodeInfo.append("<ServiceCode>" + CommonConstants.SERVICE_CODE + "</ServiceCode>");
				vercodeInfo.append("<Version>" + CommonConstants.SERVICE_CODE + verTime + "</Version>");
				vercodeInfo.append("<ActionCode>" + CommonConstants.ACTION_CODE + "</ActionCode>");
				vercodeInfo.append("<TransactionID>" + CommonConstants.VSOP_CODE + transactionSeq + "</TransactionID>");
				vercodeInfo.append("<SrcSysID>" + CommonConstants.VSOP_CODE + "</SrcSysID>");
				vercodeInfo.append("<DstSysID>" + CommonConstants.DSTSYSID + "</DstSysID>");
				vercodeInfo.append("<ReqTime>" + reqTime + "</ReqTime>");
				vercodeInfo.append("</SessionHeader>");
				vercodeInfo.append("<SessionBody>");
				vercodeInfo.append("<Action>" + CommonConstants.ACTION_CHECK + "</Action>");
				vercodeInfo.append("<AccountID>" + accNbr + "</AccountID>");
				vercodeInfo.append("<AccountType>" + CommonConstants.ACCOUNT_TYPE + "</AccountType>");
				vercodeInfo.append("<InputRandomPwd>" + verCode + "</InputRandomPwd>");
				vercodeInfo.append("</SessionBody>");
				vercodeInfo.append("</CAPRoot>");
				
				String serviceId = "UAM_checkVerCode";
				log.debug("二次检验请求 === " + vercodeInfo.toString());
				String retXml = callWebServiceUtil.callWebService(serviceId, vercodeInfo.toString());
				log.debug("二次检验响应 === " + retXml);
				 
				Document doc = DocumentHelper.parseText(retXml);
				Node parentNode = doc.selectSingleNode("CAPRoot");
				Element headElement = (Element) parentNode.selectNodes("./SessionHeader").get(0);	
				resultCode = headElement.selectSingleNode("./RspCode").getText(); 
				if(resultCode.equals("0000")){
					resultCode = CommonConstants.EXTRACT_SUCESS;
				}else{
					resultCode = CommonConstants.EXTRACT_FAIL;
				}
				resultMsg =  headElement.selectSingleNode("./RspDesc").getText();
			}
		} catch (Exception e) {
			log.error("-----------二次检验异常： " + MsgHandleUtil.getExceptionString(e) + "-----------");
			resultCode = CommonConstants.EXTRACT_FAIL;
			resultMsg = "二次检验失败";
		}
		retMap.put("resultCode", resultCode);
		retMap.put("resultMsg", resultMsg);		
		return retMap;
	}
}
