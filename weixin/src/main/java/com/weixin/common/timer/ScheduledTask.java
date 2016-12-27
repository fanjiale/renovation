package com.weixin.common.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 定时任务
 * @author H
 *
 */
@Component("scheduledTask")
public class ScheduledTask {
	@Autowired
	UpdateAccessTokenTask updateAccessTokenTask;
	
	public UpdateAccessTokenTask getUpdateAccessTokenTask() {
		return updateAccessTokenTask;
	}

	public void setUpdateAccessTokenTask(UpdateAccessTokenTask updateAccessTokenTask) {
		this.updateAccessTokenTask = updateAccessTokenTask;
	}

	/**
	 * AccessToken更新任务
	 * @return
	 */
	public  Runnable updateAccessTokenTask(){
		return new Runnable() {
			@Override
			public void run() {
				updateAccessTokenTask.update();
			}
		};
	}
	
	
}
