package com.weixin.webservice.util;

import java.io.InputStream;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.client.Stub;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.weixin.util.ConfigFileManage;
import com.weixin.webservice.client.UaServiceStub;
import com.weixin.webservice.client.UaServiceStub.DynamicPWDResponse;


/**
 * CallWebServiceUtil 工具类
 * @author XMZ
 * @date 2015-6-12 
 */
public class CallWebServiceUtil {
	private static Logger logger = Logger.getLogger(CallWebServiceUtil.class);

	/*读取配置文件的数据
	 *入参 xmlname 配置xml文件名称
	 *返回 配置xml文件内容 
	 */
	public Document qryConfig(String xmlname) throws Exception {
		Document document = null;
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(xmlname);
		SAXReader reader = new SAXReader();
		document = reader.read(in);
		in.close();
		return document;
	}

	/*读取节点的数据
	 *入参 xmlname 配置xml文件名称,nodepath 
	 *返回 配置xml文件内容 
	 */
	public Node qryNode(String xmlname, String nodepath) throws Exception {
		//XML文件内容
		Document anRuleXml = qryConfig(xmlname);
		//节点内容
		Node node = anRuleXml.selectSingleNode(nodepath);
		return node;
	}

	//公用的调用WebService服务方法
	public String callWebService(String serviceId, String sendMessage) throws Exception {
		try {
			String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			if(serviceId.equals("UAM_checkVerCode")){
				sendMessage = xmlString + sendMessage.replaceAll("<\\?.*\\?>", "");
			}else{
				sendMessage = xmlString + "<Request><SessionBody>" + sendMessage.replaceAll("<\\?.*\\?>", "") + "</SessionBody></Request>";
			}
			String url = ConfigFileManage.getConfigValue(serviceId);
			logger.debug("--------调用WebService服务，url:" + url + ",serviceId:" + serviceId);
			logger.debug("--------入参报文信息:" + sendMessage);
			String resutlXml = null;
			if(serviceId.equals("UAM_checkVerCode")){
				//UAM验证码获取检验
				resutlXml = callCheckVercodeToUAM(sendMessage, url);
			}
			if (resutlXml == null) {
				throw new Exception("未找到对应的WebService,serviceId=" + serviceId);
			}
			logger.debug("--------调用WebService成功,返回报文信息:" + resutlXml);
			resutlXml = resutlXml.replaceAll("<Response>", "");
			resutlXml = resutlXml.replaceAll("</Response>", "");
			resutlXml = resutlXml.replaceAll("<\\?.*\\?>", "");
			return resutlXml;
		} catch (Exception e) {
			logger.error("----------------------调用WebService服务失败，serviceId=" + serviceId);
			throw new Exception(e);
		}
	}

	
	//UAM验证码获取检验接口
	private String callCheckVercodeToUAM(String sendMessage, String url) throws Exception {
		UaServiceStub stub = new UaServiceStub(url);
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(CommonConstants.CALL_WEBSERVICE_TIME_OUT);
		UaServiceStub.DynamicPWD dynamicPWD = new UaServiceStub.DynamicPWD();
		dynamicPWD.setRequest(sendMessage);
		DynamicPWDResponse dynamicPWDResponse = stub.dynamicPWD(dynamicPWD);
		return dynamicPWDResponse.getResponse();
	}

	//封装包头
	public static Stub csbHeader(String SRC_SYS_ID, String SRC_AUTH_PWD, String areaCode, Stub stub) {
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMElement omSecurityElement1 = omFactory.createOMElement(new QName("", "SRC_SYS_ID"), null);
		omSecurityElement1.setText(SRC_SYS_ID);
		stub._getServiceClient().addHeader(omSecurityElement1);
		OMElement omSecurityElement2 = omFactory.createOMElement(new QName("", "SRC_AUTH_PWD"), null);
		omSecurityElement2.setText(SRC_AUTH_PWD);
		stub._getServiceClient().addHeader(omSecurityElement2);
		OMElement omSecurityElement3 = omFactory.createOMElement(new QName("", "areacode"), null);
		omSecurityElement3.setText(areaCode);
		stub._getServiceClient().addHeader(omSecurityElement3);
		return stub;
	}
}