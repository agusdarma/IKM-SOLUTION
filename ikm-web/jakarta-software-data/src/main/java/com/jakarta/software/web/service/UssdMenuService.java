package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.param.UssdMenuParamVO;
import com.jakarta.software.web.entity.UssdMenu;
import com.jakarta.software.web.mapper.UssdMenuMapper;

@Service
public class UssdMenuService {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(UssdMenuService.class);
	
	@Autowired
	private UssdMenuMapper ussdMenuMapper;
	
	public List<UssdMenu> findUssdMenuByParam(UssdMenuParamVO paramVO) {
		List<UssdMenu> listMenu = ussdMenuMapper.findUssdMenuByParam(paramVO);
		if (listMenu == null)
			listMenu = new ArrayList<UssdMenu>();
		return listMenu;
	}
	
	public int countUssdMenuByParam(UssdMenuParamVO paramVO) {
		int count = ussdMenuMapper.countUssdMenuByParam(paramVO);
		return count;
	}
	
	public List<UssdMenu> findUssdMenuChildById(int id) {
		List<UssdMenu> listUssdMenu = ussdMenuMapper.findUssdMenuChildById(id);
		if (listUssdMenu == null)
			listUssdMenu = new ArrayList<UssdMenu>();
		return listUssdMenu;
	}
	
	public List<UssdMenu> findListParentMenu(int parentMenuId) throws MmbsWebException {
		List<UssdMenu> listParentMenu = new ArrayList<UssdMenu>();
		int currentId = parentMenuId;
		while (currentId > 0) {
			UssdMenu um = findUssdMenuById(currentId);
			listParentMenu.add(0, um);
			// bulletproof
			if (currentId == um.getParentId())
				break;
			currentId = um.getParentId();
		}
		return listParentMenu;
	}
	
	public UssdMenu findUssdMenuById(int id) throws MmbsWebException {
		UssdMenu ussdMenu = ussdMenuMapper.findUssdMenuById(id);
		if (ussdMenu == null) {
			LOGGER.warn("Unable to find UssdMenu with Id: " + id);
			throw new MmbsWebException(MmbsWebException.NE_DATA_NOT_FOUND);
		}
		return ussdMenu;
	}
	
	@Transactional
	public UssdMenu saveUssdMenu(UssdMenu ussdMenu, UserDataLoginVO loginVO) throws MmbsWebException {
		try {
			LOGGER.info("Processing - > Ussd Menu Insert/Update: " + ussdMenu);
			if(StringUtils.isEmpty(ussdMenu.getNote())){
				LOGGER.warn("Missing input Note");
				throw new MmbsWebException(MmbsWebException.NE_USSD_MENU_EMPTY);
			}
			
			Date nowDate = new Date();
			ussdMenu.setUpdatedBy(loginVO.getId());
			ussdMenu.setUpdatedOn(nowDate);
			
			/* update */
			if(ussdMenu.getId() > 0){
				ussdMenuMapper.updateUssdMenu(ussdMenu);
			} else {/* insert */
				ussdMenu.setCreatedBy(loginVO.getId());
				ussdMenu.setCreatedOn(nowDate);
				
				ussdMenuMapper.createUssdMenu(ussdMenu);
			}
			// select again to get latest data
			ussdMenu = findUssdMenuById(ussdMenu.getId());
			return ussdMenu;
		} catch (MmbsWebException e) {
			 throw e;
		} catch (Exception e) {
			LOGGER.error("Unknown Exception saveUssdMenu: " + ussdMenu, e);
			throw new MmbsWebException(e);
		}
	
	}
	
	@Transactional
	public void deleteUssdMenu(int id, UserDataLoginVO loginVO) throws MmbsWebException {
		try {
			LOGGER.info("Processing - > Delete UssdMenu with id: [{}]", id);
			// check if this id has child
			List<UssdMenu> listChild = ussdMenuMapper.findUssdMenuChildById(id);
			if (listChild != null && listChild.size() > 0) {
				LOGGER.warn("UssdMenu [{}] has {} child menu. Cannot be deleted", new String[] { "" + id, "" + listChild.size() } );
				throw new MmbsWebException(MmbsWebException.NE_USSD_MENU_CANNOT_DELETE);
			}
			ussdMenuMapper.deleteUssdMenu(id);
		} catch (MmbsWebException me) {
			throw me;
		} catch (Exception e) {
			LOGGER.error("Unknown Exception deleteUssdMenu " + id,e);
			throw new MmbsWebException(e);
		}
	}
}
