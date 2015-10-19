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

import com.jakarta.software.web.data.PushRequestVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.PushRequestParamVO;
import com.jakarta.software.web.entity.PushRequestDetail;
import com.jakarta.software.web.entity.PushRequestHeader;
import com.jakarta.software.web.mapper.GroupMsisdnMapper;
import com.jakarta.software.web.mapper.PushRequestMapper;

@Service
public class PushRequestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PushRequestService.class);

	@Autowired
	private GroupMsisdnMapper groupMsisdnHeaderMapper;

	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private PushRequestMapper pushRequestMapper;

	public List<PushRequestVO> selectPushRequestHeaderByParam(PushRequestParamVO pushRequestParamVO)
	{
		List<PushRequestVO> listPushRequestHeader = pushRequestMapper.selectPushRequestHeaderByParam(pushRequestParamVO);
		if(listPushRequestHeader==null)
		{
			listPushRequestHeader = new ArrayList<PushRequestVO>();
		}
		return listPushRequestHeader;
	}

	public int countPushRequestHeaderByParam(PushRequestParamVO pushRequestParamVO)
	{
		try{
			int count = pushRequestMapper.countPushRequestHeaderByParam(pushRequestParamVO);
			return count;
		} catch(Exception e){
			return 0;
		}
	}

	public PushRequestVO selectPushRequestHeaderById(int id)
	{
 		PushRequestVO pushRequestHeader = pushRequestMapper.selectPushRequestHeaderById(id);
		if(pushRequestHeader==null)
		{
			pushRequestHeader = new PushRequestVO();
		}
		return pushRequestHeader;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO insertPushRequest(PushRequestHeader pushRequestHeader, 
			UserDataLoginVO loginVO, Locale language, String msisdn) throws MmbsWebException 
	{
		WebResultVO wrv = new WebResultVO();
		Date now = new Date();
		pushRequestHeader.setCreatedBy(loginVO.getId());
		pushRequestHeader.setCreatedOn(now);
		pushRequestHeader.setUpdatedBy(loginVO.getId());
		pushRequestHeader.setUpdatedOn(now);
		LOGGER.debug("validate properties Push Request");
		
		if(pushRequestHeader.getGroupMsisdnId()==0)
		{
			if(StringUtils.isEmpty(msisdn))
			{
				throw new MmbsWebException(MmbsWebException.NE_MUST_FILL_GROUP_OR_MSISDN);
			}
		}
		if(pushRequestHeader.getSentTime()==null) 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.sendTime")});
		}	
		
		if(StringUtils.isEmpty(pushRequestHeader.getSubject()))
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.subject")});
		}
		
		if(StringUtils.isEmpty(pushRequestHeader.getMessage()))
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.remarks")});
		}
		
		if(pushRequestHeader.getId()==0)
		{
			LOGGER.info("Insert Push Header: {}, additional msisdn: {}, user: " + loginVO.getUserName(), pushRequestHeader, msisdn);
			try {
				pushRequestMapper.insertPushRequestHeader(pushRequestHeader);
				PushRequestDetail detail = new PushRequestDetail();
				detail.setHeaderId(pushRequestHeader.getId());
				if(pushRequestHeader.getGroupMsisdnId() > 0)
				{
					detail.setGroupMsisdnId(pushRequestHeader.getGroupMsisdnId());
					List<String> listMsisdnGroup=groupMsisdnHeaderMapper.selectListMsisdnByGroupId(pushRequestHeader.getGroupMsisdnId());
					for (String string : listMsisdnGroup) {
						detail.setMsisdn(string);
						pushRequestMapper.insertPushRequestDetail(detail);
					}
					if(!StringUtils.isEmpty(msisdn))
					{						
						String[] msisdnSpliter = msisdn.split(",");
						List<String> listMsisdn = com.jakarta.software.web.utils.StringUtils.arrayToListStringKillDuplicate(msisdnSpliter);	
						detail.setGroupMsisdnId(0);
						for (String string : listMsisdn)
						{
							int count = 0 ;
							detail.setMsisdn(string);
							for (String msisdnGroup : listMsisdnGroup)
							{
								if(string.equals(msisdnGroup))
								{
									count++;
									break;
								}
							}
							if(count==0)
							{
								pushRequestMapper.insertPushRequestDetail(detail);
							}
						}
					}
				}
				else
				{
					String[] msisdnSpliter = msisdn.split(",");
					List<String> listMsisdn = com.jakarta.software.web.utils.StringUtils.arrayToListStringKillDuplicate(msisdnSpliter);	
					detail.setGroupMsisdnId(0);
					for (String string : listMsisdn) {
						detail.setMsisdn(string);
						pushRequestMapper.insertPushRequestDetail(detail);
					}
				}

				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
						new String[] {messageService.getMessageFor("l.sendSms", language),
						messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);		
				
				/** SET TO USER ACTIVITY FOR HEADER**/
				try {
					PushRequestHeader oriRequest=new PushRequestHeader();
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriRequest, pushRequestHeader, loginVO.getId(), 
							WebConstants.ACT_MODULE_SEND_SMS, WebConstants.ACT_TYPE_INSERT,
							WebConstants.ACT_TABLE_PUSH_REQUEST_HEADER, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY FOR HEADER**/
				
			} catch (Exception e) {
				LOGGER.warn("Insert Group MSISDN Fail :" + e);
				throw new MmbsWebException(e);
			}
		}
		else
		{
			LOGGER.info("Update Push Header: {}, additional msisdn: {}, user: " + loginVO.getUserName(), pushRequestHeader, msisdn);
			try{
				PushRequestHeader oriRequestHeader = pushRequestMapper.selectPushRequestHeaderById(pushRequestHeader.getId());
				pushRequestMapper.updatePushRequestHeader(pushRequestHeader);
				pushRequestMapper.deletePushRequestDetailByHeaderId(pushRequestHeader.getId());
				PushRequestDetail detail = new PushRequestDetail();
				detail.setHeaderId(pushRequestHeader.getId());
				detail.setStatus(WebConstants.STATUS_SEND_SMS_START);
				
				if(pushRequestHeader.getGroupMsisdnId() > 0)
				{
					detail.setGroupMsisdnId(pushRequestHeader.getGroupMsisdnId());
					List<String> listMsisdnGroup=groupMsisdnHeaderMapper.selectListMsisdnByGroupId(pushRequestHeader.getGroupMsisdnId());
					for (String string : listMsisdnGroup) {
						detail.setMsisdn(string);
						pushRequestMapper.insertPushRequestDetail(detail);
					}
				}
				
				if(!StringUtils.isEmpty(msisdn))
				{
					String[] msisdnSpliter = msisdn.split(",");
					List<String> listMsisdn = com.jakarta.software.web.utils.StringUtils.arrayToListStringKillDuplicate(msisdnSpliter);	
					detail.setGroupMsisdnId(0);
					for (String string : listMsisdn) {
						detail.setMsisdn(string);
						pushRequestMapper.insertPushRequestDetail(detail);
					}
				}
			
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
						new String[] {messageService.getMessageFor("l.sendSms", language),
						messageService.getMessageFor("l.updated", language)}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);
				wrv.setPath(WebConstants.PATH_UPDATE_SEND_SMS);
				
				/** SET TO USER ACTIVITY FOR HEADER**/
				try {
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, oriRequestHeader, pushRequestHeader, loginVO.getId(), 
							WebConstants.ACT_MODULE_SEND_SMS, WebConstants.ACT_TYPE_UPDATE,
							WebConstants.ACT_TABLE_PUSH_REQUEST_HEADER, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY FOR HEADER**/
								
			}catch(Exception e){
				LOGGER.warn("Update Send SMS Fail :" + e);
				throw new MmbsWebException(e);
			}
		}		
		return wrv;
	}
	
	public String selectRequestDetailByHeaderId(int id)
	{
		List<String> listMsisdn = pushRequestMapper.selectListMsisdnByHeaderId(id);
		String msisdn="";
		if(listMsisdn.size() > 0)
		{
			for (String string : listMsisdn) {
				msisdn+=string + ",";
			}
			msisdn=msisdn.substring(0, msisdn.length()-1);
		}
		return msisdn;
	}
	
	public List<PushRequestVO> selectListPushRequestDetailByHeaderId(int id)
	{
		List<PushRequestVO> listPushRequestVO = pushRequestMapper.selectListPushRequestDetailByHeaderId(id);
		return listPushRequestVO;
	}
	
}