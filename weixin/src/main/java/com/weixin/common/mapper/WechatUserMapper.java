package com.weixin.common.mapper;


import com.weixin.common.model.user.WechatUser;


public interface WechatUserMapper {
	
	/**
	 * 保存微信用户基本信息 
	 * @param wechatUser：用户基础信息
	 * @author XMZ
	 */
	void insertWechatUser(WechatUser wechatUser);
	
	/**
	 * 更新微信用户基本信息 
	 * @param wechatUser：用户基础信息
	 * @author XMZ
	 */
	void updateWechatUser(WechatUser wechatUser);
	
	/**
	 * 查询微信用户
	 * @param openId
	 * @return String
	 */
	WechatUser queryWechatUserByOpenId(String openId);

	/**
	 * 查询微信用户数量
	 * @param openId
	 * @return String
	 */
	int queryWechatUserCountByOpenId(String openId);
	
}
