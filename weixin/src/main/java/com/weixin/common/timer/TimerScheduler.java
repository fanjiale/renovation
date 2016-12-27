package com.weixin.common.timer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 定时任务执行
 */
@Component("timerScheduler")
public class TimerScheduler {
	@Autowired
	ScheduledTask scheduledTask;
	
	final static int ACCESS_TOKEN_PERIOD = 1500;
	
	public ScheduledTask getScheduledTask() {
		return scheduledTask;
	}
	public void setScheduledTask(ScheduledTask scheduledTask) {
		this.scheduledTask = scheduledTask;
	}
	Scheduler scheduler;

	//todo 定时任务
	@PostConstruct
	public void init(){
		/*scheduler = new Scheduler(1, "wx-scheduler", false);
		this.scheduler.scheduleWithRate(scheduledTask.updateAccessTokenTask(), 0L, ACCESS_TOKEN_PERIOD*1000);*/
	}

	@PreDestroy
	public void destory(){
		scheduler.shutdownGraceful();
	}
}
