package com.weixin.common.config.load.xml;

import java.io.File;
import java.io.FileInputStream;
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

import com.weixin.common.model.config.ReplyConfig;
import com.weixin.common.model.respone.Article;
import com.weixin.common.util.DataUtil;

/**
 * 事件配置读取类
 * @author H
 *
 */
public class XMLEventConfigLoader {

	private ReplyConfig subscribeEventConfig;
	
	private List<ReplyConfig> menuEventConfig;
	
	private Map<String ,ReplyConfig> menuEventMap;
	
	XMLEventConfigLoader() throws Exception{
		menuEventConfig = new ArrayList<ReplyConfig>();
		menuEventMap = new HashMap<String, ReplyConfig>();
		this.load();
	}


	ReplyConfig getSubscribeEventConfig() {
		return subscribeEventConfig;
	}

	List<ReplyConfig> getMenuEventConfig() {
		return menuEventConfig;
	}

	Map<String, ReplyConfig> getMenuEventMap() {
		return menuEventMap;
	}

	/**
	 * 读取event.xml加载缓存
	 * @throws Exception
	 */
	private void load() throws Exception {
		InputStream xml = null;
        Document doc = null;
        SAXReader reader = new SAXReader();
        try {
        	InputStream input = null;
        	input = XMLMenuConfigLoader.class.getClassLoader().getResourceAsStream("config/event.xml");
        	doc = reader.read(input);
            loadEvent(doc);
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

	private void loadEvent(Document doc) {
		Element root = doc.getRootElement();
		Node subscirbeNode = root.selectSingleNode("./SubscribeEvent");
		if(subscirbeNode != null){
			this.subscribeEventConfig = loadEvent(subscirbeNode);
		}
		loadMenuEvent(root);
	}

	@SuppressWarnings("unchecked")
	private void loadMenuEvent(Element root) {
		List<Node> menuEventList = root.selectNodes("./MenuEventList/MenuEvent");
		if(menuEventList != null && menuEventList.size() > 0){
			for (Node menuEvent : menuEventList) {
				ReplyConfig e = loadEvent(menuEvent);
				menuEventMap.put(e.getKey(), e);
				menuEventConfig.add(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private ReplyConfig loadEvent(Node eventNode) {
		String key = DataUtil.getNodeValue(eventNode.selectSingleNode("./Key"));
		String eventType = DataUtil.getNodeValue(eventNode.selectSingleNode("./EventType"));
		String msgType = DataUtil.getNodeValue(eventNode.selectSingleNode("./MsgType"));
		String msgContent = DataUtil.getNodeValue(eventNode.selectSingleNode("./MsgContent"));
		List<Node> articles = eventNode.selectNodes("./Articles/item");
		List<Article> articleList = new ArrayList<Article>();
		if(articles != null && articles.size() > 0){
			for (Node item : articles) {
				String title = DataUtil.getNodeValue(item.selectSingleNode("./Title"));
				String description = DataUtil.getNodeValue(item.selectSingleNode("./Description"));
				String picUrl = DataUtil.getNodeValue(item.selectSingleNode("./PicUrl"));
				String url = DataUtil.getNodeValue(item.selectSingleNode("./Url"));
				Article art =new Article();
				art.setDescription(description);
				art.setPicUrl(picUrl);
				art.setTitle(title);
				if(url != null && !url.equals("") && key != null && !key.equals("") && (url.indexOf("uesrAccessController") != -1)){
					url = url + "&key=" + key;
				}
				art.setUrl(url);
				articleList.add(art);
			}
		}
		ReplyConfig eConfig = new ReplyConfig();
		eConfig.setKey(key);
		eConfig.setMsgType(msgType);
		eConfig.setEventType(eventType);
		eConfig.setMsgContent(msgContent);
		eConfig.setArticles(articleList);
		return eConfig;
	}
}
