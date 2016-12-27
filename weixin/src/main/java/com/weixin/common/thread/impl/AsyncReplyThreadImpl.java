package com.weixin.common.thread.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.weixin.common.thread.AsyncReplyRunnable;
import com.weixin.common.thread.ThreadManage;

/**
 * 线程管理实现类
 * @author XMZ
 */

public class AsyncReplyThreadImpl implements ThreadManage{

	private AsyncReplyRunnable[] asyncReplyRun;
	private Future[] futureHandles ;
	private ExecutorService sendMsgService ;
	
	public AsyncReplyRunnable[] getAsyncReplyRun() {
		return asyncReplyRun;
	}

	public void setAsyncReplyRun(AsyncReplyRunnable[] asyncReplyRun) {
		this.asyncReplyRun = asyncReplyRun;
	}

	/**
	 * 线程启动 
	 */
	public void start(){
		futureHandles = new Future[asyncReplyRun.length];
		sendMsgService = Executors.newFixedThreadPool(asyncReplyRun.length);
		for(int i = 0 ; i < asyncReplyRun.length ; i ++){
			asyncReplyRun[i].setRunflag(true,i);
			futureHandles[i] = sendMsgService.submit(asyncReplyRun[i]);
		}
	}
	
	/**
	 * 线程停止
	 */
	public void stop(){
		if(asyncReplyRun.length>0){
			for (int i = 0; i < asyncReplyRun.length; i++) {
				asyncReplyRun[i].setRunflag(false);
				futureHandles[i].cancel(true);
				asyncReplyRun[i]=null;
			}
		}
		sendMsgService.shutdown();
	}
}
