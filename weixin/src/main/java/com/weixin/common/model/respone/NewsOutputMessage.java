package com.weixin.common.model.respone;

import java.util.List;

public class NewsOutputMessage extends OutputMessage {

	private static final long serialVersionUID = 2861415510387973225L;

	// 消息类型:图文消息
	private String MsgType = "news";
	// 图片消息个数
	private int ArticleCount;
	// 图片消息
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

	@Override
	public String getMsgType() {
		// TODO Auto-generated method stub
		return MsgType;
	}

}
