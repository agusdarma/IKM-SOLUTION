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

import com.jakarta.software.web.data.AccessGroupHeaderVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.AccessGroupParamVO;
import com.jakarta.software.web.entity.AccessGroupDetail;
import com.jakarta.software.web.entity.AccessGroupHeader;
import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.mapper.AccessGroupMapper;
import com.jakarta.software.web.utils.CommonUtils;

@Service
public class AccessGroupService {
	private static final Logger LOG = LoggerFactory.getLogger(AccessGroupService.class);
	
	@Autowired
	private AccessGroupMapper accessGroupMapper;
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	public List<AccessGroupHeader> findAllAccessGroup() throws MmbsWebException {
		try {
			List<AccessGroupHeader> listAccessGroup = accessGroupMapper.findAllAccessGroup();
			return listAccessGroup;
		} catch (Exception e) {
			LOG.error("Error find all access group: ", e);
			return new ArrayList<AccessGroupHeader>();
		}
	}
	
	public AccessGroupHeader findAccessById(int id) {
		LOG.debug("Find Access Group by id: {}", id);
		try {
			AccessGroupParamVO paramVO = new AccessGroupParamVO();
			paramVO.setId(id);
			paramVO.setCategoryType(LookupService.CAT_SERVICE_TYPE);
			
			AccessGroupHeader access = accessGroupMapper.findAccessById(paramVO);
			return access;
		} catch (Exception e) {
			LOG.error("Error find access group by id: ", e);
			return null;
		}
	}
	
	public int countByParam(AccessGroupParamVO paramVO) throws MmbsWebException {
		LOG.debug("Processing -> Count access group by Param: [{}]", paramVO.toString());
		try {
			if (paramVO != null) {
				if (!StringUtils.isEmpty(paramVO.getAccessName())) {
					paramVO.setAccessName(paramVO.getAccessName());
				}
			}
			int record = accessGroupMapper.countByParam(paramVO);
			return record;
		} catch (Exception e) {
			LOG.warn("Exception while countByParam: " + e, e);
			return 0;
		}
	}
	
	public int countAccessGroupValidate(String accessName) {
		try {
			int count = accessGroupMapper.countAccessGroupValidate(accessName);
			return count;
		} catch (Exception e) {
			LOG.warn("Exception while countAccessGroupValidate: " + e, e);
			return 0;
		}
	}
	
