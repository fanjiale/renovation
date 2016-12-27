package com.weixin.common.model.respone;

/**
 * 回复消息基类（公众帐号->普通用户）
 * 
 * @author fukai
 * @date 2015-06-01
 */
public abstract class OutputMessage implements java.io.Serializable {

	private static final long serialVersionUID = 2459720522512217625L;
	// 开发者微信号
	private String ToUserName;
	// 发送方帐号（即OpenId）
	private String FromUserName;
	// 消息创建时间（整型）
	private long CreateTime;
	// 位0x0001被标志时，星标刚收到的消息
	private int FuncFlag;

	// 消息类型（在继承类中定义类型）
	public abstract String getMsgType();

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public int getFuncFlag() {
		return FuncFlag;
	}

	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}
}