package com.jakarta.software.web.data;

import com.jakarta.software.web.entity.UserData;

public class UserDataVO extends UserData{
	private static final long serialVersionUID = 1L;
	// for display
	private int userId;
    private String userStatusDisplay; 
    private String userLevelDisplay;
    private String branchDisplay;
    private String createdByDisplay;
    private String updatedByDisplay;
    private String activityType;
    
	public String getUserStatusDisplay() {
		return userStatusDisplay;
	}
	public void setUserStatusDisplay(String userStatusDisplay) {
		this.userStatusDisplay = userStatusDisplay;
	}
	public String getUserLevelDisplay() {
		return userLevelDisplay;
	}
	public void setUserLevelDisplay(String userLevelDisplay) {
		this.userLevelDisplay = userLevelDisplay;
	}
	public String getBranchDisplay() {
		return branchDisplay;
	}
	public void setBranchDisplay(String branchDisplay) {
		this.branchDisplay = branchDisplay;
	}
	public String getCreatedByDisplay() {
		return createdByDisplay;
	}
	public void setCreatedByDisplay(String createdByDisplay) {
		this.createdByDisplay = createdByDisplay;
	}
	public String getUpdatedByDisplay() {
		return updatedByDisplay;
	}
	public void setUpdatedByDisplay(String updatedByDisplay) {
		this.updatedByDisplay = updatedByDisplay;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
   
}
