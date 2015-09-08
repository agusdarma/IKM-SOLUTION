package com.myproject.parking.lib.data;

import java.util.ArrayList;
import java.util.List;

public class ModuleVO implements java.io.Serializable, Comparable<ModuleVO> {
	private static final long serialVersionUID = 1L;
	
	private int moduleId;
	private int moduleLevel;
	private String moduleName;
	private String modulePath;
	private String moduleDesc;
	private String moduleGroup;
	private boolean moduleLeaf;
	private boolean alwaysIncluded;
	private int moduleParentId;
	private int moduleRootId;
	private int showOrder;
	private List<ModuleVO> childs;
	
	//for access level
	// 1 = read only, 2= full
	private int accessLevel;
	
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
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
	public boolean getModuleLeaf() {
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
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	
	public List<ModuleVO> getChilds() {
		if (childs == null)
			childs = new ArrayList<ModuleVO>();
		return childs;
	}
	public boolean hasChild() {
		if (getChilds().size() == 0) return false;
		else return true;
	}
	public boolean hasModulePath() {
		return (modulePath != null && !modulePath.trim().equals(""));
	}
	
	public int compareTo(ModuleVO o) {
		return showOrder - o.getShowOrder();
	}
	public int getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	
}
