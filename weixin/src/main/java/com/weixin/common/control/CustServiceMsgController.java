package com.weixin.common.control;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.common.core.AsyncReplyManage;
import com.weixin.common.core.CustServiceMsg;
import com.weixin.common.model.request.UserRequest;


@Controller
@RequestMapping("custService")
public class CustServiceMsgController {

	@Autowired
	private CustServiceMsg custServiceMsg;
	@Autowired
	private AsyncReplyManage asyncReplyManage;
	
	private UserRequest userRequest = null;
	
	/**
	 * 客服接口
	 */
	@RequestMapping("sendCustMsg")
	@ResponseBody
	public void sendCustServiceMsg(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String openId = request.getParameter("openId");
		custServiceMsg.sendCustServiceMsg(id, openId);
	}
	
	/**
	 * 测试异步处理消息
	 */
	@RequestMapping("testAsyncMessage")
	@ResponseBody
	public void testAsyncMessage(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String content = request.getParameter("content");
		String openId = request.getParameter("openId");
		userRequest = new UserRequest();
		userRequest.setCreateTime("1437032510");
		userRequest.setFromUserName(openId);
		userRequest.setMsgId("6172007633946228392");
		userRequest.setMsgType("text");
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("Content", content);
		userRequest.setRequestMap(requestMap);
		userRequest.setToUserName("gh_31f1494fc969");
		userRequest.setRequestUrl("http://xmz9172.xicp.net/weixin-manager-web/custService/testAsyncMessage");
		
		asyncReplyManage.dealAsyncMessage(userRequest);
	}
}
