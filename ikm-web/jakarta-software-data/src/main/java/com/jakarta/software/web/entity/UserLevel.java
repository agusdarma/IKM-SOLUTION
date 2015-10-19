package com.jakarta.software.web.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UserLevel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;  // level_id INT AUTO_INCREMENT NOT NULL,
	private String levelName;  //  level_name VARCHAR(32) NOT NULL,
	private String levelDesc;  //  level_desc VARCHAR(255) NOT NULL,
	private Date createdOn;  //  created_on DATETIME NOT NULL,
	private int createdBy;  //  created_by INT NOT NULL,
	private Date updatedOn;  //  updated_on DATETIME NOT NULL,
	private int updatedBy;  //  updated_by INT NOT NULL,
	private List<UserMenu> listMenu;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName == null? null: levelName.trim();
	}
	public String getLevelDesc() {
		return levelDesc;
	}
	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc == null? null: levelDesc.trim();
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public List<UserMenu> getListMenu() {
		return listMenu;
	}
	public void setListMenu(List<UserMenu> listMenu) {
		this.listMenu = listMenu;
	}
	public void addMenu(UserMenu menu) {
		if(menu == null || menu.getMenuId() == 0)
			return;
		if (listMenu == null)
			listMenu = new ArrayList<UserMenu>();
		if (!listMenu.contains(menu))
			listMenu.add(menu);
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
