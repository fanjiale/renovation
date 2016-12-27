package com.weixin.common.util;

/**
 * 常量定义
 * @author XMZ
 */
public class CommonConstants {
	
	//错误编码
	public static final String Error_40029_Code = "40029";
	public static final String Error_40029_Msg = "用户访问会话失效";
	public static final String Error_Other_Code = "other";
	public static final String Error_Other_Msg = "其他错误";
	public static final String Error_Param_Code = "40001";
	public static final String Error_Param_Msg = "参数错误";

	
	//工程URL配置
	public static final String USER_BOUND_CHECK_SERVLET = "viewController/getClickAddrAction";

	
	//用户绑定界面地址
	public static final String USER_BOUND_URL = "restaurant/userBound/user-bound.html";
	
	//Session属性
	public static final String SESSION_ATTR_ACCNBR = "accnbr";
	public static final String SESSION_ATTR_OPENID = "openid";
	
	//ACCESS_TOKEN类型
	public static final int ACCESS_TOKEN_TYPE_NORMAL = 1;
	//应用授权作用域
	public static String OAUTH_SCOPE_NOPAGE = "snsapi_base";//不弹出授权页面
	public static String OAUTH_SCOPE_PAGE = "snsapi_userinfo";//弹出授权页面
	
	//用户消息回复
	public static String MSG_REPLY_DEFAULT = "success";
	
	public static String MSG_REPLY_SYNC = "sync";
	
	public static String MSG_REPLY_ASYNC = "async";
	
	public static String PARAM_REPLACE_OPENID = "{openid}";
	
	public static String PASSWORD_ENC = "01234567";
	
	public static String SEND_MODE_HTTP = "HTTP";
	
	public static String SEND_MODE_WEBSERVICE = "WEBSERVICE";
	
	//错误编码
	public static final String ERROR_CODE_1 = "1"; // 其他类型错误
	public static final String ERROR_MESSAGE_1 = "其他类型错误";
	
	//对象类型
	public static final int OBJECT_TYPE_1 = 1; //微信用户
	
	//日志类型
	public static final int LOG_TYPE_1 = 1; //异步消息类型
	
	//群发消息
	public static final String MASS_STATUS_GEN = "M";
	public static final String MASS_STATUS_UPLOAD = "U";
	public static final String MASS_STATUS_SEND = "S";
	public static final String MASS_STATUS_UPLOADERROR = "E";
	public static final String MASS_STATUS_SENDFAIL = "F";
	public static final String MASS_STATUS_PREVIEW = "P";
	public static final String MASS_STATUS_C = "C";
	
	public static final String LOCAL_PATH ="F:/"; //上传的服务器路径
	
	
	public static final String ERR_CODE= "errCode";
	public static final String ERR_MSG = "errMsg";
	
	public static final int MSG_TYPE_TEXT = 1;
	public static final int MSG_TYPE_NEWS = 2;
	public static final int MSG_TYPE_IMAGE = 3;
	public static final int MSG_TYPE_MUSIC = 4;
	public static final int MSG_TYPE_VIDEO = 5;
	public static final int MSG_TYPE_VOICE = 6;
	
	//关注状态
	public static final Integer SUBSCRIBE_STATUS = 1; //已关注
	public static final Integer UNSUBSCRIBE_STATUS = 0; //取消关注
	
	//验证消息类型
	public static final int VALIDATEMSG_UAM = 0; //UAM接口
	public static final int VALIDATEMSG_PLAT = 1; //平台接口
	
	public static final String EXTRACT_SUCESS = "0"; //执行结果，成功为0
	public static final String EXTRACT_FAIL = "1"; //执行结果，失败为1
}
