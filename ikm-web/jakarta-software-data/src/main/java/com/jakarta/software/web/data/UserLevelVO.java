package com.jakarta.software.web.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jakarta.software.web.entity.UserMenu;

public class UserLevelVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String levelName;
	private String levelDesc;
	private List<MenuVO> orderedMenu;
	private int[] allowedIds;	
	
	public UserLevelVO(List<UserMenu> listMenu) {
		allowedIds = new int[listMenu.size()];
		
		orderedMenu = new ArrayList<MenuVO>();
		// get max level
		int maxLevel = 0;
		int idx = 0;
		for (UserMenu menu: listMenu) {
			allowedIds[idx++] = menu.getMenuId();
			if (menu.getMenuLevel() > maxLevel) maxLevel = menu.getMenuLevel();
		}
		
		// this only set for 2 level
		List<MenuVO> temp = new ArrayList<MenuVO>();
		for (int i = 0; i < maxLevel; i++) {
			for (UserMenu menu: listMenu) {
				if (menu.getMenuLevel() == i + 1) {
					MenuVO vo = createMenuVO(menu);
					temp.add(vo);
					if (i == 0) orderedMenu.add(vo);
					else {
						for (MenuVO data2: temp) {
//							if (data2.getMenuId().equals(vo.getParentId()))
							if (data2.getMenuId() == vo.getParentId())
								data2.getChilds().add(vo);
						}  // end for -> looping modules child
					}
				}  // end if module == level
			}  // end for -> looping module
		}  // end for -> looping to maxLevel
		Collections.sort(orderedMenu);
		for (MenuVO vo: orderedMenu) {
			if (vo.getChilds() != null && vo.getChilds().size() > 0) {
				Collections.sort(vo.getChilds());
			}
		}
	}
	
	public List<MenuVO> getListMenu() {
		return orderedMenu;
	}

	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelDesc() {
		return levelDesc;
	}
	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}
	
	public boolean isMenuAllowed(int menuId) {
		for (int allowedId: allowedIds)
			if (allowedId == menuId) return true;
		return false;
	}
	
	private MenuVO createMenuVO(UserMenu menu) {
		MenuVO vo = new MenuVO();
		BeanUtils.copyProperties(menu, vo);		
		return vo;
	}
	
}
