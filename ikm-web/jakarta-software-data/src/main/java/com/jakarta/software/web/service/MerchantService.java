package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jakarta.software.web.data.MerchantVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.UserDataVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.MerchantParamVO;
import com.jakarta.software.web.entity.Merchant;
import com.jakarta.software.web.entity.UserData;
import com.jakarta.software.web.entity.UserPreference;
import com.jakarta.software.web.mapper.MerchantMapper;
import com.jakarta.software.web.mapper.UserPreferenceMapper;

@Service
public class MerchantService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(MerchantService.class);
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private UserPreferenceMapper userPreferenceMapper;
	
	public WebResultVO insertOrUpdateMerchant(Merchant merchant, UserDataLoginVO loginVO, Locale language) throws MmbsWebException{
		WebResultVO wrv = new WebResultVO();
		Date current = new Date();
		
		merchant.setUpdatedBy(loginVO.getId());
		merchant.setUpdatedOn(current);
		
		try {
			//validate input
			validateInput(merchant, language);
			
			if(merchant.getId() == 0){
				checkDuplicateMerchantByMerchantCode(merchant.getId(), merchant.getMerchantCode(), language);
				
				checkDuplicateMerchantHistoryByMerchantCode(merchant.getMerchantCode(), language);				
				merchant.setCreatedBy(loginVO.getId());
				merchant.setCreatedOn(current);
				merchant.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
				merchant.setActivityType(WebConstants.ACT_TYPE_INSERT);
				
				LOGGER.debug("Processing -> Insert Merchant History: {}", merchant);			
				
				merchantMapper.insertMerchantHistory(merchant);				
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.merchant", language), messageService.getMessageFor("l.created", language)}, language));
				wrv.setType(WebConstants.TYPE_INSERT);	
				
				/** HISTORY ACTIVITY **/
				try {
					Merchant merchantOri=new Merchant();
					Collection<String> excludes=new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, merchantOri, merchant, loginVO.getId(), "Merchant", 
							WebConstants.ACT_TYPE_INSERT , "merchant_history", loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
					throw e;
				}
				/** HISTORY ACTIVITY **/
				
			}
			else{//update
				merchant.setActivityType(WebConstants.ACT_TYPE_UPDATE);
				merchant.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
				
				MerchantVO merchantVO = new MerchantVO();
				merchantVO.setUpdatedBy(loginVO.getId());
				merchantVO.setUpdatedOn(current);
				merchantVO.setAuthStatus(WebConstants.STAT_NOT_AUTHORIZED);
				merchantVO.setAuthOn(merchant.getAuthOn());
				merchantVO.setAuthBy(merchant.getAuthBy());
				merchantVO.setId(merchant.getId());
				LOGGER.debug("Processing -> Update Merchant : {}", merchantVO);
				merchantMapper.changeMerchantAuthStatus(merchantVO);
				
				LOGGER.debug("Processing -> Insert Merchant History: {}", merchant);				
				merchantMapper.insertMerchantHistory(merchant);
				
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.merchant", language), messageService.getMessageFor("l.updated", language)}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);
				wrv.setPath(WebConstants.PATH_UPDATE_MERCHANT);		
			}
		}catch (MmbsWebException e){
			throw e;
		}catch (Exception e) {
			LOGGER.error("exception: " +e, e);
			throw new MmbsWebException(e);
		}		
		return wrv;
	}
	
	public void validateInput(Merchant merchant, Locale language) throws MmbsWebException{
		if (StringUtils.isEmpty(merchant.getMerchantCode())) {
			LOGGER.warn("Missing Input Merchant Code..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.merchantCode", language)});
		}
		if (StringUtils.isEmpty(merchant.getMerchantName())) {
			LOGGER.warn("Missing Input Merchant Name..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.merchantName", language)});
		}
		if(StringUtils.isEmpty(merchant.getAlias())){
			LOGGER.warn("Missing Input Merchant Alias..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.alias", language)});
		}
		if(StringUtils.isEmpty(merchant.getAccountType())){
			LOGGER.warn("Missing Input Account Type..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.accountType", language)});
		}
		if(StringUtils.isEmpty(merchant.getAccountNumber())){
			LOGGER.warn("Missing Input Account Number..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.accountNumber", language)});
		}
		if(StringUtils.isEmpty(merchant.getCurrency())){
			LOGGER.warn("Missing Input Currency..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.currency", language)});
		}
		if(StringUtils.isEmpty(merchant.getStatus())){
			LOGGER.warn("Missing Input Merchant Status..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.status", language)});
		}
		if(merchant.getTimeout() == 0){
			LOGGER.warn("Missing Input Merchant Timeout..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.timeout", language)});
		}

	}
	
	public void checkDuplicateMerchantByMerchantCode(int id, String merchantCode, Locale language) throws MmbsWebException {
		LOGGER.debug("Processing -> checkDuplicateMerchantByMerchantCode: [{}]", merchantCode);
		Merchant merchant = merchantMapper.findMerchantHistoryByMerchantCode(merchantCode);
		if (merchant != null) {
			if (id != 0) {
				if (merchant.getId() != id)
					throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.merchantCode", language)});
			} else
				throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.merchantCode", language)});
		}
	}
	
	public void checkDuplicateMerchantHistoryByMerchantCode(String merchantCode, Locale language) throws MmbsWebException {
		LOGGER.debug("Processing -> checkDuplicateMerchantHistoryByMerchantCode: [{}], authStatus= Y, and merchant_id= 0", merchantCode);
		Merchant merchant = merchantMapper.findMerchantHistoryByMerchantCode(merchantCode);
		if (merchant != null) {
			LOGGER.info("Found Duplicate Merchant History with Code: [{}], id: [{}]", merchant.getMerchantCode(), merchant.getId());
			throw new MmbsWebException(MmbsWebException.NE_MERCHANT_REGISTRATION_NOT_YET_AUTHORIZED,new String[] {messageService.getMessageFor("l.userData", language)});
		}
	}
	
	public int countMerchantByParam(MerchantParamVO paramVO) throws MmbsWebException{
		LOGGER.debug("Processing -> countMerchantByParam: [{}]", paramVO);
		try {
			int count = merchantMapper.countMerchantByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<MerchantVO> findMerchantByParam(MerchantParamVO paramVO) throws MmbsWebException{
		LOGGER.debug("Processing -> findMerchantByParam: [{}]", paramVO);
		try {
			List<MerchantVO> listMerchant = merchantMapper.findMerchantByParam(paramVO);
			return listMerchant;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<MerchantVO>();
		}
	}
	
	public Merchant findMerchantById(int id) throws MmbsWebException{
		LOGGER.debug("Processing -> findMerchantById: [{}]", id);
		try {
			Merchant merchant = merchantMapper.findMerchantById(id);
			return merchant;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return null;
		}		
	}
	
	
	public int countMerchantHistoryByParam(MerchantParamVO paramVO) {
		LOGGER.debug("Processing -> countMerchantHistoryByParam: [{}]", paramVO);
		try {
			int count = merchantMapper.countMerchantHistoryByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<MerchantVO> findMerchantHistoryByParam(MerchantParamVO paramVO) {
		LOGGER.debug("Processing -> findMerchantHistoryByParam: [{}]", paramVO);
		try {
			List<MerchantVO> listMerchant = merchantMapper.findMerchantHistoryByParam(paramVO);
			return listMerchant;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<MerchantVO>();
		}
	}
	
	public MerchantVO findMerchantHistoryByIdHistory(int id) { //throws MmbsWebException{
		LOGGER.debug("Processing -> findMerchantHistoryById: [{}]", id);
//		try {
			MerchantVO merchantVO = merchantMapper.findMerchantHistoryByIdHistory(id);
			return merchantVO;
//		} catch (Exception e) {
//			LOGGER.error("ERROR: " + e, e);
//			return null;
//		}		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO authorizeMerchant(MerchantVO merchantVO, UserDataLoginVO loginVO, String mode, Locale language) throws MmbsWebException
	{
		WebResultVO wrv = new WebResultVO();
		try {
			Date now = new Date();
			merchantVO.setUpdatedBy(loginVO.getId());
			merchantVO.setUpdatedOn(now);
			merchantVO.setAuthBy(loginVO.getId());
			merchantVO.setAuthOn(now);
			
			//authorize
			Merchant merchant = new Merchant();
			BeanUtils.copyProperties(merchantVO, merchant);
			merchant.setId(merchantVO.getMerchantId());
			if(merchantVO.getMerchantId()==0)
			{			
				if(merchantVO.getAuthStatus().equals(WebConstants.STAT_APPROVED))//tidak reject
				{
					//insert to merchant
					merchantMapper.insertMerchant(merchant);
					wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
							new String[] {messageService.getMessageFor("l.newMerchantRegistration", language),
								messageService.getMessageFor("l.approved", language)}, language));
					merchantVO.setMerchantId(merchant.getId());
					
					/** SET TO USER ACTIVITY **/
					try {
						Merchant oriMerchant=new Merchant();
						Collection<String> excludes = new ArrayList<String>();
						excludes.add("createdOn");
						excludes.add("createdBy");
						excludes.add("updatedOn");
						excludes.add("updatedBy");
						
						userActivityService.generateHistoryActivity(excludes, oriMerchant, merchant, loginVO.getId(), 
								WebConstants.ACT_MODULE_MERCHANT, WebConstants.ACT_TYPE_INSERT,
								WebConstants.ACT_TABLE_MERCHANT, loginVO.getId());
					} catch (Exception e) {
						LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
					}		
					/** SET TO USER ACTIVITY **/
				}
				else
				{
					//reject
					wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
							new String[] {messageService.getMessageFor("l.newMerchantRegistration", language),
								messageService.getMessageFor("l.rejected", language)}, language));
				}
			}
			else
			{
				Merchant merchantOri = findMerchantById(merchant.getId());
				
				//update to merchant
				if(merchantVO.getAuthStatus().equals(WebConstants.STAT_APPROVED)) //tidak reject
				{
					merchantMapper.updateMerchant(merchant);
					wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
							new String[] {messageService.getMessageFor("l.merchantChanges", language),
								messageService.getMessageFor("l.approved", language)}, language));				}
				else //reject
				{
					wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
							new String[] {messageService.getMessageFor("l.merchantChanges", language),
								messageService.getMessageFor("l.rejected", language)}, language));
					merchantMapper.changeMerchantAuthStatus(merchantVO);
				}		
				
				/** SET TO USER ACTIVITY **/
				try {
					Collection<String> excludes = new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, merchantOri, merchant, loginVO.getId(), 
							WebConstants.ACT_MODULE_MERCHANT, WebConstants.ACT_TYPE_UPDATE,
							WebConstants.ACT_TABLE_MERCHANT, loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
				}		
				/** SET TO USER ACTIVITY **/

			}
			merchantMapper.changeMerchantHistoryAuthStatus(merchantVO);
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setPath(WebConstants.PATH_UPDATE_AUTH_MERCHANT);
			wrv.setType(WebConstants.TYPE_UPDATE);
			return wrv;
		} catch (Exception e) {
			LOGGER.error("exception: " +e, e);
			throw new MmbsWebException(e);
		}
		
	}

}
