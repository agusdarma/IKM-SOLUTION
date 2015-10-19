// PACKAGE

package com.jakarta.software.web.mapper;


// IMPORT

import com.jakarta.software.web.entity.UserPreference;


// LOGIC

public interface UserPreferenceMapper 
{
	// BASIC INITIALIZATION
	
	public UserPreference findUserPreferenceByID(int userID);
	public void insertUserPreference(UserPreference userPreference);
	public void updateUserPreferenceByID(UserPreference userPreference);
	public void deleteUserPreferenceByID(UserPreference userPreference);
}