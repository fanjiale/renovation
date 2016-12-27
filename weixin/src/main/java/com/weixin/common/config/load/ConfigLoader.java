package com.weixin.common.config.load;

import java.util.List;
import java.util.Map;

import com.weixin.common.model.config.CustServiceConfig;
import com.weixin.common.model.config.MediaFileLocation;
import com.weixin.common.model.config.ReplyConfig;
import com.weixin.common.model.config.MenuConfig;
import com.weixin.common.model.config.ValidateMessageConfig;


/**
 * 配置获取接口
 */
public interface ConfigLoader {

	/**
	 * 获取菜单配置MAP
	 * @return 菜单配置
	 */
	public Map<String,MenuConfig> getMenuConfigMap();
	
	/**
	 * 获取菜单配置List
	 * @return 菜单配置List
	 */
	public List<MenuConfig> getMenuConfigList();
	
	/**
	 * 获取菜单click事件回复配置
	 * @return 回复配置
	 */
	public Map<String,ReplyConfig> getMenuEventReplyConfig();
	
	/**
	 * 获取默认事件回复配置
	 * @return 默认事件回复配置
	 */
	public ReplyConfig getDefaultEventReply();
	
	/**
	 * 获取订阅事件回复配置
	 * @return 订阅事件回复配置
	 */
	public ReplyConfig getSubscribeEventReply();
	
	/**
	 * 重新读取配置并加载缓存
	 * @throws Exception
	 */
	public void reload() throws Exception;
	
	/**
	 * 重新读取菜单
	 * @throws Exception
	 */
	public void reloadMenu() throws Exception;
	
	/**
	 * 获得短信发送配置
	 * @return
	 */
	public ValidateMessageConfig getValidateMessageConfig() ;
	
	/**
	 * 获取客服消息配置
	 * @return
	 */
	public Map<String, CustServiceConfig> getCustMsgConfigMap();
	
	/**
	 * 获取用户text消息回复配置
	 * @return
	 */
	public Map<String,ReplyConfig> getTextMsgReply();
	
	/**
	 * 获取用户默认消息回复配置
	 * @return
	 */
	public ReplyConfig getDefaultMsgReply();
	
	/**
	 * 获得媒体配置文件路径
	 * @return
	 */
	public MediaFileLocation getMediaFileLocation();
}
