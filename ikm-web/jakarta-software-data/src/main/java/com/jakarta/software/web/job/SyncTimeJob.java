package com.jakarta.software.web.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.jakarta.software.web.service.AppsTimeService;

public class SyncTimeJob {
	
	@Autowired
	private AppsTimeService timeService;
	
	public void syncTime() {
		timeService.updateCurrentTime();
	}
}
