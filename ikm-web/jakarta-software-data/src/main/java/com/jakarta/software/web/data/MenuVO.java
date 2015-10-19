package com.jakarta.software.web.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.entity.UserMenu;

public class MenuVO extends UserMenu implements Comparable<MenuVO> {
	private static final long serialVersionUID = 1L;
	
	private List<MenuVO> childs;
	
	public List<MenuVO> getChilds() {
		if (childs == null)
			childs = new ArrayList<MenuVO>();
		return childs;
	}
	public boolean hasChild() {
		if (getChilds().size() == 0) return false;
		else return true;
	}
	public boolean hasMenuUrl() {
		if (StringUtils.isEmpty(getMenuUrl()))
			return false;
		else
			return true;
	}
	
	public int compareTo(MenuVO o) {
		return this.getShowOrder() - o.getShowOrder();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
