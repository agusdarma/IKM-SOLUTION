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

import com.jakarta.software.web.data.BillPaymentVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.data.param.BillPaymentParamVO;
import com.jakarta.software.web.entity.BillPayment;
import com.jakarta.software.web.mapper.BillPaymentMapper;

@Service
public class BillPaymentService {	
	protected static final Logger LOGGER = LoggerFactory.getLogger(BillPaymentService.class);
	
	@Autowired
	private BillPaymentMapper billPaymentMapper;
	
	@Autowired
	private BizMessageService messageService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Transactional(rollbackFor=Exception.class)
	public WebResultVO insertOrUpdateBillPayment(BillPaymentVO billPayment, UserDataLoginVO loginVO, Locale language) throws MmbsWebException{
		WebResultVO wrv = new WebResultVO();
		Date current = new Date();
		
		billPayment.setUpdatedBy(loginVO.getId());
		billPayment.setUpdatedOn(current);
		
		try {
			// validate input
			billPayment = validateInput(billPayment, language);
			//insert
			if(billPayment.getId() == 0)
			{
				try {
					checkDuplicateBillerCode(billPayment.getId(), billPayment.getBillerNo(), language);
				} catch (MmbsWebException MWE) {
					throw MWE;
				}				
				billPayment.setCreatedBy(loginVO.getId());
				billPayment.setCreatedOn(current);
				
				LOGGER.info("Processing -> Insert Biller: {}, user: {}", billPayment, loginVO.getUserName());			
				
				billPaymentMapper.insertBillPayment(billPayment);				
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.billPayment"), messageService.getMessageFor("l.created")}, language));
				wrv.setType(WebConstants.TYPE_INSERT);	
				
				/** HISTORY ACTIVITY **/
				try {
					BillPayment billPaymentOri=new BillPayment();
					Collection<String> excludes=new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, billPaymentOri, billPayment, loginVO.getId(), "Bill Payment", 
							WebConstants.ACT_TYPE_INSERT , "bill_payment", loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
					throw e;
				}
				/** HISTORY ACTIVITY **/
			}
			else{ //update
				BillPayment billPaymentOri = findBillPaymentById(billPayment.getId());
				
				checkDuplicateBillerCode(billPayment.getId(), billPayment.getBillerNo(), language);
				
				LOGGER.info("Processing -> Update Biller: {}, user: {}", billPayment, loginVO.getUserName());
				
				billPaymentMapper.updateBillPayment(billPayment);
				wrv.setRc(WebConstants.RESULT_SUCCESS);
				wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.billPayment"), messageService.getMessageFor("l.updated")}, language));
				wrv.setType(WebConstants.TYPE_UPDATE);
				wrv.setPath(WebConstants.PATH_UPDATE_BILL_PAYMENT);		
				
				/** HISTORY ACTIVITY **/
				try {
					Collection<String> excludes=new ArrayList<String>();
					excludes.add("createdOn");
					excludes.add("createdBy");
					excludes.add("updatedOn");
					excludes.add("updatedBy");
					
					userActivityService.generateHistoryActivity(excludes, billPaymentOri, billPayment, loginVO.getId(), "Bill Payment", 
							WebConstants.ACT_TYPE_UPDATE , "bill_payment", loginVO.getId());
				} catch (Exception e) {
					LOGGER.warn("Unable to Create History Activity: " + e.getMessage());
					throw e;
				}
				/** HISTORY ACTIVITY **/
			}
			return wrv;
		}catch (MmbsWebException e){
			throw e;
		}catch (Exception e) {
			LOGGER.error("exception: " +e, e);
			throw new MmbsWebException(e);
		}		
	}
	
