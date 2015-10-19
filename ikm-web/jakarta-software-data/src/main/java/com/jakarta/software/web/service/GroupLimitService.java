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
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.GroupLimitParamVO;
import com.jakarta.software.web.entity.GroupLimitDetail;
import com.jakarta.software.web.entity.GroupLimitHeader;
import com.jakarta.software.web.mapper.GroupLimitMapper;

@Service
public class GroupLimitService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(GroupLimitService.class);
	
	@Autowired
	private GroupLimitMapper groupLimitMapper;
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	public List<GroupLimitHeader> findAllGroupLimit() {
		List<GroupLimitHeader> listGroupLimit = groupLimitMapper.findAllGroupLimit();
		return listGroupLimit;
	}
	
	public List<GroupLimitHeader> findListGroupLimitHeaderByParam(GroupLimitParamVO groupLimitParamVO) {
		List<GroupLimitHeader> listGroupHeader = groupLimitMapper.findListGroupLimitHeaderByParam(groupLimitParamVO);
		if (listGroupHeader == null) {
			listGroupHeader = new ArrayList<GroupLimitHeader>();
		}
		return listGroupHeader;
	}

	public int countListGroupLimitHeaderByParam(GroupLimitParamVO groupLimitParamVO) {
		try {
			int count = groupLimitMapper.countListGroupLimitHeaderByParam(groupLimitParamVO);
			return count;
		} catch (Exception e) {
			return 0;
		}
	}

	public GroupLimitHeader findGroupLimitHeaderById(int groupHeaderId)
	{
		try {
			GroupLimitHeader groupLimitHeader = groupLimitMapper.findGroupLimitHeaderById(groupHeaderId);
			return groupLimitHeader;
		} catch (Exception e) {
			LOGGER.warn("Fail to get GroupLimitHeader By Id Cause : "+e);
			return new GroupLimitHeader();
		}
	}
	
	public List<GroupLimitDetail> findListGroupLimitDetailByHeaderId(int groupHeaderId)
	{
		try {
			List<GroupLimitDetail> listGroupLimitDetail = groupLimitMapper.findListGroupLimitDetailByHeaderId(groupHeaderId);
			return listGroupLimitDetail;
		} catch (Exception e) {
			LOGGER.warn("Fail to get list GroupLimitDetail By header Id Cause : "+e);
			return new ArrayList<GroupLimitDetail>();
		}
	}
	
	public void deleteGroupLimitDetail(int id){
		try {
			groupLimitMapper.deleteGroupLimitDetail(id);
		} catch (Exception e) {
			LOGGER.warn("Fail To Delete Group Limit Detail Cause:" + e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public WebResultVO insertOrUpdateGroupLimitHeader(GroupLimitHeader groupLimitHeader, UserDataLoginVO loginVO, Locale language) throws MmbsWebException {
		WebResultVO wrv = new WebResultVO();
		Date now = new Date();
		groupLimitHeader.setCreatedBy(loginVO.getId());
		groupLimitHeader.setCreatedOn(now);
		groupLimitHeader.setUpdatedBy(loginVO.getId());
		groupLimitHeader.setUpdatedOn(now);
		LOGGER.debug("validate properties group limit header !");
		if(StringUtils.isEmpty(groupLimitHeader.getGroupName())) {
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.groupName", language)});
		}
		
		if(groupLimitHeader.getId()==0)
		{
			int duplicity = groupLimitMapper.findDuplicateName(groupLimitHeader.getGroupName());
			if(duplicity > 0){
				throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.groupName", language)});
			}
				
			try {				
				groupLimitMapper.insertGroupLimitHeader(groupLimitHeader);
				wrv.setRc(0);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("t.groupLimit", language), messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);						
				
				/** SET TO USER ACTIVITY **/
				try {
					GroupLimitHeader oriHeader=new GroupLimitHeader();
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriHeader, groupLimitHeader, loginVO.getId(), 
							WebConstants.ACT_MODULE_GROUP_LIMIT, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_GROUP_LIMIT_HEADER, groupLimitHeader.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/
				
			} catch (Exception e) {
				LOGGER.warn("Gagal insert group limit header:" + e);
				throw new MmbsWebException(e);
			}
		}
		else
		{
			try {
				GroupLimitHeader oriHeader=groupLimitMapper.findGroupLimitHeaderById(groupLimitHeader.getId());
				if(!oriHeader.getGroupName().equalsIgnoreCase(groupLimitHeader.getGroupName()))
				{
					int duplicity = groupLimitMapper.findDuplicateName(groupLimitHeader.getGroupName());
					if(duplicity > 0){
						throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.groupName", language)});
					}
				}
				groupLimitMapper.updateGroupLimitHeader(groupLimitHeader);
				
				/** SET TO USER ACTIVITY **/
				try {
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriHeader, groupLimitHeader, loginVO.getId(), 
							WebConstants.ACT_MODULE_GROUP_LIMIT, WebConstants.ACT_TYPE_UPDATE,
							WebConstants.ACT_TABLE_GROUP_LIMIT_HEADER, groupLimitHeader.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/

				
				wrv.setRc(0);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("t.groupLimit", language), messageService.getMessageFor("l.updated", language)}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);	
				wrv.setPath(WebConstants.PATH_UPDATE_PRODUCT);
			} catch (MmbsWebException mwe) {
				throw mwe;
			} catch (Exception e) {
				LOGGER.warn("Gagal update product service :" + e);
				throw new MmbsWebException(e);
			}
		}
		return wrv;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void insertOrUpdateGroupLimitDetail(GroupLimitDetail groupLimitDetail, UserDataLoginVO loginVO) {
		try {
			Date now =new Date();
			groupLimitDetail.setCreatedBy(loginVO.getId());
			groupLimitDetail.setCreatedOn(now);
			groupLimitDetail.setUpdatedBy(loginVO.getId());
			groupLimitDetail.setUpdatedOn(now);
			if(groupLimitDetail.getId()==0)
			{
				groupLimitMapper.insertGroupLimitDetail(groupLimitDetail);
				
				/** SET TO USER ACTIVITY **/
				try {
					GroupLimitDetail oriDetail=new GroupLimitDetail();
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriDetail, groupLimitDetail, loginVO.getId(), 
							WebConstants.ACT_MODULE_GROUP_LIMIT, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_GROUP_LIMIT_DETAIL, groupLimitDetail.getId());				
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}
			}
			else
			{
				GroupLimitDetail oriDetail=groupLimitMapper.findGroupLimitDetailById(groupLimitDetail.getId());
				if(!oriDetail.getTrxCode().equals(groupLimitDetail.getTrxCode()))
				{
					int duplicity = groupLimitMapper.findDuplicateTrxCode(groupLimitDetail.getTrxCode(), groupLimitDetail.getGroupLimitId());

				}
				groupLimitMapper.updateGroupLimitDetail(groupLimitDetail);
				/** SET TO USER ACTIVITY **/
				try {
					
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriDetail, groupLimitDetail, loginVO.getId(), 
							WebConstants.ACT_MODULE_GROUP_LIMIT, WebConstants.ACT_TYPE_UPDATE,
							WebConstants.ACT_TABLE_GROUP_LIMIT_DETAIL, groupLimitDetail.getId());				
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}
			}
			/** SET TO USER ACTIVITY **/
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
		}
	}
	
	public int checkDuplicateGroupDetail(String trxCode, int headerId)
	{
		try {
			int count = groupLimitMapper.findDuplicateTrxCode(trxCode, headerId);
			return count;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public GroupLimitDetail findGroupLimitDetailById(int detailId)
	{
		try {
			GroupLimitDetail gld = groupLimitMapper.findGroupLimitDetailById(detailId);
			return gld;
		} catch (Exception e) {
			return new GroupLimitDetail();
		}
	}
	
}
