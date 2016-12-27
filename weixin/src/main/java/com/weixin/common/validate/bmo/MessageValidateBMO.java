package com.weixin.common.validate.bmo;

import java.util.Map;



public interface MessageValidateBMO {
	/**
	 * 获取验证码（自行生成调用短信接口）  
	 * @param opendId
	 * @param accNbr
	 * @return
	 */
	public Map<String, Object> getVercodeByAccNbr(String openId, String accNbr);
	
	/**
	 * 二次校验  (自行校验)
	 * @param accNbr：用户号码
	 * @param verCode：验证码
	 * @return Map
	 */
	public Map<String,Object> checkVercode(String opendId,String accNbr,Long msgId ,String verCode);
	
	/**
	 * 调用UAM获取验证码  
	 * @param accNbr：用户号码
	 * @return Map
	 */
	public Map<String,Object> getVercodeFromUAM(String accNbr);
	
	/**
	 * 调用UAM二次校验  
	 * @param accNbr：用户号码
	 * @param verCode：验证码
	 * @return Map
	 */
	public Map<String,Object> checkVercodeFromUAM(String accNbr, String verCode);
}
