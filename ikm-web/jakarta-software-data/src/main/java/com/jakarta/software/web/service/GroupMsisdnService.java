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

import com.jakarta.software.web.data.GroupMsisdnDetailVO;
import com.jakarta.software.web.data.GroupMsisdnHeaderVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.GroupMsisdnParamVO;
import com.jakarta.software.web.entity.GroupMsisdnDetail;
import com.jakarta.software.web.entity.GroupMsisdnHeader;
import com.jakarta.software.web.mapper.GroupMsisdnMapper;

@Service
public class GroupMsisdnService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupMsisdnService.class);

	@Autowired
	private GroupMsisdnMapper groupMsisdnHeaderMapper;

	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	public List<GroupMsisdnHeader> selectGroupMsisdnHeader()
	{
		List<GroupMsisdnHeader> listGroupMsisdnHeader = groupMsisdnHeaderMapper.selectGroupMsisdnHeader();
		if(listGroupMsisdnHeader==null)
		{
			listGroupMsisdnHeader = new ArrayList<GroupMsisdnHeader>();
		}
		return listGroupMsisdnHeader;
		
	}

	@Transactional(rollbackFor=Exception.class)
	public WebResultVO insertGroupMsisdnHeader(GroupMsisdnHeader groupMsisdnHeader, 
			UserDataLoginVO loginVO, Locale language, String msisdn) throws MmbsWebException 
	{
		WebResultVO wrv = new WebResultVO();
		Date now = new Date();
		groupMsisdnHeader.setCreatedBy(loginVO.getId());
		groupMsisdnHeader.setCreatedOn(now);
		groupMsisdnHeader.setUpdatedBy(loginVO.getId());
		groupMsisdnHeader.setUpdatedOn(now);
		LOGGER.debug("validate properties group msisdn header");
		
		String[] msisdnSpliter = msisdn.split(",");
		List<String> listMsisdn = com.jakarta.software.web.utils.StringUtils.arrayToListStringKillDuplicate(msisdnSpliter);	
		
		if(StringUtils.isEmpty(groupMsisdnHeader.getGroupName())) 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.name")});
		}	

		if(listMsisdn.size()==0)
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.phoneNo")});
		}
		
		if(groupMsisdnHeader.getId()==0)
		{
			int countGroup = groupMsisdnHeaderMapper.validateGroupHeader(groupMsisdnHeader.getGroupName());
			if(countGroup!=0)
			{
				throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.name")});	
			}
			LOGGER.info("Insert Group MSISDN Header: {}, user: {}", groupMsisdnHeader, loginVO.getUserName());
			try {
				groupMsisdnHeaderMapper.insertGroupMsisdnHeader(groupMsisdnHeader);
				GroupMsisdnDetail detail = new GroupMsisdnDetail();
				detail.setGroupId(groupMsisdnHeader.getId());				
				for (String string : listMsisdn) {
					detail.setMsisdn(string);
					groupMsisdnHeaderMapper.insertGroupMsisdnDetail(detail);
				}
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
						new String[] {messageService.getMessageFor("l.groupPhoneNo", language),
						messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);		
				
				/** SET TO USER ACTIVITY FOR HEADER**/
				try {
					GroupMsisdnHeader oriGroup=new GroupMsisdnHeader();
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriGroup, groupMsisdnHeader, loginVO.getId(), 
							WebConstants.ACT_MODULE_GROUP_MSISDN, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_GROUP_MSISDN_HEADER, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY FOR HEADER**/
				
				
				/** SET TO USER ACTIVITY DETAIL**/
				try {
					GroupMsisdnDetailVO oriGroup=new GroupMsisdnDetailVO();
					GroupMsisdnDetailVO currentGroup=new GroupMsisdnDetailVO();
					currentGroup.setGroupId(groupMsisdnHeader.getId());
					currentGroup.setArrayMsisdn(msisdn);
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriGroup, currentGroup, loginVO.getId(), 
							WebConstants.ACT_MODULE_GROUP_MSISDN, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_GROUP_MSISDN_DETAIL, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY DETAIL**/
				
			} catch (Exception e) {
				LOGGER.warn("Insert Group MSISDN Fail :" + e);
				throw new MmbsWebException(e);
			}
		}
		else
		{
			LOGGER.info("Update Group MSISDN header: {}, user: {}", groupMsisdnHeader, loginVO.getUserName());
			try{
				GroupMsisdnHeader oriGroup = selectGroupMsisdnById(groupMsisdnHeader.getId());
				String msisdnOri = selectMsisdnByGroupId(groupMsisdnHeader.getId());
				
				if(!oriGroup.getGroupName().equals(groupMsisdnHeader.getGroupName()))
				{
					int countGroup = groupMsisdnHeaderMapper.validateGroupHeader(groupMsisdnHeader.getGroupName());
					if(countGroup!=0)
					{
						throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.name")});	
					}
				}
				groupMsisdnHeaderMapper.updateGroupMsisdnHeader(groupMsisdnHeader);
				groupMsisdnHeaderMapper.deleteGroupDetailByGroupId(groupMsisdnHeader.getId());
				GroupMsisdnDetail detail = new GroupMsisdnDetail();
				detail.setGroupId(groupMsisdnHeader.getId());	
				for (String string : listMsisdn) {
					detail.setMsisdn(string);
					groupMsisdnHeaderMapper.insertGroupMsisdnDetail(detail);
				}
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
						new String[] {messageService.getMessageFor("l.groupPhoneNo", language),
						messageService.getMessageFor("l.updated", language)}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);
				wrv.setPath(WebConstants.PATH_UPDATE_GROUP_MSISDN);
				
				/** SET TO USER ACTIVITY FOR HEADER**/
				try {
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriGroup, groupMsisdnHeader, loginVO.getId(), 
							WebConstants.ACT_MODULE_GROUP_MSISDN, WebConstants.ACT_TYPE_UPDATE,
							WebConstants.ACT_TABLE_GROUP_MSISDN_HEADER, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY FOR HEADER**/
				
				/** SET TO USER ACTIVITY FOR DETAIL**/
				try {
					GroupMsisdnDetailVO currentGroupDetail=new GroupMsisdnDetailVO();
					currentGroupDetail.setGroupId(groupMsisdnHeader.getId());
					currentGroupDetail.setArrayMsisdn(msisdn.replaceAll(",", "="));
					GroupMsisdnDetailVO oriGroupDetail=new GroupMsisdnDetailVO();
					oriGroupDetail.setGroupId(groupMsisdnHeader.getId());
					oriGroupDetail.setArrayMsisdn(msisdnOri.replaceAll(",", "="));

					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriGroupDetail, currentGroupDetail, loginVO.getId(), 
							WebConstants.ACT_MODULE_GROUP_MSISDN, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_GROUP_MSISDN_DETAIL, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY FOR DETAIL**/
				
			}catch(Exception e){
				LOGGER.warn("Update Group MSISDN Fail :" + e);
				throw new MmbsWebException(e);
			}
		}		
		return wrv;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateGroupMsisdnHeader(GroupMsisdnHeader groupMsisdnHeader)
	{
		try{
			groupMsisdnHeaderMapper.updateGroupMsisdnHeader(groupMsisdnHeader);
		}catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
		}
	}

	public List<GroupMsisdnHeaderVO> selectGroupMsisdnHeaderByParam(GroupMsisdnParamVO groupMsisdnParamVO)
	{
		List<GroupMsisdnHeaderVO> listGroupMsisdnHeader = groupMsisdnHeaderMapper.selectGroupMsisdnHeaderByParam(groupMsisdnParamVO);
		if(listGroupMsisdnHeader==null)
		{
			listGroupMsisdnHeader = new ArrayList<GroupMsisdnHeaderVO>();
		}
		return listGroupMsisdnHeader;
	}

	public int countGroupMsisdnHeaderByParam(GroupMsisdnParamVO groupMsisdnHeaderParamVO)
	{
		try{
			int count = groupMsisdnHeaderMapper.countGroupMsisdnHeaderByParam(groupMsisdnHeaderParamVO);
			return count;
		} catch(Exception e){
			return 0;
		}
	}

	public GroupMsisdnHeader selectGroupMsisdnById(int id)
	{
		return groupMsisdnHeaderMapper.selectGroupMsisdnHeaderById(id);
	}

	public String selectMsisdnByGroupId(int id)
	{
		List<String> listMsisdn = groupMsisdnHeaderMapper.selectListMsisdnByGroupId(id);
		String msisdn="";
		for (String string : listMsisdn) {
			msisdn+=string + ",";
		}
		msisdn=msisdn.substring(0, msisdn.length()-1);
		return msisdn;
	}
}