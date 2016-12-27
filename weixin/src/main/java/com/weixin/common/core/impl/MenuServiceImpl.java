package com.weixin.common.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.core.MenuService;
import com.weixin.common.core.WxService;
import com.weixin.common.model.config.MenuConfig;
import com.weixin.common.model.menu.CommonButton;
import com.weixin.common.model.menu.ComplexButton;
import com.weixin.common.model.menu.Menu;

/**
 * 菜单注册实现类
 */
@Component("menuService")
public class MenuServiceImpl implements MenuService{

	private static List<MenuConfig> menuList;
	private static Menu menu;

	@Autowired
	private ConfigLoader configLoader;

	@Autowired
	private WxService wxService;

	/**
	 * 更新菜单
	 * @return
	 * @throws Exception
	 */
	public int refresh() throws Exception {
		initBaseInfo();
		initMenuList();
		buildMenu();
		return wxService.createMenu(menu);
	}

	/**
	 * 构造菜单对象
	 */
	private  void buildMenu() {
		List<ComplexButton> complexButtonList = new ArrayList<ComplexButton>();
		for (MenuConfig menu : menuList) {
			ComplexButton mainBtn1 = new ComplexButton();
			mainBtn1.setName(menu.getName());
			List<MenuConfig> subList = menu.getSub();
			List<CommonButton> commonButtonList = new ArrayList<CommonButton>();
			for (MenuConfig menuConfig : subList) {
				CommonButton subButton = new CommonButton();
				subButton.setName(menuConfig.getName());
				subButton.setType(menuConfig.getType());
				if("view".equalsIgnoreCase(menuConfig.getType())){
					subButton.setUrl(menuConfig.getUrl());
				}else{
					subButton.setKey(menuConfig.getKey());
				}
				commonButtonList.add(subButton);
			}
			CommonButton[] cb = new CommonButton[commonButtonList.size()];
			for (int i = 0; i < commonButtonList.size(); i++) {
				cb[i] = commonButtonList.get(i);
			}
			mainBtn1.setSub_button(cb);
			complexButtonList.add(mainBtn1);
		}
		ComplexButton[] cxb = new ComplexButton[complexButtonList.size()];
		for (int i = 0; i < complexButtonList.size(); i++) {
			cxb[i] = complexButtonList.get(i);
		}
		menu = new Menu();
		menu.setButton(cxb);
	}

	/**
	 * 读取菜单xml配置
	 * @throws Exception
	 */
	private void initMenuList() throws Exception {
		configLoader.reloadMenu();
		menuList = configLoader.getMenuConfigList();
	}

	/**
	 * 获取AccessToken配置
	 * @throws Exception
	 */
	private void initBaseInfo() throws Exception{
	}
	
}
