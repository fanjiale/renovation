package com.weixin.common.core;

import java.io.File;
import java.util.Map;


import com.alibaba.fastjson.JSONObject;
import com.weixin.common.model.menu.Menu;
import com.weixin.common.model.token.AccessToken;

public interface WxService {

	/**
	 * 获取AccessToken
	 * @return AccessToken
	 */
	public String getAccessToken();

	/**
	 * 获取AccessToken
	 * @param appid
	 * @param appsecret
	 * @return AccessToken
	 */
	public AccessToken getAccessToken(String appid, String appsecret);
	
	/**
	 * 使用code换取AccessToken
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getAccessTokenByCode(String appID, String appsecret, String code);
	
	/**
	 * 调用客服接口
	 * @param accessToken
	 * @param msgInfo
	 */
	public Map<String,Object> sendCustService(String msgInfo);
	
	/**
	 * 获取二维码
	 * @param accessToken
	 * @param jsonMsg
	 * @return JSONObject
	 */
	public JSONObject getQr(String jsonMsg);
	
	/**
	 * 用ticket获取二维码
	 * @param ticket
	 * @return boolean
	 */
	public boolean chageQr(String ticket);
	
	/**
	 * 创建菜单 
	 * @param menu
	 * @param accessToken
	 * @return int
	 */
	public int createMenu(Menu menu);
	
	/**
	 * 根据分组发送群发消息
	 * @param request
	 * @param accessToken
	 * @return
	 */
	public JSONObject sendMsgByGroupId(String request);
	
	
	/**
	 * 根据openid列表发送群消息
	 * @param request
	 * @param accessToken
	 * @return
	 */
	public JSONObject sendMsgByOpenids(String request);
	
	/**
	 * 上传图文消息
	 * @param request
	 * @param accessToken
	 * @return
	 */
	public JSONObject uploadNews(String request);
	
	/**
     * 微信服务器素材上传
     * @param file  表单名称media
     * @param token access_token
     * @param type  type只支持四种类型素材(video/image/voice/thumb)
     */
    public JSONObject uploadMedia(File file, String type);
    
    /**
     * 预览群发消息
     * @param openid 关注着id
     * @param mediaId 媒体id
     * @param type 类型
     * @param accessToken
     * @return
     */
    public JSONObject previewMass(String openid,String mediaId,String type,String content);
    
    /**
     * 获得群发消息状态
     * @param msgId 消息id
     * @param accessToken
     * @return
     */
    public JSONObject getMassSendStatus(String msgId);
    
    /**
     * 删除群发消息
     * @param msgId
     * @return
     */
    public JSONObject deleteMass(String msgId);
    
    /**
     * 删除素材（永久）
     * @param mediaId
     * @return
     */
    public JSONObject deleteMaterial(String mediaId);
    
}
