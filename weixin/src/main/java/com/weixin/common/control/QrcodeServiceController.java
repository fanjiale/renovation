package com.weixin.common.control;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.andy.common.domains.RspResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.common.core.QrcodeService;

/**
 * 二维码获取 
 */
@Controller
@RequestMapping("/qrcodea")
public class QrcodeServiceController {

	@Autowired
	private QrcodeService qrcodeService;
	
	@RequestMapping("getQrTicket")
	@ResponseBody
	public RspResult getQrTicket(String qrLimit, String sceneId){
		String ticket = qrcodeService.getQrTicket(qrLimit, sceneId);
		return RspResult.getSuccessResult().setData(ticket);
	}
	
	@RequestMapping("download")
	public void download(HttpServletRequest req,HttpServletResponse res){
		qrcodeService.download(req, res);
	}
}
