package com.weixin.common.config.load.xml;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.model.config.BaseConfig;
import com.weixin.common.model.config.CustServiceConfig;
import com.weixin.common.model.config.MediaFileLocation;
import com.weixin.common.model.config.ReplyConfig;
import com.weixin.common.model.config.MenuConfig;
import com.weixin.common.model.config.ValidateMessageConfig;
/**
 * XML格式配置读取/封装类
 *
 */
@Component("configLoader")
public class XMLConfigLoader implements ConfigLoader {
	private static Logger log = LoggerFactory.getLogger(XMLConfigLoader.class);
	private static BaseConfig baseConfig;
	private List<MenuConfig> menuConfigList;
	private Map<String,MenuConfig> menuConfigMap;
	private ReplyConfig defaultEventReply;
	private ReplyConfig subscribeEventReply;
	private Map<String,ReplyConfig> menuEventReply;
	private ValidateMessageConfig validateMessageConfig;
	private Map<String,CustServiceConfig> custMsgConfigMap;
	private Map<String,ReplyConfig> textMsgReply;
	private ReplyConfig defaultMsgReply;
	private static MediaFileLocation mediaFileLocation;

	/**
	 * 构造方法
	 * @throws Exception
	 */
	XMLConfigLoader() throws Exception{
		log.error("读取配置");
		load();
	}
	
	private void load() throws Exception {
		XMLMenuConfigLoader menuConfigLoader = new XMLMenuConfigLoader();
		menuConfigList = menuConfigLoader.getMenuConfigList();
		menuConfigMap = menuConfigLoader.getMenuConfigMap();
		XMLEventConfigLoader eventConfigLoader = new XMLEventConfigLoader();
		subscribeEventReply = eventConfigLoader.getSubscribeEventConfig();
		menuEventReply = eventConfigLoader.getMenuEventMap();
		XMLWeiXinConfigLoader weiXinConfigLoader = new XMLWeiXinConfigLoader();
		baseConfig = weiXinConfigLoader.getBaseConfig();
		validateMessageConfig = weiXinConfigLoader.getValidateMessageConfig();
		custMsgConfigMap = weiXinConfigLoader.getCustMsgConfigMap();
		mediaFileLocation = weiXinConfigLoader.getMediaFileLocation();
		XMLMsgReplyConfigLoader replyConfigLoader = new XMLMsgReplyConfigLoader();
		textMsgReply = replyConfigLoader.getTextReply();
		defaultEventReply = replyConfigLoader.getDefalutMsgReply();
		defaultMsgReply = replyConfigLoader.getDefalutMsgReply();
	}

	/**
	 * 重新加载配置
	 */
	public void reload() throws Exception{
		log.error("reload config");
		load();
	}
	
	public void reloadMenu() throws Exception{
		XMLMenuConfigLoader menuConfigLoader = new XMLMenuConfigLoader();
		menuConfigList = menuConfigLoader.getMenuConfigList();
		menuConfigMap = menuConfigLoader.getMenuConfigMap();
	}
	
	public static BaseConfig getBaseConfig() {
		return baseConfig;
	}

	public static MediaFileLocation getMediaFileLocationStatic(){
		return mediaFileLocation;
	}
	
	@Override
	public Map<String, MenuConfig> getMenuConfigMap() {
		return menuConfigMap;
	}

	@Override
	public Map<String, ReplyConfig> getMenuEventReplyConfig() {
		return menuEventReply;
	}


	@Override
	public List<MenuConfig> getMenuConfigList() {
		return menuConfigList;
	}

	@Override
	public ReplyConfig getDefaultEventReply() {
		return defaultEventReply;
	}

	@Override
	public ReplyConfig getSubscribeEventReply() {
		return subscribeEventReply;
	}

	@Override
	public ValidateMessageConfig getValidateMessageConfig() {
		return validateMessageConfig;
	}

	@Override
	public Map<String, CustServiceConfig> getCustMsgConfigMap() {
		return custMsgConfigMap;
	}

	@Override
	public Map<String, ReplyConfig> getTextMsgReply() {
		return textMsgReply;
	}

	@Override
	public ReplyConfig getDefaultMsgReply() {
		return defaultMsgReply;
	}

	@Override
	public MediaFileLocation getMediaFileLocation() {
		return mediaFileLocation;
	}
	
}
