package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.AccountResponseVO;
import com.jakarta.software.web.data.CifEngineRequestVO;
import com.jakarta.software.web.data.CifHistoryVO;
import com.jakarta.software.web.data.CifVO;
import com.jakarta.software.web.data.EngineResponseVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebAccountRequestVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.CifAuthParamVO;
import com.jakarta.software.web.data.param.CifParamVO;
import com.jakarta.software.web.entity.Account;
import com.jakarta.software.web.entity.AccountHistory;
import com.jakarta.software.web.entity.Cif;
import com.jakarta.software.web.entity.CifHistory;
import com.jakarta.software.web.mapper.AccountHistoryMapper;
import com.jakarta.software.web.mapper.AccountMapper;
import com.jakarta.software.web.mapper.CifHistoryMapper;
import com.jakarta.software.web.mapper.CifMapper;
import com.jakarta.software.web.utils.Constants;
import com.jakarta.software.web.utils.DateUtils;
import com.jakarta.software.web.utils.SecureUtils;

@Service
public class CifService {
	private static final Logger LOG = LoggerFactory.getLogger(CifService.class);

	@Autowired
	private CifMapper cifMapper;
	
	@Autowired
	private CifHistoryMapper cifHistoryMapper;

	@Autowired
	private AccountHistoryMapper accountHistMapper;
	
	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private HttpClientService httpClientService;

	@Autowired
	private LookupService lookupService;

	@Autowired
	private BizMessageService bizMessageService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private UserActivityService userActivityService;

	@Autowired
	private EngineService engineService;
	
	private ObjectMapper mapper;
	
	public CifService() {
		mapper = new ObjectMapper();
	}

