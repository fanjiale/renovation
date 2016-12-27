package com.weixin.common.config.load.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.weixin.common.model.config.BaseConfig;
import com.weixin.common.model.config.CustServiceConfig;
import com.weixin.common.model.config.MediaFileLocation;
import com.weixin.common.model.config.ValidateMessageConfig;
import com.weixin.common.model.message.CustArticle;
import com.weixin.common.util.CommonConstants;
import com.weixin.common.util.DataUtil;
import com.weixin.util.AES;

/**
 * 基础配置读取类
 */
public class XMLWeiXinConfigLoader {

	private BaseConfig baseConfig;
	private ValidateMessageConfig validateMessageConfig;
	private Map<String,CustServiceConfig> custMsgConfigMap;
	private MediaFileLocation mediaFilelocation;
	
	MediaFileLocation getMediaFileLocation() {
		return mediaFilelocation;
	}

	BaseConfig getBaseConfig() {
		return baseConfig;
	}

	ValidateMessageConfig getValidateMessageConfig() {
		return validateMessageConfig;
	}
	
	Map<String, CustServiceConfig> getCustMsgConfigMap() {
		return custMsgConfigMap;
	}

	XMLWeiXinConfigLoader() throws Exception{
		custMsgConfigMap = new HashMap<String, CustServiceConfig>();
		this.load();
	}

	/**
	 * 读取base.xml配置加载缓存
	 * @throws Exception
	 */
	private void load() throws Exception {
        InputStream xml = null;
        Document doc = null;
        SAXReader reader = new SAXReader();
        try {
        	InputStream input = null;
        	input = XMLMenuConfigLoader.class.getClassLoader().getResourceAsStream("config/weixinconfig.xml");
        	doc = reader.read(input);
        	loadBaseConfig(doc);
        	loadValidateConfig(doc);
            loadCustMsg(doc);
            loadFileLocations(doc);
        } catch (Throwable e) {
            throw new Exception(e);
        } finally {
            if (xml != null) {
                try {
                    xml.close();
                } catch (IOException e) {
                }
            }
        }
		
	}

	private void loadFileLocations(Document doc) {
		Element root = doc.getRootElement();
		Node locationNode = root.selectSingleNode("./MediaFileLocation");
		String newsHtmlFilePath = DataUtil.getNodeValue(locationNode.selectSingleNode("./NewsLocation/Html"));
		String newsImageFilePath = DataUtil.getNodeValue(locationNode.selectSingleNode("./NewsLocation/Image"));
		String imageFilePath = DataUtil.getNodeValue(locationNode.selectSingleNode("./ImageLocation"));
		String videoFilePath = DataUtil.getNodeValue(locationNode.selectSingleNode("./VideoLocation"));
		String musicFilePath = DataUtil.getNodeValue(locationNode.selectSingleNode("./MusicLocation"));
		MediaFileLocation location = new MediaFileLocation();
		location.setImage(imageFilePath);
		location.setMusic(musicFilePath);
		location.setNewsHtml(newsHtmlFilePath);
		location.setNewsImage(newsImageFilePath);
		location.setVideo(videoFilePath);
		this.mediaFilelocation = location;
	}

	/**
	 * 加载基础配置
	 */
	private void loadBaseConfig(Document doc) throws Exception {
		Element root = doc.getRootElement();
		Node baseNode = root.selectSingleNode("./Base");
		String taken = DataUtil.getNodeValue(baseNode.selectSingleNode("./Taken"));
		String appid = DataUtil.getNodeValue(baseNode.selectSingleNode("./AppID"));
		String secret = DataUtil.getNodeValue(baseNode.selectSingleNode("./Secret"));
		BaseConfig baseConfig = new BaseConfig();
		System.out.println("taken值：" + taken);		
		
		baseConfig.setTaken(taken);
		baseConfig.setAppid(appid);
		baseConfig.setSecret(secret);
		this.baseConfig = baseConfig;
	}
	
	/**
	 * 加载验证信息配置 
	 */
	private void loadValidateConfig(Document doc) throws Exception {
		Element root = doc.getRootElement();
		Node vmNode = root.selectSingleNode("./ValidateMessage");
		String type = DataUtil.getNodeValue(vmNode.selectSingleNode("./Type"));
		String expTime = DataUtil.getNodeValue(vmNode.selectSingleNode("./ExpTime"));
		String limit  = DataUtil.getNodeValue(vmNode.selectSingleNode("./Limit"));
		String limitTime  = DataUtil.getNodeValue(vmNode.selectSingleNode("./LimitTime"));
		String sendMode  = DataUtil.getNodeValue(vmNode.selectSingleNode("./SendMode"));
		String url  = DataUtil.getNodeValue(vmNode.selectSingleNode("./URL"));
		try{
			int typeInt = Integer.parseInt(type);
			int expTimeInt = Integer.parseInt(expTime);
			int limitInt = Integer.parseInt(limit);
			int limitTimeInt = Integer.parseInt(limitTime);
			ValidateMessageConfig vmc = new ValidateMessageConfig();
			vmc.setExpTime(expTimeInt);
			vmc.setLimit(limitInt);
			vmc.setLimitTime(limitTimeInt);
			vmc.setSendMode(sendMode);
			vmc.setUrl(url);
			vmc.setType(typeInt);
			this.validateMessageConfig = vmc;
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 加载客服消息配置
	 */
	@SuppressWarnings("unchecked")
	void loadCustMsg(Document doc){
		Element root = doc.getRootElement();
		List<Node> custMsgList = root.selectNodes("./CustServiceMessage/CustMsgList/CustMsg");
		try{
			if(custMsgList != null && custMsgList.size()>0){
				CustServiceConfig custServiceConfig = null;
				for (Node custMsg : custMsgList) {
					custServiceConfig = new CustServiceConfig();
					String key = DataUtil.getNodeValue(custMsg.selectSingleNode("./Key"));
					String msgType = DataUtil.getNodeValue(custMsg.selectSingleNode("./MsgType"));
					String msgContent = DataUtil.getNodeValue(custMsg.selectSingleNode("./MsgContent"));
					List<Node> articles = custMsg.selectNodes("./Articles/item");
					List<CustArticle> articleList = new ArrayList<CustArticle>();
					if(articles != null && articles.size() > 0){
						for (Node item : articles) {
							String title = DataUtil.getNodeValue(item.selectSingleNode("./Title"));
							String description = DataUtil.getNodeValue(item.selectSingleNode("./Description"));
							String picUrl = DataUtil.getNodeValue(item.selectSingleNode("./PicUrl"));
							String url = DataUtil.getNodeValue(item.selectSingleNode("./Url"));
							CustArticle art =new CustArticle();
							art.setDescription(description);
							art.setPicurl(picUrl);
							art.setTitle(title);
							art.setUrl(url);
							articleList.add(art);
						}
					}
					custServiceConfig.setKey(key);
					custServiceConfig.setMsgType(msgType);
					custServiceConfig.setMsgContent(msgContent);
					custServiceConfig.setArticles(articleList);
					custMsgConfigMap.put(key, custServiceConfig);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
