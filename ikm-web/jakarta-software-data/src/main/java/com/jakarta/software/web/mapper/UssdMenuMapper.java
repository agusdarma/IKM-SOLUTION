package com.jakarta.software.web.mapper;

import java.util.List;

import com.jakarta.software.web.data.param.UssdMenuParamVO;
import com.jakarta.software.web.entity.UssdMenu;

public interface UssdMenuMapper {
	public List<UssdMenu> findUssdMenuChildById(int id);
	
	public UssdMenu findUssdMenuById (int id);
	
	public void createUssdMenu (UssdMenu ussdMenu);
	public void updateUssdMenu (UssdMenu ussdMenu);
	public int deleteUssdMenu (int id);
	
	public List<UssdMenu> findUssdMenuByParam(UssdMenuParamVO paramVO);
	public int countUssdMenuByParam(UssdMenuParamVO paramVO);
}
