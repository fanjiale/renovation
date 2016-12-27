package com.weixin.common.model.message;

import java.util.List;

/**
 * 客服接口图文消息 
 */
public class CustNewsMessage {

	private String touser;
	private String msgtype;
	private NewsContent news;
	
	public class NewsContent{
		private List<CustArticle> articles;

		public List<CustArticle> getArticles() {
			return articles;
		}

		public void setArticles(List<CustArticle> articles) {
			this.articles = articles;
		}
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public NewsContent getNews() {
		return news;
	}

	public void setNews(NewsContent news) {
		this.news = news;
	}
}