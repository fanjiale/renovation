package com.weixin.common.core.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.common.core.CoreService;
import com.weixin.common.core.SimpleEventManage;
import com.weixin.common.model.menu.MsgEventType;
import com.weixin.common.model.request.UserRequest;
import com.weixin.common.util.MessageUtil;

/**
 * 核心处理服务
 *
 * @author fukai
 * @date 2015-06-01
 */
@Component("coreService")
public class CoreServiceImpl implements CoreService {

    @Autowired
    private SimpleEventManage simpleEventManage;

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            UserRequest ur = (UserRequest) request.getAttribute("UR");
            if (null == ur) {
                ur = MessageUtil.parseRequest(request);
            }
            switch (getMsgType(ur)) {
                case LOCATION:
                    respMessage = simpleEventManage.dealLocationReq(ur);
                    break;
                case DEFAULT:
                    respMessage = simpleEventManage.dealRequestDefaultMsg(ur);
                    break;
                case SUBSCIRBE:
                    respMessage = simpleEventManage.dealSubscribeEvent(ur);
                    break;
                case MENUEVENT:
                    respMessage = simpleEventManage.dealClickEvent(ur);
                    break;
                case UNSUBSCRIBE:
                    respMessage = simpleEventManage.dealUnSubscribeEvent(ur);
                case TEXT:
                    respMessage = simpleEventManage.dealTextReq(ur);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respMessage;
    }

    private static MsgEventType getMsgType(UserRequest ur) {
        if (ur.getMsgType().equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            return MsgEventType.TEXT;
        }
        // 请求消息类型为推送
        if (ur.getMsgType().equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {

            String eventType = ur.getRequestMap().get("Event");
            // 上报地理位置
            if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                return MsgEventType.LOCATION;
            }
            // 关注
            if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                return MsgEventType.SUBSCIRBE;
            }
            // 取消关注
            if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                return MsgEventType.UNSUBSCRIBE;
            }

            // 菜单点击
            if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                return MsgEventType.MENUEVENT;
            }
        }
        return MsgEventType.DEFAULT;
    }


}
