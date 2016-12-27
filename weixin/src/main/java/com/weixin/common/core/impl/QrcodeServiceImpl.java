package com.weixin.common.core.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.common.core.QrcodeService;
import com.weixin.common.core.WxService;
import com.weixin.common.mapper.AccessTokenMapper;

/**
 * 二维码获取实现类
 */
@Component("qrcodeService")
public class QrcodeServiceImpl implements QrcodeService{

	@Autowired
	AccessTokenMapper accessTokenMapper;
	@Autowired
	private WxService wxService;
	
	/**
	 * 获取二维码 
	 */
	public String getQrTicket(String qrLimit, String sceneId){
		if(qrLimit == null || qrLimit.length()==0){
			qrLimit = "QR_LIMIT_SCENE";
		}
		String jsonStr = "{\"action_name\":\""+ qrLimit+"\",\"action_info\":{\"scene\":{\"scene_id\":"+sceneId+"}}}";
		JSONObject jo = wxService.getQr(jsonStr);
		String ticket = String.valueOf(jo.get("ticket"));
		return ticket;
	}
	
	/**
	 * 下载二维码 
	 */
	public void download(HttpServletRequest req,HttpServletResponse res){
		String ticket = req.getParameter("ticket");
		String sceneId = req.getParameter("sceneId");
		InputStream is = null;
		OutputStream os = null;
		try{
			URL url = new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket);
		    // 打开连接
		    URLConnection con = url.openConnection();
		    // 输入流
		    is = con.getInputStream();
		    // 1K的数据缓冲
		    byte[] bs = new byte[1024];
		    // 读取到的数据长度
		    int len;
		    // 输出的文件流
		    os = res.getOutputStream();
		    // 开始读取
		    res.addHeader("Content-Disposition","attachment;filename=\"" + sceneId+".jpg"+"\"");
		    while ((len = is.read(bs)) != -1) {
		      os.write(bs, 0, len);
		    }
		}catch(Exception e){
			
		}finally{
		    // 完毕，关闭所有链接
		    try {
		    	if(os != null){
		    		os.flush();
		    		os.close();
		    	}
		    	if(is != null) {
		    		is.close();
		    	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
