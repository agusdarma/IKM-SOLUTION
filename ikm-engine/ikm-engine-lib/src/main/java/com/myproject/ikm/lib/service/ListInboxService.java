package com.myproject.ikm.lib.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ikm.lib.data.InboxVO;
import com.myproject.ikm.lib.data.ReqListInboxData;
import com.myproject.ikm.lib.data.RespListInboxVO;
import com.myproject.ikm.lib.entity.User;
import com.myproject.ikm.lib.mapper.InboxMapper;
import com.myproject.ikm.lib.mapper.UserDataMapper;
import com.myproject.ikm.lib.utils.CipherUtil;
import com.myproject.ikm.lib.utils.Constants;

@Service
public class ListInboxService {
	private static final Logger LOG = LoggerFactory.getLogger(ListInboxService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private InboxMapper inboxMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	
	
	public RespListInboxVO findInboxByUser(ReqListInboxData reqListInboxData) throws IkmEngineException {
		LOG.debug("findInboxByUser with param : " + " reqListInboxData: " + reqListInboxData);	
		User user = userDataMapper.findUserByKodeSekolahAndNoIndukAndUserType(reqListInboxData.getKodeSekolah(), reqListInboxData.getNoInduk(),reqListInboxData.getUserType());
		if(user == null){
			LOG.error("Can't find User with parameter: " + reqListInboxData);
			throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
		}
		if(Constants.BLOCKED == user.getStatusUser()){
			LOG.error("User already blocked");
			throw new IkmEngineException(IkmEngineException.ENGINE_USER_BLOCKED);
		}
		if(Constants.PENDING == user.getStatusUser()){
			LOG.error("User not active");
			throw new IkmEngineException(IkmEngineException.ENGINE_USER_NOT_ACTIVE);
		}	
		if(Constants.TEACHER == reqListInboxData.getUserType()){
			String passwordDB = user.getPassword();
			String passwordInput = CipherUtil.passwordDigest(reqListInboxData.getKodeSekolah(), reqListInboxData.getPassword());
			if(!passwordDB.equals(passwordInput)){
				throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
			}
		}
		RespListInboxVO respListInboxVO = new RespListInboxVO();
		/**
		 * Base Data Login
		 */
		respListInboxVO.setKodeSekolah(reqListInboxData.getKodeSekolah());
		respListInboxVO.setNoInduk(reqListInboxData.getNoInduk());
		respListInboxVO.setOriginRequest(reqListInboxData.getOriginRequest());
		respListInboxVO.setUserType(reqListInboxData.getUserType());
		
		/**
		 * Get data inbox
		 */
		List<InboxVO> listInboxVOs = inboxMapper.findListInboxByUser(user.getId());
		if(listInboxVOs.size()>0){
			for (InboxVO inboxVO : listInboxVOs) {
				if(inboxVO.getFromName().equalsIgnoreCase(user.getNama())){
					inboxVO.setSelf(true);
				}
			}
			respListInboxVO.setListInboxVO(listInboxVOs);
		}
		
		LOG.info("findInboxByUser done with param : " + " respListInboxVO: " + respListInboxVO);
		return respListInboxVO;		
	}
	
}
