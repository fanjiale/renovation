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
 * 用户消息回复读取类
 * @author H
 *
 */
public class XMLMsgReplyConfigLoader {

	private ReplyConfig defalutMsgReply;
	private Map<String,ReplyConfig> textReplyMap;
	
	XMLMsgReplyConfigLoader() throws Exception{
		defalutMsgReply = new ReplyConfig();
		textReplyMap = new HashMap<String, ReplyConfig>();
		this.load();
	}

	ReplyConfig getDefalutMsgReply(){
		return defalutMsgReply;
	}
	
	Map<String,ReplyConfig> getTextReply(){
		return textReplyMap;
	}

	/**
	 * 读取msgReply.xml加载缓存
	 * @throws Exception
	 */
	private void load() throws Exception {
		InputStream xml = null;
        Document doc = null;
        SAXReader reader = new SAXReader();
        try {
        	InputStream input = null;
        	input = XMLMenuConfigLoader.class.getClassLoader().getResourceAsStream("config/msgReply.xml");
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

	@SuppressWarnings("unchecked")
	private void loadEvent(Document doc) {
		Element root = doc.getRootElement();
		if(root != null){
			loadDefault(root);
		}
		if(root != null && root.selectNodes("./ReqTextContent") != null){
			loadTextReply(root.selectNodes("./ReqTextContent"));
		}
	}

	@SuppressWarnings("unchecked")
	private void loadTextReply(List<Node> textNodes) {
		if(textNodes != null && textNodes.size() > 0){
			for (Node node : textNodes) {
				ReplyConfig textReply = new ReplyConfig();
				String replyMode = DataUtil.getNodeValue(node.selectSingleNode("./ReplyMode"));
				String respMsgType = DataUtil.getNodeValue(node.selectSingleNode("./RespMsgType"));
				String msgContent = DataUtil.getNodeValue(node.selectSingleNode("./MsgContent"));
				String reqContentValue = DataUtil.getNodeValue(node.selectSingleNode("./ReqContentValue"));
				String asyncUrl = DataUtil.getNodeValue(node.selectSingleNode("./AsyncUrl"));
				List<Node> articles = node.selectNodes("./Articles/item");
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
						art.setUrl(url);
						articleList.add(art);
					}
				}
				textReply.setArticles(articleList);
				textReply.setMsgContent(msgContent);
				textReply.setReplyMode(replyMode);
				textReply.setMsgType(respMsgType);
				textReply.setAsyncUrl(asyncUrl);
				textReplyMap.put(reqContentValue, textReply);
			}
		}
		
	}


	@SuppressWarnings("unchecked")
	private void loadDefault(Element root) {
		String defaultRespMsgContent = DataUtil.getNodeValue(root.selectSingleNode("./DefaultRespMsgContent"));
		String defaultRespMsgType = DataUtil.getNodeValue(root.selectSingleNode("./DefaultRespMsgType"));
		defalutMsgReply.setMsgContent(defaultRespMsgContent);
		defalutMsgReply.setMsgType(defaultRespMsgType);
		
		List<Node> articles = root.selectNodes("./DefaultRespArticles/Articles/item");
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
				art.setUrl(url);
				articleList.add(art);
			}
		}
		defalutMsgReply.setArticles(articleList);
	}
}