	public List<AccessGroupHeaderVO> findListAccessByParam(AccessGroupParamVO paramVO) throws MmbsWebException {
		LOG.debug("Processing -> Find list access group by Param: [{}]", paramVO.toString());
		try {
			if (paramVO != null) {
				if (!StringUtils.isEmpty(paramVO.getAccessName())) {
					paramVO.setAccessName(paramVO.getAccessName());
				}
			}
			List<AccessGroupHeaderVO> listAccess = accessGroupMapper.findListAccessByParam(paramVO);
			return listAccess;
		} catch (Exception e) {
			LOG.warn("Exception while findListAccessByParam: ", e);
			throw new MmbsWebException(e);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO saveAccessData(AccessGroupHeader accessGroup, List<String> modulesSelected,
			UserDataLoginVO loginVO, Locale language) throws MmbsWebException {
		LOG.info("Save Access Group data: {}, user: {}", accessGroup.getAccessName(), loginVO.getUserName());
		WebResultVO wrv = new WebResultVO();
		Date now = new Date();
		//accessGroup.setAccessStatus();
		accessGroup.setCreatedBy(loginVO.getId());
		accessGroup.setCreatedOn(now);
		accessGroup.setUpdatedBy(loginVO.getId());
		accessGroup.setUpdatedOn(now);
		
		if (StringUtils.isEmpty(accessGroup.getAccessName())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { messageService.getMessageFor("l.accessName", language) });
		}
		if (modulesSelected == null) {
			throw new MmbsWebException(MmbsWebException.NE_MUST_CHOOSE_MENU);
		}
		
		List <Lookup> modules = constructModules(modulesSelected);
		accessGroup.setListDetail(modules);
		
		try {
			if (accessGroup.getId() == 0) {
				LOG.debug("Create new Access Group: {}", accessGroup.getAccessName());
				int countAccess = countAccessGroupValidate(accessGroup.getAccessName());
				if (countAccess != 0) {
					throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,
							new String[] { messageService.getMessageFor("l.accessName", language) });
				}
				accessGroup.setAccessStatus(WebConstants.STAT_ACTIVE);
				accessGroupMapper.createAccessGroupData(accessGroup);
				
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",
						new String[] { messageService.getMessageFor("l.accessGroup", language),
							messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);	
				
				/** SET TO USER ACTIVITY **/
				try {
					AccessGroupHeader oriAccess = new AccessGroupHeader();
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					excludes.add("listMenu");
					
					userActivityService.generateHistoryActivity(excludes, oriAccess, accessGroup, loginVO.getId(), 
							WebConstants.ACT_MODULE_USER_LEVEL, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_USER_LEVEL, loginVO.getId());
				} catch (Exception e) {
					LOG.warn("Exception while Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/
			} else {
				LOG.debug("Update Access Group with id: {}", accessGroup.getId());
				AccessGroupHeader oriAccess = accessGroupMapper.findAccessHeaderById(accessGroup.getId());
				if (!CommonUtils.compareEqual(accessGroup.getAccessName(), oriAccess.getAccessName())) {
					int countAccess = countAccessGroupValidate(accessGroup.getAccessName());
					if (countAccess != 0) {
						throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,
								new String[] {messageService.getMessageFor("l.accessName", language)});
					}
				}
				accessGroupMapper.updateAccessGroupData(accessGroup);
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",
						new String[] { messageService.getMessageFor("l.accessGroup", language),
							messageService.getMessageFor("l.updated", language)}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);
				wrv.setPath(WebConstants.PATH_UPDATE_ACCESS_GROUP);
				
				/** SET TO USER ACTIVITY **/
				try {
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					excludes.add("listMenu");
					
					userActivityService.generateHistoryActivity(excludes, oriAccess, accessGroup, loginVO.getId(), 
							WebConstants.ACT_MODULE_USER_LEVEL, WebConstants.ACT_TYPE_UPDATE,
							WebConstants.ACT_TABLE_USER_LEVEL, loginVO.getId());
				} catch (Exception e) {
					LOG.warn("Exception while Create History Activity: ", e);
				}		
				/** SET TO USER ACTIVITY **/
			}
			
			accessGroupMapper.deleteAccessGroupDetail(accessGroup.getId());
			// save access detail
			for (Lookup lookup : accessGroup.getListDetail()) {
				AccessGroupDetail detail = new AccessGroupDetail();
				detail.setAccessId(accessGroup.getId());
				detail.setTrxCode(lookup.getLookupValue());
				
				accessGroupMapper.insertAccessGroupDetail(detail);
			}
			
		} catch (MmbsWebException e) {
			LOG.warn("Exception while saveAccessData: ", e);
			throw e;
		} catch (Exception e) {
			LOG.warn("Exception while saveAccessData: ", e);
			throw new MmbsWebException(e);
		}
		
		return wrv;
	}
	
	private List<Lookup> constructModules(List<String> listSelected) {
		List<Lookup> modules = new ArrayList<Lookup>();
		
		// GET ALL TRANSACTIONS
		List<Lookup> allModules = lookupService.findLookupByCat(LookupService.CAT_SERVICE_TYPE);
		
		// ADD TRANSACTIONS
		for (Lookup l : allModules) {
			for (String trxCode : listSelected) {
				if (CommonUtils.compareEqual(trxCode, l.getLookupValue())) {
					modules.add(l);
					break;
				}
			}
		}
		
		return modules;
	}
	
}
