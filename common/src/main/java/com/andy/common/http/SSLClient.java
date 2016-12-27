/**
 * 
 */
package com.andy.common.http;

import com.andy.common.utils.CodeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import com.andy.common.utils.CodeUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * 
 * @author root
 * @createDt 2014-9-9 上午10:32:39
 */
public class SSLClient {
	
	private String id = "a";
	private String httpUrl = "https://127.0.0.1:8443/blue-service/user/name/" + CodeUtils.encodeToUtf8(id) + "?type=aa";

	private static final String CLIENT_KEY = "d:/ssl/client_a.key";
	private static final String CLIENT_KEY_STORE_PASSWORD = "123456";
	private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "123456";
	SSLContext ctx = null;
	
	public void initEvn() throws NoSuchAlgorithmException, KeyStoreException,
			CertificateException, FileNotFoundException, IOException,
			UnrecoverableKeyException, KeyManagementException {
		ctx =  SSLContext.getInstance("SSL");
		// 证书
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		// 信任证书库
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		KeyStore ks = KeyStore.getInstance("JKS");
		KeyStore tks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(CLIENT_KEY),
				CLIENT_KEY_STORE_PASSWORD.toCharArray());
		tks.load(new FileInputStream(CLIENT_KEY),
				CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
		kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
		tmf.init(tks);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
	}
	
	
	public void doService() throws ClientProtocolException, IOException{
		HttpClient httpClient = new DefaultHttpClient();
		SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
		Scheme sch = new Scheme("https", 8443, socketFactory);
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		
		HttpGet httpPost = new HttpGet(httpUrl);
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		nvps.add(new BasicNameValuePair("user", "abin"));
//		nvps.add(new BasicNameValuePair("pwd", "abing"));
//		httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		HttpResponse httpResponse = httpClient.execute(httpPost);
//		String spt = System.getProperty("line.separator"); 
		BufferedReader buffer = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		StringBuffer stb=new StringBuffer();
		String line=null;
		while((line=buffer.readLine())!=null){
			stb.append(line);
		}
		buffer.close();
		String result=stb.toString();
		System.out.println("result="+result);
	}
	
	
	public static void main(String[] args) throws Exception {
		SSLClient client = new SSLClient();
		client.initEvn();
		client.doService();
	}
	

}