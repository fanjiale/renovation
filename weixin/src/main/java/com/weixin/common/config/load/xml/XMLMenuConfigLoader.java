package com.weixin.common.config.load.xml;

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

import com.weixin.common.model.config.MenuConfig;
import com.weixin.common.util.DataUtil;

/**
 * 菜单配置读取类
 *
 */
public class XMLMenuConfigLoader {

	public List<MenuConfig> menuConfigList;
	
	public Map<String,MenuConfig> menuConfigMap;
	
	XMLMenuConfigLoader() throws Exception{
		menuConfigList = new ArrayList<MenuConfig>();
		menuConfigMap = new HashMap<String, MenuConfig>();
		this.load();
	}

	/**
	 * 读取menu.xml加载缓存
	 * @throws Exception
	 */
	private void load() throws Exception {
		 InputStream xml = null;
	        Document doc = null;
	        SAXReader reader = new SAXReader();
	        try {
	        	InputStream input = null;
	        	input = XMLMenuConfigLoader.class.getClassLoader().getResourceAsStream("config/menu.xml");
	        	doc = reader.read(input);
	            loadMenu(doc);
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
	private void loadMenu(Document doc) {
		Element root = doc.getRootElement();
		List<Node> menus = root.selectNodes("Menu");
		if(menus != null && menus.size()>0){
			for (Node menu : menus) {
				MenuConfig menuConfig = new MenuConfig();
				String key = DataUtil.getNodeValue(menu.selectSingleNode("./Key"));
				String name = DataUtil.getNodeValue(menu.selectSingleNode("./Name"));
				String type = DataUtil.getNodeValue(menu.selectSingleNode("./Type"));
				String url = DataUtil.getNodeValue(menu.selectSingleNode("./Url"));
				String isOAuthPage= DataUtil.getNodeValue(menu.selectSingleNode("./IsOAuthPage"));
				
				menuConfig.setKey(key);
				menuConfig.setName(name);
				menuConfig.setType(type);
				if(url != null && !url.equals("") && (url.indexOf("uesrAccessController") != -1)){
					url = url + "&key=" + key;
				}
				menuConfig.setUrl(url);
				menuConfig.setIsOAuthPage(isOAuthPage);
				
				List<MenuConfig> subMenuList = new ArrayList<MenuConfig>();
				menuConfig.setSub(subMenuList);
				Node subMenu = menu.selectSingleNode("./MenuSubList");
				menuConfigMap.put(key, menuConfig);
				if(subMenu != null){
					List<Node> subList = subMenu.selectNodes("./MenuSub");
					if(subList != null && subList.size()>0){
						for (Node sub : subList) {
							MenuConfig smenuConfig = new MenuConfig();
							String skey = DataUtil.getNodeValue(sub.selectSingleNode("./Key"));
							String sname = DataUtil.getNodeValue(sub.selectSingleNode("./Name"));
							String stype = DataUtil.getNodeValue(sub.selectSingleNode("./Type"));
							String surl = DataUtil.getNodeValue(sub.selectSingleNode("./Url"));
							String sisOAuthPage= DataUtil.getNodeValue(sub.selectSingleNode("./IsOAuthPage"));
							
							smenuConfig.setKey(skey);
							smenuConfig.setName(sname);
							smenuConfig.setType(stype);
							if(surl != null && !surl.equals("") && (surl.indexOf("uesrAccessController") != -1)){
								surl = surl + "&key=" + key;
							}
							smenuConfig.setUrl(surl);
							smenuConfig.setIsOAuthPage(sisOAuthPage);
							
							subMenuList.add(smenuConfig);
							menuConfigMap.put(skey, smenuConfig);
						}
					}
				}
				menuConfigList.add(menuConfig);
			}
		}
	}

	List<MenuConfig> getMenuConfigList() {
		return menuConfigList;
	}
	
	Map<String, MenuConfig> getMenuConfigMap() {
		return menuConfigMap;
	}
	
	
}
