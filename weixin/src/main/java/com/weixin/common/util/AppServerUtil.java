package com.weixin.common.util;

/**
 * 
 */

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


/**
 * @author linrq
 *
 */
public class AppServerUtil {	
	private static Logger logger = LoggerFactory.getLogger(AppServerUtil.class);
	public static final String WEBSPHERE_SERVER_ATTRIBUTE_NAME = "com.ibm.websphere.servlet.application.host";
	public static final String DEFAULT_APP_SERVER_INSTANCE_NAME = "defaultAppServer";
	public static final String TOMCAT_SERVER_INSTANCE_NAME = "tomcat";
	public static final String OS_LINUX = "linux";
	public static final String OS_WINDOWS = "windows";
	public static final String OS_UNKNOW = "unknow";
	
	private static String appServerName = null;
	private static int appServerHttpPort = -1;
	private static String webAppContextPath = null;
	private static String hostName = null;
	private static byte[] lockGetPort = new byte[0];  
	private static Map<String, String> cacheForLocalIPs = new ConcurrentHashMap<String, String>();
	
	/*
	//提前获取主机名、JVM实例名备用。
	static{
		getAppServerInstanceName();
		getHostName();
	}*/
	
	public static boolean isWebSphere() {		
        return AppServerUtil.class.getClassLoader().getClass().getName().startsWith("com.ibm");
    }
	
	public static boolean isTomcat() {	
		return AppServerUtil.class.getClassLoader().getClass().getName().startsWith("org.apache.catalina");
	}
	
	public static boolean isWeblogic() {	
		return AppServerUtil.class.getClassLoader().getClass().getName().startsWith("weblogic");
	}
	
	public static boolean isJetty(){
		String home = System.getProperty("jetty.home");
		return (home!=null);		
	}
	
	/**
	 * 在应用（如在Servlet或Listener）启动时，设置appServerName
	 * @param servletContext
	 */
	public static void setAppServerInstanceName(ServletContext servletContext){			
		if(isWebSphere()){
			appServerName = (String)servletContext.getAttribute(WEBSPHERE_SERVER_ATTRIBUTE_NAME);
			System.out.println("WAS appServerName:" + appServerName);
		}else if(isWeblogic()){
			appServerName = System.getProperty("weblogic.Name");
		}else if(isTomcat()){
			appServerName = TOMCAT_SERVER_INSTANCE_NAME;
		}
		else appServerName = DEFAULT_APP_SERVER_INSTANCE_NAME;		
	}

	
	
	/**
	 * @return the appServerName
	 * @throws Exception 
	 */
	public static String getAppServerInstanceName() throws Exception {
		if(appServerName == null || DEFAULT_APP_SERVER_INSTANCE_NAME.equals(appServerName)){
			if(isWeblogic()){
				appServerName = System.getProperty("weblogic.Name");
			}else if(isTomcat()){
				appServerName = TOMCAT_SERVER_INSTANCE_NAME;
			}else if(isWebSphere()){
				throw new Exception("Can not get WebSphere App Server name!!!");
			}else if(appServerName == null) appServerName = DEFAULT_APP_SERVER_INSTANCE_NAME;	
		}
		
		return appServerName;
	}
	
	public static String getHostName() throws Exception {
		if(hostName != null){
			return hostName;
		}
		
		InetAddress ia = null;		
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {		
			e.printStackTrace();
			throw new Exception("Can not get host name!", e);
		} 
		
		if (ia == null ) {
			return null;
		} else{
			hostName = ia.getHostName();
			return hostName;
		}
	}
	
	public static String getHostIP() {
		InetAddress ia = null;
		
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {		
			e.printStackTrace();
		} 
		
		if (ia == null ) {
			return null;
		} else return ia.getHostAddress();
	}
	
