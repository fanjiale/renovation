package com.weixin.webservice.util;

/**
 * 常量定义
 * @author XMZ
 * @date 2015-6-12 
 */
public class CommonConstants {

	// 访问 外部WebService服务超时时间（毫秒）
	public static final long CALL_WEBSERVICE_TIME_OUT = 10000;
	
	//微信平台编码
	public static final String WEIXIN_CODE = "703";
	
	public static final String EXTRACT_SUCESS = "0"; //执行结果，成功为0
	public static final String EXTRACT_FAIL = "1"; //执行结果，失败为1
	//UAM验证码获取校验接口
	public static final String ACTION_GENERATE = "generate"; //UAM接口生成验证码标志
	public static final String ACTION_CHECK = "check"; //UAM接口校验验证码标志
	public static final String VSOP_CODE = "11067"; //UAM对应的VSOP系统编码,11 是uam定义的江苏省份编码,067 是vsop业务平台编码
	public static final String ACTION_CODE = "0"; //请求会话控制，值为0   
	public static final String SERVICE_CODE = "JAP05001"; //UAM接口协议编码 
	public static final String ACCOUNT_TYPE = "2000004"; //账号类型   2000004：手机（产品类型）
	public static final String DSTSYSID = "11"; //落地方平台编码就是江苏uam平台编码
	public static final String WEAGENT_CODE = "702"; //翼桶金平台编码
	public static final String SYS_CODE = "BSS";
	public static final String SYS_PWD = "Ued9px1S8we1vVZMhbtBqUP/HBcSIN29";
	
	//Session属性
	public static final String SESSION_ATTR_ACCNBR = "accnbr";
	public static final String SESSION_ATTR_OPENID = "openid";
	//错误编码
	public static final String Error_Other_Code = "other";
	public static final String Error_Other_Msg = "其他错误";
	
}
