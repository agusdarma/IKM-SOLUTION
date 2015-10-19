package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.UserLevelMenuVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.UserLevelParamVO;
import com.jakarta.software.web.entity.GroupModule;
import com.jakarta.software.web.entity.UserLevel;
import com.jakarta.software.web.entity.UserLevelMenu;
import com.jakarta.software.web.entity.UserMenu;
import com.jakarta.software.web.mapper.UserLevelMapper;

@Service
public class UserLevelService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLevelService.class);
	
	@Autowired
	private UserLevelMapper userLevelMapper;
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	public List<UserLevel> findListUserLevelByParam(UserLevelParamVO paramVO){
		try {
			 List<UserLevel> listUserLevel = userLevelMapper.findListUserLevelByParam(paramVO);
			return listUserLevel; 
		} catch (Exception e) {
			LOGGER.warn("findListUserLevelByParam Exception: " + e, e);
			return null;
		}
	}

	public Integer countUserLevelByParam(UserLevelParamVO paramVO){
		try {
			int count = userLevelMapper.countUserLevelByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.warn("countUserLevelByParam Exception: " + e, e);
			return 0;
		}
	}
	
	public UserLevel findUserLevelByLevelName(String levelName){
		try {
			 UserLevel userLevel = userLevelMapper.findUserLevelByName(levelName);
			return userLevel; 
		} catch (Exception e) {
			LOGGER.warn("findUserLevelByLevelName Exception: " + e, e);
			return null;
		}
	}

	
	public Integer countUserLevelValidate(String levelName){
		try {
			int count2 = userLevelMapper.countUserLevelValidate(levelName);
			return count2;
		} catch (Exception e) {
			LOGGER.warn("countUserLevelValidate Exception: " + e, e);
			return 0;
		}
	}
	
	public List<UserLevelMenuVO> findModules() {
		List<UserLevelMenuVO> vos = userLevelMapper.findModuleLeaf();
		return vos;
	}
	
	public UserLevel findLevelById(int levelId){
		try {
			 UserLevel userLevel = userLevelMapper.findLevelById(levelId);
			 return userLevel; 
		} catch (Exception e) {
			LOGGER.warn("findLevelById Exception: " + e, e);
			return null;
		}
	}
	
	public void deleteByLevelId(int levelId){
		userLevelMapper.deleteLevelMenuById(levelId);
		userLevelMapper.deleteUserLevelByLevelId(levelId);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO saveLevelData(UserLevel userLevel,List<Integer> modulesSelected, 
			UserDataLoginVO loginVO, Locale language) throws MmbsWebException {
		WebResultVO wrv = new WebResultVO();
		Date now = new Date();
		userLevel.setCreatedBy(loginVO.getId());
		userLevel.setCreatedOn(now);
		userLevel.setUpdatedBy(loginVO.getId());
		userLevel.setUpdatedOn(now);
		
		if(StringUtils.isEmpty(userLevel.getLevelName())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.levelName", language)});
		}
		
		if(modulesSelected==null) {
			throw new MmbsWebException(MmbsWebException.NE_MUST_CHOOSE_MENU);
		}
		
		int[] listSelected = new int[modulesSelected.size()];
		for (int i=0; i<listSelected.length; i++)
		{
			listSelected[i] = modulesSelected.get(i);
		}
		// create group module
		List<UserMenu> modules = constructModules(listSelected);
		userLevel.setListMenu(modules);
			
		try {
			if(userLevel.getId()==0){
				int countUser=countUserLevelValidate(userLevel.getLevelName());
				if(countUser!=0) 
				{
					throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.levelName", language)});
				}		
				LOGGER.info("Insert User Level: {}, user: ", userLevel, loginVO.getUserName());
				userLevelMapper.createUserLevelData(userLevel);
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",
						new String[]{messageService.getMessageFor("l.userLevel", language),
							messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);	
				
				/** SET TO USER ACTIVITY **/
				try {
					UserLevel oriLevel=new UserLevel();
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					excludes.add("listMenu");
					
					userActivityService.generateHistoryActivity(excludes, oriLevel, userLevel, loginVO.getId(), 
							WebConstants.ACT_MODULE_USER_LEVEL, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_USER_LEVEL, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/
				
			} else {
				UserLevel oriLevel=findLevelById(userLevel.getId());
				if(!userLevel.getLevelName().equals(oriLevel.getLevelName()))
				{
					int countUser=countUserLevelValidate(userLevel.getLevelName());
					if(countUser!=0) 
					{
						throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.levelName", language)});
					}		
				}
				LOGGER.info("Update User Level: {}, user: ", userLevel, loginVO.getUserName());
				userLevelMapper.updateUserLevelData(userLevel);
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",
						new String[]{messageService.getMessageFor("l.userLevel", language),
							messageService.getMessageFor("l.updated", language)}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);	
				wrv.setPath(WebConstants.PATH_UPDATE_USER_LEVEL);
				
				/** SET TO USER ACTIVITY **/
				try {
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					excludes.add("listMenu");
					
					userActivityService.generateHistoryActivity(excludes, oriLevel, userLevel, loginVO.getId(), 
							WebConstants.ACT_MODULE_USER_LEVEL, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_USER_LEVEL, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/
			}
			userLevelMapper.deleteUserLevelMenu(userLevel.getId());	
			// save group modules
			for (UserMenu module: modules)
			{
				UserLevelMenu userLevelMenu = new UserLevelMenu();
				userLevelMenu.setLevelId(userLevel.getId());
				userLevelMenu.setMenuId(module.getMenuId());
				userLevelMapper.insertUserLevelMenu(userLevelMenu);
			}	
		}	catch (MmbsWebException mwe) {
			LOGGER.warn("MmbsWebException: " + mwe, mwe);
			throw mwe;
		}	catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			throw new MmbsWebException(e);
		}
		
		return wrv;
	}
	
	private List<UserMenu> constructModules(int[] listSelected) {
		List<UserMenu> modules = new ArrayList<UserMenu>();
		
		// GET ALL MODULES
		List<UserMenu> allModules = userLevelMapper.findUserModulesAll();

		// ADD MODULES
		for (UserMenu m : allModules) {
			if (m.getAlwaysInclude() == WebConstants.DB_TRUE){
				modules.add(m);
			} else {
				for (int moduleId : listSelected){
					if (moduleId == m.getMenuId()){
						modules.add(m);
						break;
					}						
				}	
			}
		}

		// ADD PARENTS
		UserMenu[] leafModules = modules.toArray(new UserMenu[0]);
		for (UserMenu m : leafModules) {
			int parentId = m.getParentId();
			while (parentId > 0) {
				// check if parentId already in modules
				if (getModule(modules, parentId) != null)
					break;

				UserMenu parentModule = getModule(allModules, parentId);
				if (parentModule == null)
					break;
				modules.add(parentModule);				
				parentId = parentModule.getParentId();
			} // end while parent exists
		} // end for -> looping modules

		return modules;
	}
	
	private UserMenu getModule(List<UserMenu> modules, int moduleId) {
		for (UserMenu m : modules) {
			if (m.getMenuId() == moduleId)
				return m;
		}
		return null;
	}

	public List<UserLevel> getAllUserLevel() {
		List<UserLevel> allUserLevel = userLevelMapper.getAllUserLevel();
		return allUserLevel;
	}
	
	public int checkMenuByLevelIdAndMenuId(int menuId, int levelId)
	{
		int counter = userLevelMapper.checkMenuByLevelIdAndMenuId(menuId, levelId);
		return counter;
	}
	
}
