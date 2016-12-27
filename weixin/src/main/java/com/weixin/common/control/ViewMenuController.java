package com.weixin.common.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.config.load.xml.XMLConfigLoader;
import com.weixin.common.core.WxService;
import com.weixin.common.model.config.BaseConfig;
import com.weixin.common.model.config.MenuConfig;
import com.weixin.common.model.token.AccessTokenCode;
import com.weixin.common.user.smo.UserBoundSMO;
import com.weixin.common.util.CommonConstants;
import com.weixin.util.AES;

@Controller
@RequestMapping("viewController")
public class ViewMenuController {
	
	@Autowired
	private UserBoundSMO userBoundSMO;
	@Autowired
	private WxService wxService;
	@Autowired
	private ConfigLoader configLoader;

	/**
	 * View菜单控制器
	 */
	@RequestMapping(value = "getViewAddrAction")
	public void getViewController(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		 String basePath = request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
		 BaseConfig baseConfig = XMLConfigLoader.getBaseConfig();
		 String appID = baseConfig.getAppid();
		 String appsecret = baseConfig.getSecret();
		
		 //code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
		 String code = request.getParameter("code"); 
		 //重定向地址
		 String redirectUrl = request.getParameter("url");
		 try{
			 Map<String,Object> map = wxService.getAccessTokenByCode(appID, appsecret, code);
			 String resultCode = (String) map.get("resultCode");
			 //成功
			 if(resultCode.equals("0")){
				 AccessTokenCode accessTokenCode = (AccessTokenCode) map.get("resultMsg");
				 String openId = accessTokenCode.getOpenId();
				//openId不为空时，才做地址跳转
				if(getString(openId)){
					 String retUrl = getRedirectUrl(openId, redirectUrl, basePath,request);
					 if(retUrl != null){
						 request.getSession().setAttribute(CommonConstants.SESSION_ATTR_OPENID, openId);
						 response.setContentType("text/html; charset=UTF-8");
						 response.sendRedirect(retUrl);
					 }else{
						 throw new Exception("跳转地址获取失败");
					 }
				}
			 }else{
				if(getString(resultCode)){
					response.setContentType("text/html; charset=UTF-8");
					response.sendRedirect( basePath + "common/jsp/Error_Code.jsp?errcode=" + resultCode);
				}
			}
		 }catch(Exception e){
			e.printStackTrace();
			response.setContentType("text/html; charset=UTF-8");
			response.sendRedirect( basePath + "common/jsp/Error_Code.jsp?errcode=" + CommonConstants.Error_Other_Code);
		 }
	}
	
	/**
	 * Click菜单控制器
	 */
	@RequestMapping(value = "getClickAddrAction")
	public void getClickController(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String basePath = request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
		String openId  = request.getParameter("openid");
		String redirectUrl = request.getParameter("requrl");
		try{
			if(getString(openId)){
				 String retUrl = getRedirectUrl(openId, redirectUrl, basePath,request);
				 if(retUrl != null){
					 response.setContentType("text/html; charset=UTF-8");
					 response.sendRedirect(retUrl);
				 }else{
					 throw new Exception("跳转地址获取失败");
				 }
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setContentType("text/html; charset=UTF-8");
			response.sendRedirect( basePath + "common/jsp/Error_Code.jsp?errcode=" + CommonConstants.Error_Other_Code);
		 }
	}
	
	
	
	/**
	 * 用户访问控制器
	 * @throws Exception 
	 */
	@RequestMapping(value = "uesrAccessController")
	public void uesrAccessController(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String basePath = request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
		HttpSession hs = request.getSession();
		String paramOpenid = request.getParameter(CommonConstants.SESSION_ATTR_OPENID);
		AES aes = new AES(CommonConstants.PASSWORD_ENC);
		if(paramOpenid != null){
			hs.setAttribute(CommonConstants.SESSION_ATTR_OPENID, 
					paramOpenid);
		}
		if(hs.getAttribute(CommonConstants.SESSION_ATTR_OPENID) == null){
			
			String requestUrl = null;
			String key = request.getParameter("key");
			String isOAuthPage = null;
			String oAuthValue = null;
			
			if(key != null && !key.equals("")){
				//检索一级菜单的key，如果有匹配的值，isKey属性为true
				List<MenuConfig> menuList = configLoader.getMenuConfigList();
				for(MenuConfig menuConfig: menuList){
					String tempKey = menuConfig.getKey();
					if(key.equals(tempKey)){
						isOAuthPage = menuConfig.getIsOAuthPage();
						break;
					}
				}
				//如果一级菜单没有匹配的，检索二级菜单
				if(isOAuthPage == null){
					Map<String, MenuConfig> menuMap = configLoader.getMenuConfigMap();
					MenuConfig menuConfig = menuMap.get(key);
					isOAuthPage = menuConfig.getIsOAuthPage();
				}
			}
			
			if(isOAuthPage != null && isOAuthPage.equals("Y")){
				//弹出授权页面
				oAuthValue = CommonConstants.OAUTH_SCOPE_PAGE;
			}else{
				//不弹出授权页面
				oAuthValue = CommonConstants.OAUTH_SCOPE_NOPAGE;
			}

			String redirect_uri = basePath + "viewController/getViewAddrAction?url=" + request.getParameter("url");
			requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+XMLConfigLoader.getBaseConfig().getAppid()
					+ "&redirect_uri=" + redirect_uri + "&response_type=code&scope=" + oAuthValue + "&state=1#wechat_redirect";
			response.sendRedirect(requestUrl);
		}else{
			if(request.getParameter("url") != null){
				response.setContentType("text/html; charset=UTF-8");
				response.sendRedirect(basePath + request.getParameter("url"));
			}else{
				throw new Exception("跳转地址获取失败");
			}
		}
	}

	/**
	 * 跳转地址 
	 * @param openId 
	 * @param redirectUrl : 跳转地址
	 * @param basePath : 跟路径
	 * @author XMZ
	 */
	private String getRedirectUrl(String openId, String redirectUrl, String basePath,HttpServletRequest request){
		String retUrl = null;
		try{
			//根据openId查询用户是否已绑定
			HttpSession hs = request.getSession();
			String accNbr =  hs.getAttribute(CommonConstants.SESSION_ATTR_ACCNBR)==null?null:hs.getAttribute(CommonConstants.SESSION_ATTR_ACCNBR).toString();
			if(accNbr == null || accNbr.length()==0){
				accNbr = userBoundSMO.queryUserBoundPhoneNum(openId);
				if(accNbr != null && accNbr.length() > 0){
					hs.setAttribute(CommonConstants.SESSION_ATTR_ACCNBR, accNbr);
				}
			}
			if(getString(accNbr)){
				//如果用户已绑定，直接跳转到功能页面
				if(redirectUrl.equals(CommonConstants.USER_BOUND_URL)){
					retUrl = basePath + CommonConstants.USER_BOUND_URL ;
				}else{
					retUrl = basePath + redirectUrl ;
				}
			}else{
				//如果未绑定，跳转到手机绑定页面
				if(redirectUrl.equals(CommonConstants.USER_BOUND_URL)){
					retUrl = basePath + CommonConstants.USER_BOUND_URL ;
				}else{
					retUrl = basePath + CommonConstants.USER_BOUND_URL + "?url=" + redirectUrl;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			retUrl = null;
		}
		return retUrl;
	}
	
	/**
	 * String非空判断 
	 * @param str
	 * @return boolean
	 */
	private static boolean getString(String str){
		if(str == null || str.equals("")){
			return false;
		}else{
			return true;
		}
	}
}