	public static List<String> getHostAllIP(){
		List<String> ipList = new LinkedList<String>();
		try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();	            
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface nif = netInterfaces.nextElement();
                Enumeration<InetAddress> iparray = nif.getInetAddresses();
                while (iparray.hasMoreElements()) {
                    String ip = iparray.nextElement().getHostAddress();
                    if(ip.indexOf(':') < 0 && !ip.equals("127.0.0.1")){
                    	ipList.add(ip);
                    }
                }
            }           
        } catch (Exception e) {
            logger.error("Can' get local host ip!", e);
        }
        
        if(ipList.isEmpty()){
        	ipList.add(getHostIP());
        }
        
        return ipList;
	}
	
	public static String getHostIPs(){		
		return getHostIPs(",");
	}
	
	public static String getHostIPs(String separator){	
		if(cacheForLocalIPs.containsKey(separator)){
			return cacheForLocalIPs.get(separator);
		}
		
		String res = _getHostIPs(separator);
		
		return res;
	}
	
	private synchronized static String _getHostIPs(String separator){
		if(cacheForLocalIPs.containsKey(separator)){
			return cacheForLocalIPs.get(separator);
		}
		
		List<String> ipList = getHostAllIP();
		Assert.notNull(ipList);
		Assert.notEmpty(ipList);
		
		Collections.sort(ipList);
		
		String res = null;
		if(ipList.size() == 1){
			res = ipList.get(0);
		}else{
			StringBuilder sb = new StringBuilder();
			sb.append(ipList.get(0));
			for(int i=1; i<ipList.size(); i++){
				sb.append(separator);
				sb.append(ipList.get(i));
			}
			res = sb.toString();
		}	
		
		cacheForLocalIPs.put(separator, res);
		return res;
	}
	
	/**
	 * 获取当前JVM的进程ID。
	 * @return
	 */
	public static int getPID(){
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();  
        String info = runtime.getName();  
        logger.debug("runtime.getName() : {}", info);
        
        final int index = info.indexOf("@");  
        if (index != -1) {  
            return Integer.parseInt(info.substring(0, index));
        }else{
        	return Integer.parseInt(info.trim());
        }
	}
	
	public static String getOSName(){
		String osName = System.getProperty("os.name");
		if(osName == null){
			return OS_LINUX;
		}
		if(osName.toLowerCase().indexOf(OS_LINUX) != -1){
			return OS_LINUX;
		}else if(osName.toLowerCase().indexOf(OS_WINDOWS) != -1){
			return OS_WINDOWS;
		}else{
			return OS_UNKNOW;
		}
	}
	
	/*
	public static List<String> getListenPortsByPIDInWindows(String pid){
		//在windows下根据进程ID查找端口命令
		String cmd = "cmd /c netstat -nao|findstr LISTENING|findstr " + pid;
		String returnValue = execCommandInWindows(cmd);
		List<String> ports = new ArrayList<String>();
		if(returnValue != null){
			String[] lines = returnValue.split("\n");
			for(String line : lines){				
				String[] arr = line.trim().split(" ");
				if("tcp".equals(arr[0].toLowerCase())){
					ports.add(arr[arr.length-1]);
				}
			}
			System.out.println("get ports :" + ports);
			return ports;
		}else{
			return null;
		}
	}
	
	public static String execCommandInWindows(String cmd){
		Process pro = null;
		InputStream in = null;
		try {
			pro = Runtime.getRuntime().exec(cmd);	
			//等待命令执行完成。
			System.out.println("exec cmd:" + cmd + " ...");
			pro.waitFor();		
			in = pro.getInputStream();  
            return StringUtil.getString(in, "UTF-8");
		} catch (InterruptedException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Can't exec cmd:{}", cmd, e);
		} finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
			if(pro != null){
				pro.destroy();
			}
			
			System.out.println("end exec cmd...");
		}
		
		return null;
	}*/
	
	public static int getHttpPort() throws Exception{
		if(appServerHttpPort > 0){
			return appServerHttpPort;
		}else{		
			synchronized(lockGetPort){
				if(appServerHttpPort > 0){
					return appServerHttpPort;
				}
				
				if(isWebSphere()){
					try {
						appServerHttpPort = getWASHttpPort(getAppServerInstanceName());
					} catch (DocumentException e) {
						throw new Exception("Can' read WAS serverindex.xml file!!!");
					}
				}else if(isTomcat()){
					try {
						appServerHttpPort = getTomcatHttpPort();
					} catch (DocumentException e) {
						throw new Exception("Can' read Tomcat server.xml file!!!");
					}
				}else if(isJetty()){
					try {
						appServerHttpPort = getJettyHttpPort();
					} catch (DocumentException e) {
						throw new Exception("Can' read Tomcat server.xml file!!!");
					}
				}else{
					throw new Exception("Unknow J2EE Application Server!");
				}
				
				return appServerHttpPort;
			}
		}
	}
	
	public static int getWASHttpPort(String serverName) throws DocumentException{
		String serverRoot = System.getProperty("server.root");
		File serverRootDir = new File(serverRoot);
		Collection<File> col = FileUtils.listFiles(serverRootDir, new NameFileFilter("serverindex.xml"), DirectoryFileFilter.DIRECTORY);
		for(Object fileObj : col){
			File file = (File)fileObj;
			logger.debug("getServerPortFromWASConfigFile file={}", file.getAbsolutePath());
			String port = getServerPortFromWASConfigFile(serverName, file);
			if(port != null){
				return Integer.parseInt(port);
			}
		}
		
		return -1;
	}
	
	private static Document getDocument(File file){
		SAXReader reader = new SAXReader();  
		Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	@SuppressWarnings("unchecked")
	private static String getServerPortFromWASConfigFile(String server, File file) throws DocumentException{
		Document doc = getDocument(file);
		Element root = doc.getRootElement();	
		List<Element> serverEntries = root.elements("serverEntries");
		for(Element serverEle : serverEntries){
			String serverType = serverEle.attributeValue("serverType");
			String serverName = serverEle.attributeValue("serverName");
			if("APPLICATION_SERVER".equals(serverType) && server.equals(serverName)){
				List<Element> specialEndpoints = serverEle.elements("specialEndpoints");
				for(Element specialEndpoint : specialEndpoints){
					String endPointName = specialEndpoint.attributeValue("endPointName");
					if("WC_defaulthost".equals(endPointName)){
						List<Element> endpoints = specialEndpoint.elements("endPoint");
						return endpoints.get(0).attributeValue("port");
					}
				}
			}
		}
		
		return null;
	}
	
	public static int getTomcatHttpPort() throws DocumentException{
		String baseRoot = System.getProperty("catalina.base");
		File baseRootDir = new File(baseRoot);
		Collection<File> col = FileUtils.listFiles(baseRootDir, new NameFileFilter("server.xml"), DirectoryFileFilter.DIRECTORY);
		for(Object fileObj : col){
			File file = (File)fileObj;
			String port = getServerPortFromTomcatConfigFile(file);
			if(port != null){
				return Integer.parseInt(port);
			}
		}
		
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	private static String getServerPortFromTomcatConfigFile(File file) throws DocumentException{
		Document doc = getDocument(file);
		Element root = doc.getRootElement();
		List<Element> connectors = root.element("Service").elements("Connector");
		for(Element connector : connectors){
			String protocol = connector.attributeValue("protocol");
			if(protocol != null && protocol.toLowerCase().startsWith("http")){
				return connector.attributeValue("port");
			}
		}
		
		return null;
	}
	
	public static int getJettyHttpPort() throws DocumentException{
		String home = System.getProperty("jetty.home");
		File homeDir = new File(home);
		Collection<File> col = FileUtils.listFiles(homeDir, new NameFileFilter("jetty.xml"), DirectoryFileFilter.DIRECTORY);
		for(Object fileObj : col){
			File file = (File)fileObj;
			String port = getServerPortFromJettyConfigFile(file);
			if(port != null){
				return Integer.parseInt(port);
			}
		}
		
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	private static String getServerPortFromJettyConfigFile(File file) throws DocumentException{
		Document doc = getDocument(file);
		Element root = doc.getRootElement();
		List<Element> calls = root.elements("Call");
		for(Element call : calls){
			if("addConnector".equals(call.attributeValue("name"))){
				List<Element> sets = call.element("Arg").element("New").elements("Set");
				for(Element setEle : sets){
					if("port".equals(setEle.attributeValue("name"))){
						return setEle.element("SystemProperty").attributeValue("default");
					}
				}				
			}
		}
		
		return null;
	}
	
	public static String getWebAppContextPath(){
		Assert.notNull(webAppContextPath, "webAppContextPath is null! must be init!");
		return webAppContextPath;
	}
	
	public static void setWebAppContextPath(String webAppContextPath) {
		AppServerUtil.webAppContextPath = webAppContextPath;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(AppServerUtil.getHostIPs());
		
		File file = new File("F:\\IBM\\WebSphere\\AppServer\\profiles\\AppSrv02\\config\\cells\\LC-PCCell01\\nodes\\LC-PCNode01\\serverindex.xml");
		String port = getServerPortFromWASConfigFile("serv1", file);
		System.out.println(port);
		
		File file1 = new File("E:\\tomcat\\apache-tomcat-6.0.29\\conf\\server.xml");		
		port = getServerPortFromTomcatConfigFile(file1);
		System.out.println(port);
		
		
		File file2 = new File("E:\\DEVELOP\\jetty\\jetty-6.1.26\\etc\\jetty.xml");		
		port = getServerPortFromJettyConfigFile(file2);
		System.out.println(port);
		
	}

	
}
