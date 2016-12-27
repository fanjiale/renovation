package com.weixin.common.thread;

public interface AsyncReplyRunnable extends Runnable {
	
	public void setRunflag(boolean runflag);
	
	public void setRunflag(boolean runflag, int i);
}