	public BillPaymentVO validateInput(BillPaymentVO billPayment, Locale language) throws MmbsWebException{
		if (StringUtils.isEmpty(billPayment.getBillerNo())) {
			LOGGER.warn("Missing Input Biller Code..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.billerCode", language)});
		}
		if (StringUtils.isEmpty(billPayment.getBillerName())) {
			LOGGER.warn("Missing Input Biller Name..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.billerName", language)});
		}
		
		if(StringUtils.isEmpty(billPayment.getBillerDesc())){
			LOGGER.warn("Missing Input Biller Name..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.billerDesc", language)});
		}
		
		// bill ref1 (harus ada bill ref1)
		if(StringUtils.isEmpty(billPayment.getBillRef1())){			
			LOGGER.warn("Missing Input Bill Ref 1..");
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.billRef1", language)});
		}
		else{
			if(StringUtils.isEmpty(billPayment.getBillType1())){
				LOGGER.warn("Missing Input Bill Type 1..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.type", language)});
			}
			if(StringUtils.isEmpty(billPayment.getBillMinLength1())){
				LOGGER.warn("Missing Minimum Bill Length 1..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.minLength1", language)});
			}
			else{
				if(billPayment.getBillMinLength1().length() == 1){
					billPayment.setBillMinLength1("0" + billPayment.getBillMinLength1());
				}
			}
			if(StringUtils.isEmpty(billPayment.getBillMaxLength1())){
				LOGGER.warn("Missing Maximum Bill Length 1..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.maxLength1", language)});
			}
			else{
				if(billPayment.getBillMaxLength1().length() == 1){
					billPayment.setBillMaxLength1("0" + billPayment.getBillMaxLength1());
				}
			}
		}
		
		// bill ref2
		if(!StringUtils.isEmpty(billPayment.getBillRef2())){
			if(StringUtils.isEmpty(billPayment.getBillType2())){
				LOGGER.warn("Missing Input Bill Type 2..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.type", language)});
			}
			if(StringUtils.isEmpty(billPayment.getBillMinLength2())){
				LOGGER.warn("Missing Minimum Bill Length 2..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.minLength2", language)});
			}
			else{
				if(billPayment.getBillMinLength2().length() == 1){
					billPayment.setBillMinLength2("0" + billPayment.getBillMinLength2());
				}
			}
			if(StringUtils.isEmpty(billPayment.getBillMaxLength2())){
				LOGGER.warn("Missing Maximum Bill Length 2..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.maxLength2", language)});
			}
			else{
				if(billPayment.getBillMaxLength2().length() == 1){
					billPayment.setBillMaxLength2("0" + billPayment.getBillMaxLength2());
				}
			}
		}
		else{
			billPayment.setBillType2("");
		}
		
		// bill ref3
		if(!StringUtils.isEmpty(billPayment.getBillRef3())){
			if(StringUtils.isEmpty(billPayment.getBillType3())){
				LOGGER.warn("Missing Input Bill Type 3..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.type", language)});
			}
			if(StringUtils.isEmpty(billPayment.getBillMinLength3())){
				LOGGER.warn("Missing Minimum Bill Length 3..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.minLength3", language)});
			}
			else{
				if(billPayment.getBillMinLength3().length() == 1){
					billPayment.setBillMinLength3("0" + billPayment.getBillMinLength3());
				}
			}
			if(StringUtils.isEmpty(billPayment.getBillMaxLength3())){
				LOGGER.warn("Missing Maximum Bill Length 3..");
				throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.maxLength3", language)});
			}
			else{
				if(billPayment.getBillMaxLength3().length() == 1){
					billPayment.setBillMaxLength3("0" + billPayment.getBillMaxLength3());
				}
			}
		}
		else{
			billPayment.setBillType3("");
		}
		return billPayment;
	}
	
	public void checkDuplicateBillerCode(int id, String billerCode, Locale language) throws MmbsWebException {
		LOGGER.debug("Processing - > checkDuplicateBillerCode: [{}]", billerCode);
		BillPayment billPayment = billPaymentMapper.findBillPaymentByBillerNo(billerCode);
		if (billPayment != null)
		{
			if (id != 0) 
			{
				if (billPayment.getId() != id)
				{
					throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.billerCode", language)});
				}
			}
			else
			{
				throw new MmbsWebException(MmbsWebException.NE_DUPLICATE_DATA,new String[] {messageService.getMessageFor("l.billerCode", language)});
			}
		}
	}
	
	public int countBillPaymentByParam(BillPaymentParamVO paramVO) throws MmbsWebException{
		try {
			int count = billPaymentMapper.countBillPaymentByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<BillPaymentVO> findBillPaymentByParam(BillPaymentParamVO paramVO) throws MmbsWebException{
		try {
			List<BillPaymentVO> listBillPayment = billPaymentMapper.findBillPaymentByParam(paramVO);
			return listBillPayment;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<BillPaymentVO>();
		}
	}
	
	public BillPaymentVO findBillPaymentById (int id) throws MmbsWebException{
		try {
			BillPaymentVO billPayment = billPaymentMapper.findBillPaymentById(id);
			return billPayment;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return null;
		}
	}	
	
	public List<BillPayment> selectAllBillPayment() {		
		List<BillPayment> listBillPayment = billPaymentMapper.selectAllBillPayment();
		return listBillPayment;	
	}	
}
