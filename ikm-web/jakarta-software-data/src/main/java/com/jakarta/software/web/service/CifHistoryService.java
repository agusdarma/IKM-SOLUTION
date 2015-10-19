package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.CifHistoryVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.CifHistoryParamVO;
import com.jakarta.software.web.entity.Account;
import com.jakarta.software.web.entity.AccountHistory;
import com.jakarta.software.web.entity.Cif;
import com.jakarta.software.web.entity.CifHistory;
import com.jakarta.software.web.mapper.AccountMapper;
import com.jakarta.software.web.mapper.CifHistoryMapper;
import com.jakarta.software.web.mapper.CifMapper;

@Service
public class CifHistoryService {
	private static final Logger LOG = LoggerFactory.getLogger(CifHistoryService.class);
	
	@Autowired
	private CifHistoryMapper cifHistoryMapper;

	@Autowired
	private CifService cifService;

	@Autowired
	private CifMapper cifMapper;
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private SettingService settingService;
	
	public int countCifHistoryByParam(CifHistoryParamVO cifHistoryParamVO) {
		try {
			cifHistoryParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			LOG.debug("cifHistoryParamVO branchId: " + cifHistoryParamVO.getBranchId());
			LOG.debug("cifHistoryParamVO cifName: " + cifHistoryParamVO.getCifName());
			int count = cifHistoryMapper.countCifHistoryByParam(cifHistoryParamVO);
			LOG.debug("countCifHistoryByParam size: " + count);
			return count;
		} catch (Exception e) {
			LOG.warn("Exception: " + e, e);
			return 0;
		}
	}
	
	public List<CifHistoryParamVO> findCifHistoryByParam(CifHistoryParamVO cifHistoryParamVO) {
		try {
			cifHistoryParamVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			 List<CifHistoryParamVO> listCifHistory = cifHistoryMapper.findCifHistoryByParam(cifHistoryParamVO);
			 LOG.debug("listCifHistory size: " + listCifHistory.size());
			return listCifHistory; 
		} catch (Exception e) {
			LOG.warn("Exception: " + e, e);
			return null;
		}
	}
	
	public CifHistoryVO findCifHistoryDetailById(int cifHistoryId) {
		try {
			CifHistoryVO cifHistoryVO = cifHistoryMapper.findCifHistoryById(cifHistoryId);
			LOG.debug("cifHistoryVO.getHostCifIdOld: " + cifHistoryVO.getHostCifIdOld());
			LOG.debug("cifHistoryVO.getCifNameOld: " + cifHistoryVO.getCifNameOld());
			LOG.debug("cifHistoryVO.getId: " + cifHistoryVO.getCifHistoryId());			
			return cifHistoryVO;
		} catch (Exception e) {
			LOG.warn("Exception: " + e, e);
			return null;
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO insertCifHistoryResetPin(int cifId, UserDataLoginVO loginVO, Locale language) throws MmbsWebException {
		try {
			WebResultVO wrv = new WebResultVO();
			Date now = new Date();
			CifHistory cifHistory = new CifHistory();
			Cif cif = cifService.findCifById(cifId);
			BeanUtils.copyProperties(cif, cifHistory);
			cifHistory.setCreatedBy(loginVO.getId());
			cifHistory.setCreatedOn(now);
			cifHistory.setUpdatedBy(loginVO.getId());
			cifHistory.setUpdatedOn(now);
			cifHistory.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			cifHistory.setActivityType(WebConstants.ACT_TYPE_RESET_PIN);
			cifHistory.setBranchId(loginVO.getBranchId());
			cifHistory.setCifId(cifId);
			cif.setUpdatedBy(loginVO.getId());
			cif.setUpdatedOn(now);
			cif.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
			LOG.debug("insert cif history with object:" + cifHistory);
			cifHistoryMapper.insertCifHistory(cifHistory);
			cifMapper.updateCifAuthStatus(cif);
			List<Account> listAccount = accountMapper.findListAccByCifId(cifId);
			for (Account account : listAccount)
			{
				AccountHistory accHistory = new AccountHistory();
				BeanUtils.copyProperties(account, accHistory);
				accHistory.setCreatedBy(loginVO.getId());
				accHistory.setCreatedOn(now);
				accHistory.setUpdatedBy(loginVO.getId());
				accHistory.setUpdatedOn(now);
				accHistory.setCifIdHistory(cifHistory.getId());
				accHistory.setModifyStatus(WebConstants.ACCOUNT_MODIFY_STATUS_UNCHANGED);
				accountMapper.createAccountHistory(accHistory);	
			}
			/** SET TO USER ACTIVITY **/
			try {
				CifHistory oriCifHistory=new CifHistory();
				Collection<String> excludes = new ArrayList<String>();
				excludes.add("createdOn");
				excludes.add("createdBy");
				excludes.add("updatedOn");
				excludes.add("updatedBy");
				
				userActivityService.generateHistoryActivity(excludes, oriCifHistory, cifHistory, loginVO.getId(), 
						WebConstants.ACT_MODULE_RESET_PIN, WebConstants.ACT_TYPE_INSERT,
						WebConstants.ACT_TABLE_CIF_HISTORY, loginVO.getId());
			} catch (Exception e) 
			{
				LOG.warn("Unable to Create History Activity: " + e.getMessage());
			}		
			/** SET TO USER ACTIVITY **/
			
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setMessage(messageService.getMessageFor("rm.successUpdate",new String[] {messageService.getMessageFor("l.resetPin")}, language));
			wrv.setType(WebConstants.TYPE_UPDATE);
			wrv.setPath(WebConstants.PATH_UPDATE_RESET_PIN);
			if(settingService.getSettingAsInt(SettingService.SETTING_AUTHORIZE_CIF)==WebConstants.YES)
			{
				wrv.setKey1(WebConstants.YES);
				wrv.setKey2(cifHistory.getId());
			}
			else
			{
				wrv.setKey1(WebConstants.NO);
			}
			return wrv;
		} catch (Exception e) {
			LOG.error("error: " + e );
			throw new MmbsWebException(e);
		}
	}
}
