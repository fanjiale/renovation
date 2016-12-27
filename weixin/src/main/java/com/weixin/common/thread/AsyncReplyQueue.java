package com.weixin.common.thread;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Component;
import com.weixin.common.model.request.UserRequest;

/**
 * 队列处理 
 */
@Component("queueManage")
public class AsyncReplyQueue {

	private static ConcurrentLinkedQueue<UserRequest> messageQueue;
	
	/** 
	 * 构造方法调用时创建队列
	 */
	public AsyncReplyQueue() {
		if (null == messageQueue) {
			messageQueue = new ConcurrentLinkedQueue<UserRequest>(); //基于链接节点的无界线程安全队列
		}
	}
	
	/**
	 * 添加到队列方法
	 */
	public static boolean add(UserRequest userRequest) {
		return (messageQueue.offer(userRequest));
	}
	
	/**
	 * 获取并移除此队列的头 ，如果此队列为空，则返回 null
	 */
	public static UserRequest getPoll() throws InterruptedException{
		return (messageQueue.poll());
	}
}
