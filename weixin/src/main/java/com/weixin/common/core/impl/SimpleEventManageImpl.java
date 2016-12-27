package com.weixin.common.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.weixin.common.core.UserManagerService;
import com.weixin.common.core.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.weixin.common.config.load.ConfigLoader;
import com.weixin.common.core.AsyncReplyManage;
import com.weixin.common.core.SimpleEventManage;
import com.weixin.common.model.config.ReplyConfig;
import com.weixin.common.model.request.UserRequest;
import com.weixin.common.model.respone.Article;
import com.weixin.common.model.respone.NewsOutputMessage;
import com.weixin.common.model.respone.TextOutputMessage;
import com.weixin.common.model.user.WechatUser;
import com.weixin.common.user.smo.UserBoundSMO;
import com.weixin.common.util.CommonConstants;
import com.weixin.common.util.MessageUtil;
import com.weixin.util.AES;

/**
 * 事件管理类
 *
 * @author WH
 */
@Component("simpleEventManage")
public class SimpleEventManageImpl implements SimpleEventManage {

    @Autowired
    AsyncReplyManage asyncReplyManage;

    @Autowired
    ConfigLoader configLoader;

    @Autowired
    private UserBoundSMO userBoundSMO;

    @Autowired
    private UserManagerService userManagerService;

    @Autowired
    private WxService wxService;

    /**
     * 请求消息类型为文本处理
     *
     * @return String
     */
    public String dealRequestDefaultMsg(UserRequest ur) {
        ReplyConfig defaultEventReply = configLoader.getDefaultEventReply();
        return doReply(ur, defaultEventReply);
    }

    @Override
    public String dealLocationReq(UserRequest ur) {


        return null;
    }

    /**
     * 处理文本请求并返回
     */
    public String dealTextReq(UserRequest ur) {
        String reqContext = ur.getRequestMap().get("Content");
        Map<String, ReplyConfig> textReplyMap = configLoader.getTextMsgReply();
        ReplyConfig textReply = textReplyMap.get(reqContext);
        if (textReply == null) {
            textReply = configLoader.getDefaultMsgReply();
        }
        if (CommonConstants.MSG_REPLY_ASYNC.equals(textReply.getReplyMode())) {
            asyncReplyManage.dealAsyncMessage(ur);
            return "";
        } else {
            return doReply(ur, textReply);
        }
    }

    /**
     * 回复消息
     *
     * @param ur
     * @param eventReply
     * @return
     */
    private String doReply(UserRequest ur, ReplyConfig eventReply) {
        if (eventReply == null) {
            return buildTextNotImpl(ur);
        }
        if (MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(eventReply.getMsgType())) {
            return buildTextMsg(ur, eventReply.getMsgContent());
        } else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(eventReply.getMsgType())) {
            return bulidNewsMsg(ur, eventReply.getArticles());
        }
        return "";
    }

    /**
     * 暂未实现功能回复处理
     *
     * @param ur
     * @return
     */
    private String buildTextNotImpl(UserRequest ur) {
        String resultMessage = "";
        try {
            TextOutputMessage textMessage = new TextOutputMessage();
            textMessage.setToUserName(ur.getFromUserName());
            textMessage.setFromUserName(ur.getToUserName());
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setFuncFlag(0);
            textMessage.setContent("功能测试中，敬请期待...");
            resultMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMessage;
    }

    /**
     * 构造文本回复消息
     *
     * @param ur
     * @param content
     * @return
     */
    private String buildTextMsg(UserRequest ur, String content) {
        if (content == null || "".equals(content)) {
            return "";
        }
        String resultMessage = "";
        try {
            TextOutputMessage textMessage = new TextOutputMessage();
            textMessage.setToUserName(ur.getFromUserName());
            textMessage.setFromUserName(ur.getToUserName());
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setFuncFlag(0);
            AES aes = new AES(CommonConstants.PASSWORD_ENC);
            if (content.contains(CommonConstants.PARAM_REPLACE_OPENID)) {
                content = content.replace(CommonConstants.PARAM_REPLACE_OPENID,
                        aes.encrypt(ur.getFromUserName()));
            }
            textMessage.setContent(content);
            resultMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMessage;
    }

    /**
     * 构造图文回复
     *
     * @param ur
     * @param arts
     * @return
     */
    private String bulidNewsMsg(UserRequest ur, List<Article> arts) {
        String resultMessage = "";
        try {
            NewsOutputMessage newsMessage = new NewsOutputMessage();
            newsMessage.setToUserName(ur.getFromUserName());
            newsMessage.setFromUserName(ur.getToUserName());
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setFuncFlag(0);
            List<Article> articleList = new ArrayList<Article>();
            if (arts != null) {
                for (Article article : arts) {
                    Article art = (Article) article.clone();
                    if (art.getPicUrl() != null && art.getPicUrl().startsWith("/")) {
                        art.setPicUrl(ur.getRequestUrl() + art.getPicUrl());
                    }
                    if (art.getUrl() != null && art.getUrl().startsWith("/")) {
                        art.setUrl(ur.getRequestUrl() + art.getUrl());
                    }
                    AES aes = new AES(CommonConstants.PASSWORD_ENC);
                    if (art.getUrl().contains(CommonConstants.PARAM_REPLACE_OPENID)) {
                        art.setUrl(art.getUrl().replace(CommonConstants.PARAM_REPLACE_OPENID,
                                aes.encrypt(ur.getFromUserName())));
                    }
                    articleList.add(art);
                }
            }
            // 设置图文消息个数
            newsMessage.setArticleCount(articleList.size());
            // 设置图文消息包含的图文集合
            newsMessage.setArticles(articleList);
            // 将图文消息对象转换成xml字符串
            resultMessage = MessageUtil.newsMessageToXml(newsMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMessage;
    }

    /**
     * 请求消息类型为链接处理
     *
     * @return String
     */
    @SuppressWarnings("unused")
    private String dealRequestLinkMsg() {
        return null;
    }

    /**
     * 请求消息类型为地理位置处理
     *
     * @return String
     */
    @SuppressWarnings("unused")
    private String dealRequestLocationMsg() {
        return null;
    }

    /**
     * 请求消息类型为语音处理
     *
     * @return String
     */
    @SuppressWarnings("unused")
    private String dealRequestVoiceMsg() {
        return null;
    }


    /**
     * 关注事件处理
     *
     * @param ur
     * @return String
     */
    public String dealSubscribeEvent(UserRequest ur) {
        String access_token = wxService.getAccessToken();
        WechatUser wechatUser = userManagerService.getUserInfo(ur.getFromUserName(), access_token);

        wechatUser.setSubscribe_status(CommonConstants.SUBSCRIBE_STATUS);

        userBoundSMO.saveWechatUser(wechatUser);
        ReplyConfig config = configLoader.getSubscribeEventReply();
        return doReply(ur, config);
    }

    /**
     * 取消关注事件处理
     *
     * @return String
     */
    public String dealUnSubscribeEvent(UserRequest ur) {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpen_id(ur.getFromUserName());
        wechatUser.setSubscribe_status(CommonConstants.UNSUBSCRIBE_STATUS);
        userBoundSMO.saveWechatUser(wechatUser);
        return null;
    }

    /**
     * 处理click事件
     */
    @Override
    public String dealClickEvent(UserRequest ur) {
        Map<String, ReplyConfig> config = configLoader.getMenuEventReplyConfig();
        Map<String, String> requestMap = ur.getRequestMap();
        String eventKey = requestMap.get("EventKey");
        ReplyConfig menuEventReply = config.get(eventKey);
        return doReply(ur, menuEventReply);
    }

}
