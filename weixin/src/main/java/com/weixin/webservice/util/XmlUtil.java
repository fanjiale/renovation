package com.weixin.webservice.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

/**
 * 将 XML中的数据根据XPATH找出来，并且过滤掉。
 * <i>需要对xpath语言较为熟悉才能配置<i>
 * <p>
 * 
 * @author zhangli
 */
public class XmlUtil {
	private static Logger logger = Logger.getLogger(XmlUtil.class);

	/**
	 * 过滤数据
	 * @param str
	 * @return Map 过滤数据
	 */

	private Document document;

	/**
	 * 载入XML
	 * @param xmlStr  字符串
	 * @throws DocumentException  异常
	 */
	public static Document convToxml(String xmlStr) {
		Document doc;
		try {
			doc = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			logger.error(MsgHandleUtil.getExceptionString(e));
			return null;
		}
		return doc;
	}

	public static String convToStr(Document doc) {
		String result = doc.asXML();
		return result;
	}

	/**
	 * 获取某个节点
	 */
	public static Element getElement(Document doc, String node) {
		Element root = doc.getRootElement();
		Element ele = (Element) root.selectSingleNode(node);
		return ele;
	}

	/**
	 * 获取某节点的某个属性
	 */
	public static Attribute getAttribute(Document doc, String node, String attribute) {
		Element root = doc.getRootElement();
		Element ele = (Element) root.selectSingleNode(node);
		return ele.attribute(attribute);
	}

	/**
	 * 获取某节点的某属性值
	 */
	public static String getAttributeValue(Document doc, String node, String attribute) {
		Element root = doc.getRootElement();
		Element ele = (Element) root.selectSingleNode(node);
		String value = ele.attributeValue(attribute);
		return value;
	}

	/**
	 * 根据Xpath得到该参数具体的值. 
	 * <b>注意：<b/><br>
	 * <i>1. 直接通过xpath 来过滤是否得到数据来返回规则true or false  直接 rule = 变量名称
	 * <i>2. 可以通过xpath 来得到具体取值，来得到指定变量的赋值。     用来判断 rule = 变量.equals("常量")；
	 * @param xmlstr  xml 字符串内容
	 * @param xpath   xpath
	 * @return value  通过xpath得到该xml文本中的返回结果
	 * @throws RuleEingineException 规则异常
	 */
	public static Object getValue(String xmlstr, String xpath) throws DocumentException {
		String returnvalue = null;
		Document doc = convToxml(xmlstr);
		Object node = doc.selectObject(xpath);
		if (node == null || node.toString().equals("[]")) {
			//如果xpath无法取道值，则返回false
			return null;
		} else {
			if (node instanceof List) {
				returnvalue = node.toString();
			} else if (node instanceof DefaultText) {
				returnvalue = ((DefaultText) node).getText();
			} else if (node instanceof DefaultElement) {
				returnvalue = ((DefaultElement) node).toString();
			} else if (node instanceof String) {
				returnvalue = node.toString();
			}
			return returnvalue;
		}
	}

	public void printDoc(Document doc) throws IOException {
		Writer out = new OutputStreamWriter(System.out, "gb2312");
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(out, format);
		writer.write(doc);
		out.flush();
	}

	/**
	 * 获取doc中的节点(节点不区分大小写)
	 */
	public static Element trace(Element root, String str) {
		Element result = null;
		if (root.getName().equalsIgnoreCase(str)) {
			result = root;
		} else {
			List l = root.elements();
			for (int i = 0; i < l.size(); i++) {
				Element e = (Element) l.get(i);
				result = trace(e, str);
				if (result != null)
					break;
			}

		}
		return result;
	}

	/**
	 * 将xml格式的字符串转化成JSON对象
	 * @param xmlStr
	 * @return
	 */
	public static JSONObject convertXml2Json(String xmlStr) {
		return convertXml2Json(convToxml(xmlStr));
	}

	/**
	 * 将document对象转化成JSON对象
	 * @param doc
	 * @return
	 */
	public static JSONObject convertXml2Json(Document doc) {
		return JSONObject.fromObject(convertXml2Map(doc));
	}

	/**
	 * 将xml格式的字符串转化成Map对象
	 * @param xmlStr
	 * @return
	 */
	public static Map<String, Object> convertXml2Map(String xmlStr) {
		return convertXml2Map(convToxml(xmlStr));
	}

	/**
	 * 将document对象转化成Map对象
	 * @param doc
	 * @return
	 */
	public static Map<String, Object> convertXml2Map(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null) {
			return map;
		}
		Node node = doc.getRootElement();
		if (node.selectNodes("./*").size() == 0) {
			map.put(node.getName(), node.getText());
		} else {
			map.put(node.getName(), convertNode2Map(node));
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> convertNode2Map(Node node) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Node> children = node.selectNodes("./*");
		if (children.size() > 0) {
			for (Iterator<Node> it = children.iterator(); it.hasNext();) {

				Node child = it.next();
				if (child.selectNodes("./*").size() > 0) {
					Map<String, Object> tmpMap = convertNode2Map(child);
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					if (map.containsKey(child.getName())) {
						Object obj = map.get(child.getName());
						if (!(obj instanceof List)) {
							list.add((Map<String, Object>) obj);
							list.add(tmpMap);
							map.put(child.getName(), list);
						} else {
							((List<Map<String, Object>>) obj).add(tmpMap);
						}
					} else {
						list.add(tmpMap);
						map.put(child.getName(), list);
					}
				} else {
					List<String> list = new ArrayList<String>();
					if (map.containsKey(child.getName())) {
						Object obj = map.get(child.getName());
						if (!(obj instanceof List)) {

							list.add((String) obj);
							list.add(child.getText());
							map.put(child.getName(), list);
						} else {
							((List<String>) obj).add(child.getText());
						}
					} else {
						list.add(child.getText());
						map.put(child.getName(), list);
					}
				}
			}
		} else {
			map.put(node.getName(), node.getText());
		}
		return map;
	}

}
