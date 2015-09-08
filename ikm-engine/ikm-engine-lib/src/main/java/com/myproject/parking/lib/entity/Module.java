package com.myproject.parking.lib.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Module implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int moduleId;  // module_id Int NOT NULL,
	private int moduleParentId;  // module_parent_id Int NOT NULL,
	private int moduleRootId;  // module_root_id Int NOT NULL,
	private int moduleLevel;  // module_level Int NOT NULL,
	private String moduleName;  // module_name Varchar(65) NOT NULL,
	private String modulePath;  // module_path Varchar(65) NOT NULL,
	private String moduleDesc;  // module_desc Varchar(65) NOT NULL,
	private String moduleGroup;  // module_group Varchar(65) NOT NULL,
	private int showOrder;  // show_order Int NOT NULL,
	private boolean moduleLeaf;  // module_leaf Bit(1) NOT NULL,
	private boolean alwaysIncluded;  // always_included Bit(1) NOT NULL,
	
	//for access level
	// 1 = read only, 2= full
	private int accessLevel;
	  
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getModuleParentId() {
		return moduleParentId;
	}
	public void setModuleParentId(int moduleParentId) {
		this.moduleParentId = moduleParentId;
	}

	public int getModuleRootId() {
		return moduleRootId;
	}
	public void setModuleRootId(int moduleRootId) {
		this.moduleRootId = moduleRootId;
	}

	public int getModuleLevel() {
		return moduleLevel;
	}
	public void setModuleLevel(int moduleLevel) {
		this.moduleLevel = moduleLevel;
	}

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModulePath() {
		return modulePath;
	}
	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	
	public String getModuleGroup() {
		return moduleGroup;
	}
	public void setModuleGroup(String moduleGroup) {
		this.moduleGroup = moduleGroup;
	}

	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public boolean isModuleLeaf() {
		return moduleLeaf;
	}
	public void setModuleLeaf(boolean moduleLeaf) {
		this.moduleLeaf = moduleLeaf;
	}

	public boolean isAlwaysIncluded() {
		return alwaysIncluded;
	}
	public void setAlwaysIncluded(boolean alwaysIncluded) {
		this.alwaysIncluded = alwaysIncluded;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public int getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	
}
