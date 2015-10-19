// PACKAGE

package com.jakarta.software.web.entity;


// IMPORT

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


// ENTITY

public class UserPreference implements java.io.Serializable 
{
	// INITIALIZATION
	
	private static final long serialVersionUID = 1L;
	
	private int userID;			// user_id		INT			AUTO_INCREMENT NOT NULL
	private String fontFamily;	// font_family	VARCHAR()	NOT NULL
	private String fontSize;	// font_size	VARCHAR()	NOT NULL
	private String language;	// language		VARCHAR()	NOT NULL
	private String theme;		// theme		VARCHAR()	NOT NULL
    
	
	// GENERAL
	
	public String toString() 
	{
		return ReflectionToStringBuilder.toString(this);
	}
	
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}

	
	// SETTER
	
	public void setUserID(int userID) 
	{
		this.userID = userID;
	}
	
	public void setFontFamily(String fontFamily) 
	{
		this.fontFamily = fontFamily;
	}
	
	public void setFontSize(String fontSize) 
	{
		this.fontSize = fontSize;
	}
	
	public void setLanguage(String language) 
	{
		this.language = language;
	}
	
	public void setTheme(String theme) 
	{
		this.theme = theme;
	}
	
	
	// GETTER
	
	public int getUserID() 
	{
		return userID;
	}
	
	public String getFontFamily() 
	{
		return fontFamily;
	}
	
	public String getFontSize() 
	{
		return fontSize;
	}
	
	public String getLanguage() 
	{
		return language;
	}
	
	public String getTheme() 
	{
		return theme;
	}
}