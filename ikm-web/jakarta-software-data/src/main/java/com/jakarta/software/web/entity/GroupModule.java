package com.jakarta.software.web.entity;

import java.io.Serializable;

//import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class GroupModule implements Serializable {
	private static final long serialVersionUID = 1L;

	private int levelId;
	private int modulesId;
	private int accessLevel;

	/*
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	*/
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public int getModulesId() {
		return modulesId;
	}
	public void setModulesId(int modulesId) {
		this.modulesId = modulesId;
	}
	public int getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
}
