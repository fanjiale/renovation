package com.weixin.common.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.weixin.common.model.request.UserRequest;
import com.weixin.common.model.respone.Article;
import com.weixin.common.model.respone.MusicOutputMessage;
import com.weixin.common.model.respone.NewsOutputMessage;
import com.weixin.common.model.respone.TextOutputMessage;

/**
 * 消息工具类
 * 
 * @author fukai
 * @date 2015-06-01
 */
public class MessageUtil {
	// 回复消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// 回复消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// 回复消息类型：图文
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	// 回复消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// 回复消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// 回复消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// 请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	// 请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	// 请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	// 请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	// 请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	// 请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	// 请求消息类型：小视频
	public static final String REQ_MESSAGE_TYPE_SVIDEO = "shortvideo";
	// 请求消息类型：推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：SCAN(已关注用户扫描带参二维码)
	public static final String EVENT_TYPE_SCAN = "SCAN";
	// 事件类型：LOCATION(上报地理事件)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(自定义菜单点击事件)
	public static final String EVENT_TYPE_CLICK = "CLICK";
	// 事件类型：VIEW(自定义菜单点击事件)
	public static final String EVENT_TYPE_VIEW = "VIEW";

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	public static UserRequest parseRequest(HttpServletRequest request) throws Exception {
		// xml请求解析
		Map<String, String> requestMap = MessageUtil.parseXml(request);
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 微信公众帐号
		String toUserName = requestMap.get("ToUserName");
		// 消息类型
		String msgType = requestMap.get("MsgType");
		// 消息ID
		String msgId = requestMap.get("MsgId");
		// 消息时间
		String createTime = requestMap.get("CreateTime");
		// 请求地址
		String requestUrl = request.getRequestURL().toString();
		requestUrl = requestUrl.replace(request.getRequestURI(), "") + request.getContextPath();
		UserRequest ur = new UserRequest();
		ur.setFromUserName(fromUserName);
		ur.setToUserName(toUserName);
		ur.setMsgType(msgType);
		ur.setRequestUrl(requestUrl);
		ur.setMsgId(msgId);
		ur.setCreateTime(createTime);
		ur.setRequestMap(requestMap);
		return ur;
	}
	
	// 文本消息对象转换成xml
	public static String textMessageToXml(TextOutputMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	// 音乐消息对象转换成xml
	public static String musicMessageToXml(MusicOutputMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	// 图文消息对象转换成xml
	public static String newsMessageToXml(NewsOutputMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	// 扩展xstream，使其支持CDATA块
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	// emoji表情转换(hex -> utf-16)
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}
