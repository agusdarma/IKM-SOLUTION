package com.myproject.parking.lib.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.myproject.parking.lib.service.AppsTimeService;

public class SyncTimeJob {
	
	@Autowired
	private AppsTimeService timeService;
	
	public void syncTime() {
		timeService.updateCurrentTime();
	}
}
