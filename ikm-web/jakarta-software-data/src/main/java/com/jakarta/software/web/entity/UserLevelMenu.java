package com.jakarta.software.web.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UserLevelMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	private int menuId;  // menu_id INT NOT NULL,
    private int levelId;  // level_id INT NOT NULL,
	
    public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
