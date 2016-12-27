package com.weixin.common.thread.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.weixin.common.core.AsyncReplyManage;
import com.weixin.common.model.request.UserRequest;
import com.weixin.common.thread.AsyncReplyRunnable;
import com.weixin.common.thread.AsyncReplyQueue;

public class AsyncReplyRunnableImpl implements AsyncReplyRunnable{
	
	@Autowired
	private AsyncReplyManage asyncReplyManage;
	
	private boolean runflag = true;
	private int theadNo; // 当前线程号
	
	
	public void startRun() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		while (isRunflag()) {
			try {
				UserRequest userRequest = AsyncReplyQueue.getPoll();
				if(userRequest != null){
					asyncReplyManage.sendAsyncMessage(userRequest);
				}else{
					Thread.sleep(20000);
				}
			} catch (InterruptedException e) {
			}
		}
	}

	public boolean isRunflag() {
		return runflag;
	}

	public void setRunflag(boolean runflag) {
		this.runflag = runflag;
	}

	public void setRunflag(boolean runflag, int i) {
		this.theadNo = i;
		this.runflag = runflag;
	}
}