	public AccountResponseVO inquiryCifByAccountNo(String cardNo, String accountNo, Locale language) {
		LOG.info("Processing -> Inquiry Cif By Account No: [{}], Card No: {}", accountNo, cardNo);
		AccountResponseVO arv = null;
		try {
			if (StringUtils.isEmpty(accountNo)) {
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
						new String[] { bizMessageService.getMessageFor(
								"l.accountNumber", language) });
			}
			if (!StringUtils.isNumeric(accountNo)) {
				throw new MmbsWebException(
						MmbsWebException.NE_INVALID_TYPE_NUMERIC,
						new String[] { bizMessageService.getMessageFor(
								"l.accountNumber", language) });
			}

			try {
				arv = engineService.sendToEngineForInqAcc(cardNo, accountNo);
				LOG.debug("AccountResponseVO Result: {}", arv);
				return arv;
			} catch (Exception e) {
				LOG.error("Error Exception: " + e, e);
				arv = new AccountResponseVO();
				arv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
				arv.setErrorMessage(e.getMessage());
				return arv;
			}

		} catch (MmbsWebException e) {
			arv = new AccountResponseVO();
			arv.setResultCode("" + e.getErrorCode());
			arv.setErrorMessage(e.getMessage());
			return arv;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			arv = new AccountResponseVO();
			arv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
			arv.setErrorMessage(e.getMessage());
			return arv;
		}
	}

	public WebResultVO updateCifData(CifHistoryVO cifHistoryVO, UserDataLoginVO loginVO, Locale language) throws MmbsWebException {
		LOG.info("updateCifData: [{}]", cifHistoryVO.getListAccount());

		LOG.debug("Processing -> Update CIF Id: [{]}", cifHistoryVO);
		WebResultVO wrv = new WebResultVO();
		//send to engine if cif terminate and reset pin with difference trxcode
		if (WebConstants.ACT_TYPE_RESET_PIN.equals(cifHistoryVO.getActivityType()))
		{
			LOG.debug("Processing -> Reset PIN CIF send to engine : [{}]",cifHistoryVO.getCifId());
			wrv = engineService.sendEngineCif(cifHistoryVO.getCifHistoryId(), cifHistoryVO.getDeviceCode(), loginVO, language, 
					Constants.TRX_CODE_RESET_PIN, Constants.STATE_CIF_REG_SUBMIT);
			return wrv;
		}

		Cif cif = (Cif) cifHistoryVO;
		int cifHistId = cifHistoryVO.getCifHistoryId();
		LOG.debug("Processing -> Update CIF Approved: [{}]", cif);
		try {
			Date current = new Date();
			
			/* update change data cif only */
			cif.setId(cifHistoryVO.getCifId());
			cif.setDeviceCode(cifHistoryVO.getDeviceCode());
			cif.setHostCifId(cifHistoryVO.getHostCifId());
			cif.setBranchId(cifHistoryVO.getBranchId());
			cif.setCifName(cifHistoryVO.getCifName());
			cif.setGroup(cifHistoryVO.getGroup());
			cif.setIdentityCode(cifHistoryVO.getIdentityCode());
			cif.setAuthStatus(cifHistoryVO.getAuthStatus());
			cif.setStatus(cifHistoryVO.getStatus());
			cif.setUseBlastSms(cifHistoryVO.getUseBlastSms());
			cif.setMotherName(cifHistoryVO.getMotherName());
			cif.setBirthDate(cifHistoryVO.getBirthDate());
			cif.setAddress(cifHistoryVO.getAddress());
			cif.setRemarks(cifHistoryVO.getRemarks());
			cif.setAccessId(cifHistoryVO.getAccessId());
			cif.setAuthOn(current);
			cif.setAuthBy(loginVO.getId());
			cif.setUpdatedOn(cifHistoryVO.getCreatedOn());
			cif.setUpdatedBy(cifHistoryVO.getCreatedBy());
			cif.setPinCount(cifHistoryVO.getPinCount());
			if(cifHistoryVO.getStatusOld().equals(Constants.STATUS_BLOCK)&&cifHistoryVO.getStatus().equals(Constants.STATUS_ACTIVE))
			{
				cif.setPinCount(0);
			}
			// delete and re-create new account for particular cif id
			List<AccountHistory> listCurrentAccount = accountHistMapper.findListAccountHistoryByCifHistoryId(cifHistoryVO.getParentId());
			
			deleteAccount(cifHistoryVO.getCifId());
			//accountMapper.deleteAccount(cifHistoryVO.getCifId());
			
			if (WebConstants.ACT_TYPE_TERMINATE.equals(cifHistoryVO.getActivityType())) 
			{
				cifMapper.deleteCif(cifHistoryVO.getCifId());
			}
			else
			{
				cifMapper.updateCif(cif);
				for (AccountHistory accountHist : cifHistoryVO.getListAccountHistory())
				{
					int counterAccount = 0;
					for (AccountHistory currentAccount : listCurrentAccount) 
					{
						if(currentAccount.getAccountNo().equals(accountHist.getAccountNo()))
						{
							counterAccount=counterAccount+1;
							if(!currentAccount.getCardNo().equals(accountHist.getCardNo())||
									!currentAccount.getStatus().equals(accountHist.getStatus())||
									currentAccount.getAccIndex()!=accountHist.getAccIndex()||
									!currentAccount.getRemarks().equals(accountHist.getRemarks()))
							{
								accountHist.setModifyStatus(WebConstants.ACCOUNT_MODIFY_STATUS_CHANGED);
							}
							else
							{
								accountHist.setModifyStatus(WebConstants.ACCOUNT_MODIFY_STATUS_UNCHANGED);
							}
						}
						
					}
					if(counterAccount==0)
					{
						accountHist.setModifyStatus(WebConstants.ACCOUNT_MODIFY_STATUS_NEW);
					}
					//LOG.info("trace 4: ", accountHist);
					Account accCif = new Account();
					accCif.setCifId(cifHistoryVO.getCifId());
					accCif.setAccountNo(accountHist.getAccountNo());
					accCif.setCardNo(accountHist.getCardNo());
					accCif.setStatus(accountHist.getStatus());
					accCif.setRemarks(accountHist.getRemarks());
					accCif.setAccIndex(Integer.toString(accountHist.getAccIndex()));
					accCif.setCreatedOn(new Date());
					accCif.setCreatedBy(accountHist.getCreatedBy());
					accCif.setUpdatedOn(new Date());
					accCif.setUpdatedBy(accountHist.getUpdatedBy());
					accountMapper.createAccount(accCif);
				}				
				
			}			
			//check removed account
			for (AccountHistory currentAccount : listCurrentAccount) {
				int counterAccount = 0;
				for (AccountHistory accountHist : cifHistoryVO.getListAccountHistory())
				{
					if(currentAccount.getAccountNo().equals(accountHist.getAccountNo()))
					{
						counterAccount=counterAccount+1;
					}
				}
				if(counterAccount==0)
				{
					currentAccount.setModifyStatus(WebConstants.ACCOUNT_MODIFY_STATUS_REMOVED);
					cifHistoryVO.getListAccountHistory().add(currentAccount);
				}
			}
			
			for (AccountHistory accountHist : cifHistoryVO.getListAccountHistory())
			{
				if(!accountHist.getModifyStatus().equals(WebConstants.ACCOUNT_MODIFY_STATUS_REMOVED))
				{
					accountHistMapper.updateAccountHistory(accountHist);
				}
				else
				{
					accountHistMapper.createAccountHistory(accountHist);
				}
			}			
			cifHistoryMapper.changeCifHistoryAuthStatus(cifHistoryVO);
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}

		try {
			wrv.setRc(WebConstants.RESULT_SUCCESS);

			wrv.setMessage(bizMessageService.getMessageFor("rm.authCifSuccessUpdate",
					new String[] { bizMessageService.getMessageFor("l.cif",language),
					bizMessageService.getMessageFor("l.approved", language)}, language));
			LOG.debug("Processing -> Update CIF setMessage: [{}]", wrv.getMessage());
			wrv.setType(WebConstants.TYPE_UPDATE);
		} catch (Exception e) {
			LOG.warn("Unexpected error when update cif data", e);
			throw new MmbsWebException(MmbsWebException.NE_UNKNOWN_ERROR,
					e.getMessage());
		}
		return wrv;
	}
	
	public WebResultVO updateCifResetPin(Cif cif, UserDataLoginVO loginVO, 
			Locale language) throws MmbsWebException {
		LOG.debug("Processing -> CIF Reset PIN: [{}]", cif.toString());
		WebResultVO wrv = null;
		try {
			/* PIN validation */
			validationInputPin(cif);
			
			Cif cifOri = cifMapper.findCifById(cif.getId());
			
			Date current = new Date();
			// Copy hidden data from query result
			cif.setLanguage(cifOri.getLanguage());
			cif.setGroup(cifOri.getGroup());
			cif.setHasstk(cifOri.getHasstk());
			cif.setStatus(cifOri.getStatus());
			cif.setAuthStatus(Constants.STATUS_ACTIVE_STRING);
			cif.setUpdatedOn(current);
			cif.setUpdatedBy(loginVO.getId());
			cif.setListAccount(cifOri.getListAccount());
		
			wrv = engineService.sendEngineCif(cif.getId(), cif.getDeviceCode(), loginVO, language, Constants.TRX_CODE_RESET_PIN, Constants.STATE_CIF_REG_SUBMIT);
		} catch (MmbsWebException e) {
			throw e;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
		return wrv;
	}

	@Transactional(rollbackFor = Exception.class)
	public WebResultVO createOrUpdate(Cif cif, UserDataLoginVO loginVO, Locale language) throws MmbsWebException
	{
		LOG.debug("Create or Update cif: " + cif);
		WebResultVO wrv = new WebResultVO();
		try {
			/* validation input */
			validationInput(cif);

			Date current = new Date();
			cif.setUpdatedBy(loginVO.getId());
			cif.setUpdatedOn(current);
			cif.setAuthStatus(Constants.AUTH_STATUS_PENDING);

			Collection<String> excludes = new ArrayList<String>();
			excludes.add("updatedBy");
			excludes.add("updatedOn");
			excludes.add("createdBy");
			excludes.add("createdOn");
			excludes.add("cardNo");
			excludes.add("listAccount.updatedBy");
			excludes.add("listAccount.updatedOn");
			excludes.add("listAccount.createdBy");
			excludes.add("listAccount.createdOn");
			excludes.add("listAccount.accountTypeDisplay");
			excludes.add("listAccount.cardTypeDisplay");

			if (cif.getId() == 0)
			{
				/* send to engine */
				LOG.info("Processing -> Send Engine Create CIF: [{}]", cif);
				//sendToEngineForCreateCif(cif, loginVO, confirmPin, language);

				CifHistory cifHistory = new CifHistory();
				insertCifHistory(cif, loginVO, cifHistory);

				/*
				 * added user activity
				 */
				Cif cifNew = new Cif();
				List<Account> listAccount = new ArrayList<Account>();
				cifNew.setListAccount(listAccount);

				userActivityService.generateHistoryActivity(excludes, cif,
						cifNew, loginVO.getId(),
						WebConstants.ACT_MODULE_CUSTOMER_INFO,
						WebConstants.ACT_TYPE_INSERT,
						WebConstants.ACT_TABLE_CIF_HISTORY, loginVO.getId());
				/*
				 * added user activity
				 */

				wrv.setRc(0);
				wrv.setMessage(bizMessageService.getMessageFor("rm.successInsert",
						new String[] { bizMessageService.getMessageFor("l.cif",
								language) }, language));
				wrv.setType(WebConstants.TYPE_INSERT);
				if(settingService.getSettingAsInt(SettingService.SETTING_AUTHORIZE_CIF)==WebConstants.YES)
				{
					wrv.setKey1(WebConstants.YES);
					wrv.setKey2(cifHistory.getId());
				}
				else
				{
					wrv.setKey1(WebConstants.NO);
				}
			} 
			else 
			{
				/*
				 * cif and account remarks set empty b'cause when authorize need
				 * to filled remarks
				 */
				/* cif remarks = account.remarks */
				Cif cifOri = cifMapper.findCifById(cif.getId());
				
				// Copy hidden data from query result
				cif.setLanguage(cifOri.getLanguage());
				cif.setHasstk(cifOri.getHasstk());
				cif.setCreatedOn(cifOri.getCreatedOn());
				cif.setCreatedBy(cifOri.getCreatedBy());
				cif.setAuthOn(cifOri.getAuthOn());
				cif.setAuthBy(cifOri.getAuthBy());

				cif.setRemarks("");
				LOG.info("Processing -> Update CIF: [{}]", cif);
				cifMapper.updateCifAuthStatus(cif);

				CifHistory cifHistory = new CifHistory();
				insertCifHistory(cif, loginVO, cifHistory);

				/*
				 * added user activity
 				 */

				userActivityService.generateHistoryActivity(excludes, cifOri,
						cif, loginVO.getId(),
						WebConstants.ACT_MODULE_CUSTOMER_INFO,
						WebConstants.ACT_TYPE_INSERT,
						WebConstants.ACT_TABLE_CIF_HISTORY, loginVO.getId());

				/*
				 * added history activity
				 */
				wrv.setRc(0);
				if(settingService.getSettingAsInt(SettingService.SETTING_AUTHORIZE_CIF)==WebConstants.YES)
				{
					wrv.setKey1(WebConstants.YES);
					wrv.setKey2(cifHistory.getId());
				}
				else
				{
					wrv.setKey1(WebConstants.NO);
				}
				wrv.setMessage(bizMessageService.getMessageFor("rm.successUpdate",
						new String[] { bizMessageService.getMessageFor("l.cif",
								language) }, language));
				wrv.setType(WebConstants.TYPE_UPDATE);
				wrv.setPath(WebConstants.PATH_UPDATE_CIF);
			}
			return wrv;
		} catch (MmbsWebException e) {
			throw e;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateCifReject(Cif cif, CifHistoryVO cifHistoryVO,
			UserDataLoginVO loginVO) throws MmbsWebException {
		LOG.debug("Processing -> Update CIF Reject: [{}]", cif);
		try {
			/* update change data cif only */
			cif.setEmail(cifHistoryVO.getEmailOld());
			cif.setGroup(cifHistoryVO.getCifGroupOld());
			cif.setAccessId(cifHistoryVO.getAccessIdOld());
			cif.setStatus(cifHistoryVO.getMobileStatusOld());
			cif.setPinCount(cifHistoryVO.getPinCountOld());
			cif.setUseBlastSms(cifHistoryVO.getUseBlastSmsOld());
			cifMapper.updateCif(cif);
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}
	@Transactional(rollbackFor = Exception.class)
	public void insertCifHistory(Cif cif, UserDataLoginVO loginVO, CifHistory cifHistory)
			throws MmbsWebException {
		LOG.debug("Processing -> InsertCifHistory() Begin");
		try {
			Date current = new Date();
			
			if(cif.getId()!=0)
			{
				int parentId=cifHistoryMapper.findHistoryParentIdByCifId(cif.getId());
				cifHistory.setParentId(parentId);
				cifHistory.setStatus(cif.getStatus());
			}
			else
			{
				cifHistory.setParentId(0);
				cifHistory.setStatus(WebConstants.STAT_ACTIVE);
			}
			cifHistory.setCifId(cif.getId());
			cifHistory.setDeviceCode(cif.getDeviceCode());
			cifHistory.setAccessId(cif.getAccessId());
			cifHistory.setHostCifId(cif.getHostCifId());
			cifHistory.setCifName(cif.getCifName());
			cifHistory.setIdentityCode(cif.getIdentityCode());
			cifHistory.setCifLang(cif.getLanguage());
			cifHistory.setCifGroup(cif.getGroup());
			cifHistory.setPin(cif.getPin());
			cifHistory.setHasstk(cif.getHasstk());
			cifHistory.setActivationDate(cif.getActivationDate());
			cifHistory.setPinChangeDate(cif.getPinChangeDate());
			cifHistory.setCifLang(WebConstants.DEFAULT_CIF_LANGUAGE);
			cifHistory.setRemarks(cif.getRemarks());
			cifHistory.setEmail(cif.getEmail());
			cifHistory.setUseBlastSms(cif.getUseBlastSms());
			cifHistory.setCreatedOn(current);
			cifHistory.setCreatedBy(loginVO.getId());
			cifHistory.setUpdatedOn(current);
			cifHistory.setUpdatedBy(loginVO.getId());
			cifHistory.setAuthStatus(Constants.AUTH_STATUS_PENDING);
			cifHistory.setBranchId(loginVO.getBranchId());
			cifHistory.setMotherName(cif.getMotherName());
			cifHistory.setBirthDate(cif.getBirthDate());
			cifHistory.setAddress(cif.getAddress());
			if (WebConstants.STATUS_CIF_TERMINATE.equals(cif.getStatus()))
			{
				cifHistory.setActivityType(WebConstants.ACT_TYPE_TERMINATE);
			}
			else
			{
				if(cif.getId()==0)
				{
					cifHistory.setActivityType(WebConstants.ACT_TYPE_REGISTER);
				}
				else
				{
					cifHistory.setActivityType(WebConstants.ACT_TYPE_UPDATE);
				}
			}
			cifHistoryMapper.insertCifHistory(cifHistory);
			for (Account account : cif.getListAccount()) 
			{
				if(account.getAccIndex()!=0)
				{
					AccountHistory accountHistory = new AccountHistory();			
					accountHistory.setCifIdHistory(cifHistory.getId());
					accountHistory.setAccountNo(account.getAccountNo());
					accountHistory.setAccountType(WebConstants.ACCOUNT_TYPE_TABUNGAN);
					accountHistory.setAccIndex(account.getAccIndex());
					accountHistory.setRemarks(account.getRemarks());
					accountHistory.setCardNo(account.getCardNo());
					accountHistory.setStatus(Constants.STATUS_ACTIVE_STRING);
					if (cif.getId() == 0)
					{
						accountHistory.setModifyStatus(WebConstants.ACCOUNT_MODIFY_STATUS_NEW);
					}
					else
					{
						if(cif.getStatus().equals(WebConstants.STATUS_CIF_TERMINATE))
						{
							accountHistory.setModifyStatus(WebConstants.ACCOUNT_MODIFY_STATUS_REMOVED);
						}
						else
						{
							accountHistory.setModifyStatus(WebConstants.ACCOUNT_MODIFY_STATUS_UNCHANGED);
						}
					}
					accountHistory.setCreatedOn(current);
					accountHistory.setCreatedBy(loginVO.getId());
					accountHistory.setUpdatedOn(current);
					accountHistory.setUpdatedBy(loginVO.getId());
					
					accountHistMapper.createAccountHistory(accountHistory);
				}
			}
			
			LOG.debug("Processing -> InsertCifHistory() Success");
		} catch (Exception e) {
			LOG.error("ERROR: " + e, e);
			throw new MmbsWebException(MmbsWebException.NE_UNKNOWN_ERROR);
		}
	}
	
	public void validationInput(Cif cif) throws MmbsWebException {
		if (StringUtils.isEmpty(cif.getAddress())) 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { bizMessageService.getMessageFor("l.address") });
		}

		if (cif.getBirthDate()==null) 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { bizMessageService.getMessageFor("l.birthDate") });
		}
		
		if (StringUtils.isEmpty(cif.getMotherName())) 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { bizMessageService.getMessageFor("l.motherName") });
		}
		
		if (StringUtils.isEmpty(cif.getDeviceCode())) 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { bizMessageService.getMessageFor("l.cifDeviceCode") });
		} 
		else 
		{
			cif.setDeviceCode(com.jakarta.software.web.utils.StringUtils.formatPhone(cif.getDeviceCode()));

			if (!cif.getDeviceCode().matches("^\\+62[1-9][0-9]{7,18}$")) 
			{
				throw new MmbsWebException(MmbsWebException.NE_CIF_INVALID_PHONE_NO);
			}
		}

		if (StringUtils.isEmpty(cif.getCifName())) 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { bizMessageService.getMessageFor("l.cifName") });
		}

		/* COMMENT BY ANTO
		 * COMMENT DATE : 15 - 09 - 2015
		 * REASON: BECAUSE EMAIL IS NOT MANDATORY @ SUMUT (PLEASE UNCOMMENT IF MANDATORY)
		 * if (!StringUtils.isEmpty(cif.getEmail())) 
		{
			*//** ex: from mkyong **//*
			if (!cif.getEmail().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) 
			{
				throw new MmbsWebException(MmbsWebException.NE_CIF_INVALID_EMAIL, 
						new String[] { bizMessageService.getMessageFor("l.cifEmail") });
			}
		} 
		else 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { bizMessageService.getMessageFor("l.cifEmail") });
		}
		*/
		
		if (StringUtils.isEmpty(cif.getIdentityCode())) 
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
					new String[] { bizMessageService.getMessageFor("l.cifIdentityCode") });
		}
		
		checkDuplicateMobilePhone(cif.getId(), cif.getDeviceCode());

		
		List<Integer> indexList = new ArrayList<Integer>();
		if(cif.getListAccount().size()==0)
		{
			throw new MmbsWebException(MmbsWebException.NE_CIF_MUST_HAVE_MINIMUM_ONE_ACCOUNT);
		}
		else
		{
			for (Account account : cif.getListAccount()) 			
			{
				if (account.getAccIndex() > 0) 
				{
					if(indexList.size() > 0) /* check for duplicate index */
					{
						for (Integer index : indexList)  
						{
							if(index==account.getAccIndex())
							{
								throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA, 
										new String[] { bizMessageService.getMessageFor("l.accountIndex") });
							}
						}
					}					
					if(account.getAccIndex()==1)
					{			
						if(account.getCardNo().length()==0)
						{
							throw new MmbsWebException(MmbsWebException.NE_CIF_PRIMARY_INDEX_MUST_HAVE_CARD_NO);
						}
					}
				}				
				indexList.add(account.getAccIndex());
			}
			if(!indexList.contains(1))
			{
				throw new MmbsWebException(MmbsWebException.NE_CIF_MUST_HAVE_PRIMARY_INDEX);
			}
		}
		

		/* check if there is a missing index */
		/*for (int i = 0; i < indexList.size(); i++) {
			if (!indexList.contains(i + 1)) {
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
						new String[] { bizMessageService.getMessageFor("l.accountIndex") });
			}
		}*/
		
		/* SUMUT TIDAK INPUT PIN
		 * if (cif.getId() == 0) {
			validationInputPin(cif);
		}*/
	}

	public void validationInputPin(Cif cif) throws MmbsWebException {
		try {
			if (StringUtils.isEmpty(cif.getPin())) {
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
						new String[] { bizMessageService
								.getMessageFor("l.cifPin") });
			}
			if (StringUtils.isEmpty(cif.getConfirmPin())) {
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,
						new String[] { bizMessageService
								.getMessageFor("l.cifConfirmPin") });
			}
			if (!cif.getPin().trim().equals(cif.getConfirmPin().trim())) {
				throw new MmbsWebException(
						MmbsWebException.NE_CIF_PIN_CONFIRM_NOT_SAME);
			}
			if (!StringUtils.isNumeric(cif.getPin().trim())
					|| !StringUtils.isNumeric(cif.getConfirmPin())) {
				throw new MmbsWebException(MmbsWebException.NE_CIF_PIN_NUMERIC);
			}
			int lengthPin = 0;
			try {
				lengthPin = settingService
						.getSettingAsInt(SettingService.SETTING_CHECK_LENGTH_PIN);
			} catch (Exception wse) {
				LOG.warn("Unable to load setting for CHECK LENGTH PIN: "
						+ wse.getMessage());
			}
			if (cif.getPin().trim().length() < lengthPin
					|| cif.getConfirmPin().trim().length() < lengthPin) {
				throw new MmbsWebException(
						MmbsWebException.NE_CIF_INVALID_LENGTH_PIN,
						new String[] { Integer.toString(lengthPin) });
			}
		} catch (MmbsWebException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Exception : " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public void checkDuplicateMobilePhone(int id, String mobilePhone)
			throws MmbsWebException {
		Cif cifTemp = cifMapper.findCifByMobilePhone(mobilePhone.trim());
		if (cifTemp != null) {
			if (id != 0) {
				if (cifTemp.getId() != id)
					throw new MmbsWebException(
							MmbsWebException.NE_DUPLICATE_DATA,
							new String[] { bizMessageService
									.getMessageFor("l.cifDeviceCode") });
			} else
				throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,
						new String[] { bizMessageService
								.getMessageFor("l.cifDeviceCode") });
		}
	}

	public void checkDuplicateAccountNo(int cifId, String accountNo)
			throws MmbsWebException {
		Account accountTemp = accountMapper.findByAccountNo(cifId, accountNo);
		if (accountTemp != null) {
			throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,
					new String[] { bizMessageService.getMessageFor("l.accountNumber") });
		}
	}

	public Cif findCifById(int id) throws MmbsWebException {
		LOG.debug("Processing -> Find Cif By Id: [{}]", id);
		try {
			Cif cif = cifMapper.findCifById(id);
			return cif;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public Cif findCifByDeviceCode(String deviceCode) throws MmbsWebException {
		LOG.debug("Processing -> Find Cif By Device Code: [{}]", deviceCode);
		try {
			Cif cif = cifMapper.findCifByMobilePhone(deviceCode);
			return cif;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public Cif findCifByAccountNo(String accountNo) throws MmbsWebException {
		LOG.debug("Processing -> Find Cif By Account Number: [{}]", accountNo);
		try {
			Cif cif = cifMapper.findCifByAccountNo(accountNo);
			return cif;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public List<CifVO> findListCifByParam(CifParamVO cifParamVO)
			throws MmbsWebException {
		LOG.debug("Processing -> Find List Cif By Params: [{}]",
				cifParamVO.toString());
		try {
			if (cifParamVO!=null) {
				if (cifParamVO.getCreatedOnStart() != null) {
					cifParamVO.setCreatedOnStart(DateUtils.generateDateStart(cifParamVO.getCreatedOnStart()));
				}
				if (cifParamVO.getCreatedOnEnd() != null) {
					cifParamVO.setCreatedOnEnd(DateUtils.generateDateEnd(cifParamVO.getCreatedOnEnd()));
				}
				if (!StringUtils.isEmpty(cifParamVO.getMobilePhone())) {
					cifParamVO.setMobilePhone(formatPhone(cifParamVO.getMobilePhone()));
				}
			}
			List<CifVO> listCif = cifMapper.findListCifByParam(cifParamVO);
			return listCif;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public int countByParam(CifParamVO cifParamVO) throws MmbsWebException {
		LOG.debug("Processing -> Count by Param: [{}]", cifParamVO.toString());
		try {
			if (cifParamVO!=null) {
				if (cifParamVO.getCreatedOnStart() != null) {
					cifParamVO.setCreatedOnStart(DateUtils.generateDateStart(cifParamVO.getCreatedOnStart()));
				}
				if (cifParamVO.getCreatedOnEnd() != null) {
					cifParamVO.setCreatedOnEnd(DateUtils.generateDateEnd(cifParamVO.getCreatedOnEnd()));
				}
				if (!StringUtils.isEmpty(cifParamVO.getMobilePhone())) {
					cifParamVO.setMobilePhone(formatPhone(cifParamVO.getMobilePhone()));
				}
			}
			int record = cifMapper.countByParam(cifParamVO);
			return record;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			return 0;
		}
	}

	public List<Cif> findListCifReplacementByParam(CifParamVO paramVO)
			throws MmbsWebException {
		LOG.debug("Processing -> findListCifReplacementByParam: [{}]", paramVO);
		try {
			List<Cif> listCifReplacement = cifMapper.findListCifReplacementByParam(paramVO);
			if (listCifReplacement.size() == 0) {
				LOG.debug("List Account/Cif Replacement empty for: [{}]",  paramVO);
			}
			return listCifReplacement;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			return new ArrayList<Cif>();
		}
	}

	/*
	 * auth cif
	 */
	public int countAuthCifByParam(CifAuthParamVO cifAuthParamVO)
			throws MmbsWebException {
		LOG.debug("Processing -> Count Auth Cif by Param: [{}]",
				cifAuthParamVO.toString());
		try {
			int record = cifMapper.countAuthCifByParam(cifAuthParamVO);
			return record;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			return 0;
		}
	}

	public List<CifVO> findListAuthCifByParam(CifAuthParamVO cifAuthParamVO)
			throws MmbsWebException {
		LOG.debug("Processing -> Find List Auth Cif By Params: [{}]",
				cifAuthParamVO.toString());
		try {
			List<CifVO> listCif = cifMapper
					.findListAuthCifByParam(cifAuthParamVO);
			return listCif;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public void fillRetailerIdToCif(Cif cif) throws MmbsWebException {
		LOG.debug("Processing -> fillRetailerIdToCif: [{}]", cif);
		try {
			cifMapper.fillRetailerIdToCif(cif);
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public List<CifVO> findCifForResetPin(CifParamVO paramVO)
			throws MmbsWebException {
		LOG.debug("Processing -> findCifForResetPin: [{}]", paramVO);
		try {
			List<CifVO> listCif = cifMapper.findCifForResetPin(paramVO);
			return listCif;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public int countCifForResetPin(CifParamVO paramVO) throws MmbsWebException {
		LOG.debug("Processing -> countCifForResetPin: [{}]", paramVO);
		try {
			int countCif = cifMapper.countCifForResetPin(paramVO);
			return countCif;
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}

	public void authorize(Cif cif, UserDataLoginVO loginVO)
			throws MmbsWebException {
		try {
			/* validation */
			// validationForAuth(cif);

			Date current = new Date();
			cif.setUpdatedBy(loginVO.getId());
			cif.setUpdatedOn(current);

			cif.setAuthBy(loginVO.getId());
			cif.setAuthOn(current);
			cif.setAuthStatus(Constants.STATUS_ACTIVE_STRING);

			LOG.debug("Authorize CIF : " + cif.toString());
		} catch (Exception e) {
			LOG.warn("Error Exception: " + e, e);
			throw new MmbsWebException(e);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO authorizeCifData(CifHistoryVO cifHistoryVO, UserDataLoginVO loginVO, Locale language) throws MmbsWebException {
		WebResultVO wrv = new WebResultVO();
		int cifId = cifHistoryVO.getCifId();
		try {
			Date now = new Date();
			cifHistoryVO.setUpdatedBy(loginVO.getId());
			cifHistoryVO.setUpdatedOn(now);
			cifHistoryVO.setAuthBy(loginVO.getId());
			cifHistoryVO.setAuthOn(now);
			if (cifId == 0)
			{
				LOG.error("cifHistoryVO.getAuthStatus(): " + cifHistoryVO.getAuthStatus());
				if (cifHistoryVO.getAuthStatus().equals(WebConstants.STAT_APPROVED))
				{
					wrv = engineService.sendEngineCif(cifHistoryVO.getCifHistoryId(), cifHistoryVO.getDeviceCode(), loginVO, language, 
							Constants.TRX_CODE_CIF_AUTH, Constants.STATE_CIF_REG_AUTH);
					
				} 
				else 
				{
					cifHistoryMapper.changeCifHistoryAuthStatus(cifHistoryVO);
					wrv.setMessage(bizMessageService.getMessageFor("rm.generalMessage", 
							new String[] {bizMessageService.getMessageFor("l.newCifRegistration", language), 
							bizMessageService.getMessageFor("l.rejected", language)}, language));
					wrv.setRc(WebConstants.RESULT_SUCCESS);
					wrv.setType(WebConstants.TYPE_UPDATE);
				}
			} 
			else
			{
				if (cifHistoryVO.getAuthStatus().equals(WebConstants.STAT_APPROVED))
				{
					wrv = updateCifData(cifHistoryVO, loginVO, language);
				} 
				else 
				{					
					cifHistoryVO.setAuthBy(loginVO.getAuthBy());
					cifHistoryVO.setAuthOn(new Date());
					cifHistoryVO.setUpdatedBy(loginVO.getId());
					cifHistoryVO.setUpdatedOn(new Date());
					cifHistoryVO.setAuthStatus(WebConstants.STAT_REJECTED);
					cifHistoryMapper.changeCifHistoryAuthStatus(cifHistoryVO);
					cifHistoryMapper.changeCifAuthStatus(cifHistoryVO);
					wrv.setMessage(bizMessageService.getMessageFor("rm.generalMessage", 
							new String[] {bizMessageService.getMessageFor("l.cifChanges", language),
							bizMessageService.getMessageFor("l.rejected", language)}, language));
					wrv.setRc(WebConstants.RESULT_SUCCESS);
					wrv.setType(WebConstants.TYPE_UPDATE);
				}
			}
		} catch (Exception e) {
			LOG.error("error: " + e );
			throw new MmbsWebException(e);
		}
		return wrv;
	}
		
	private String formatPhone(String phoneNo) {
		if (phoneNo == null)
			return phoneNo;
		phoneNo = phoneNo.trim();
		// TODO: this always assume that country is Indonesian, it must be
		// refactored to support other country
		if (phoneNo.startsWith("0")) // for 021900xxx, 0812xxxx
			phoneNo = "+62" + phoneNo.substring(1);
		else if (phoneNo.startsWith("+0")) // for +021900xxx
			phoneNo = "+62" + phoneNo.substring(2);
		return phoneNo;
	}
	
	private void deleteAccount(int cifId)
	{
		accountMapper.deleteAccount(cifId);
	}
}
