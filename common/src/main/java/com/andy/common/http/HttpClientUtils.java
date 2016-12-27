package com.andy.common.http;

import org.apache.commons.lang.StringUtils;
import com.andy.common.exception.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * HttpClient调用工具类
 * @author root
 *
 */
public class HttpClientUtils {
	public static HttpURLConnection getHttpConnection(String targetHttpUrl, String reqeustMethod){
		URL targetUrl;
		HttpURLConnection httpConnection = null;
		try {
			targetUrl = new URL(targetHttpUrl);
			httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setRequestMethod(reqeustMethod);
			httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			if(reqeustMethod.equals(Method.GET.toString())){
				httpConnection.setDoOutput(false);
			} else {
				httpConnection.setDoOutput(true);
			}
		} catch (MalformedURLException e) {
			throw new ServiceException("-9999", e);
		} catch (IOException e) {
			throw new ServiceException("-9999", e);
		}
//		new DefaultHttpRequestRetryHandler(0, false);
//		httpConnection.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler (0, false));
		return httpConnection;
	}
	
	public static void setTimeout(HttpURLConnection httpConnection, int connectionTimeout, int readTimeout){
		httpConnection.setConnectTimeout(30000);
		httpConnection.setReadTimeout(30000);
	}
	
	/**
	 * 请求时发送数据,如post请求的话，需要放入post信息
	 * @param httpConnection
	 * @param input
	 */
	public static void sendReqData(HttpURLConnection httpConnection, String input){
		OutputStream outputStream = null;
		try {
			outputStream = httpConnection.getOutputStream();
			System.out.println("input = " + input);
			outputStream.write(input.getBytes("utf-8"));
			outputStream.flush();
		} catch (IOException e) {
			throw new ServiceException("-9999", e);
		} finally {
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {}
			}
		}
		
	}
	
	/**
	 * 获取数据
	 * @param httpConnection
	 * @return
	 */
	public  static String fetchResData(HttpURLConnection httpConnection, String charSetString){
		if(StringUtils.isNotEmpty(charSetString)){
			charSetString = "UTF-8";
		}
		String retString = null;
		try {
			BufferedReader responseBuffer= null;
			
			int retCode = httpConnection.getResponseCode();
			if (httpConnection.getResponseCode() == 404) {
				throw new ServiceException("http-404", "Failed : HTTP error code : " + httpConnection.getResponseCode());
			} else if(httpConnection.getResponseCode() == 200){
				responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),"UTF-8"));
			} else if(retCode == 302){
				return ("只能使用https进行调用");
//				Header header = httpConnection.getResponseHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
//				String newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
			} else {
				responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream(),"UTF-8"));
			}
			StringBuilder sb = new StringBuilder("");
			String tmp = null;
			while ((tmp = responseBuffer.readLine()) != null) {
				sb.append(tmp);
			}
			retString = sb.toString();
		} catch (IOException e) {
			throw new ServiceException("-9999", e);
		}
		return retString;
	}
	
	/**
	 * 获取数据，默认utf-8
	 * @param httpConnection
	 * @return
	 */
	public  static String fetchResData(HttpURLConnection httpConnection){
		return fetchResData(httpConnection, "UTF-8");
	}
	
	public enum Method {
		GET,
		POST,
		PUT,
		DELETE;
	}
}


