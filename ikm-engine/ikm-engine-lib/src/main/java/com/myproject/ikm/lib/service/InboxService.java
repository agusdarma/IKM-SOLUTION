package com.myproject.ikm.lib.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ikm.lib.data.InboxVO;
import com.myproject.ikm.lib.data.ReqListInboxData;
import com.myproject.ikm.lib.data.ReqSendMessageData;
import com.myproject.ikm.lib.data.RespListInboxVO;
import com.myproject.ikm.lib.entity.Kelas;
import com.myproject.ikm.lib.entity.Message;
import com.myproject.ikm.lib.entity.User;
import com.myproject.ikm.lib.mapper.InboxMapper;
import com.myproject.ikm.lib.mapper.UserDataMapper;
import com.myproject.ikm.lib.utils.CipherUtil;
import com.myproject.ikm.lib.utils.CommonUtil;
import com.myproject.ikm.lib.utils.Constants;

@Service
public class InboxService {
	private static final Logger LOG = LoggerFactory.getLogger(InboxService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private InboxMapper inboxMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	public void sendMessage(ReqSendMessageData reqSendMessageData) throws IkmEngineException {
		LOG.debug("sendMessage with param : " + " reqSendMessageData: " + reqSendMessageData);	
		User user = userDataMapper.findUserByKodeSekolahAndNoIndukAndUserType(reqSendMessageData.getKodeSekolah(), reqSendMessageData.getNoInduk(),reqSendMessageData.getUserType());
		if(user == null){
			LOG.error("Can't find User with parameter: " + reqSendMessageData);
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
		if(Constants.TEACHER == reqSendMessageData.getUserType()){
			String passwordDB = user.getPassword();
			String passwordInput = CipherUtil.passwordDigest(reqSendMessageData.getKodeSekolah()+reqSendMessageData.getNoInduk(), reqSendMessageData.getPassword());
			if(!passwordDB.equals(passwordInput)){
				throw new IkmEngineException(IkmEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
			}
		}
		
		
		if(Constants.PARENT == reqSendMessageData.getUserType()){
			List<Kelas> listKelasUser = userDataMapper.findKelasByUser(reqSendMessageData.getKodeSekolah(), user.getId());
			if(listKelasUser.size() == 0){
				throw new IkmEngineException(IkmEngineException.ENGINE_USER_NOT_HAVE_KELAS);
			}
			Date now = timeService.getCurrentTime();
			Message message = new Message();
			message.setCreatedOn(now);
			message.setUpdatedOn(now);
			message.setIsiMessage(reqSendMessageData.getIsiMessage());
			message.setFromUserId(user.getId());
			message.setToUserId(listKelasUser.get(0).getWaliKelasId());
			message.setIsRead(Constants.UNREAD);
			LOG.info("parent send message : " + message);
			try {
				inboxMapper.insertMessage(message);
				/**
				 * update status unread menjadi read
				 */
				inboxMapper.updateMessageToRead(listKelasUser.get(0).getWaliKelasId());
			} catch (Exception e) {
				throw new IkmEngineException(IkmEngineException.ENGINE_SEND_MESSAGE_FAILED);
			}
		}else if(Constants.TEACHER == reqSendMessageData.getUserType()){
//			List<User> listRecepient = userDataMapper.findAllRecepientMessageFromTeacher(reqSendMessageData.getKodeSekolah(), user.getId());
//			if(listRecepient.size() == 0){
//				throw new IkmEngineException(IkmEngineException.ENGINE_RECEPIENT_EMPTY);
//			}
//			for (User recepient : listRecepient) {
				Date now = timeService.getCurrentTime();
				Message message = new Message();
				message.setCreatedOn(now);
				message.setUpdatedOn(now);
				message.setIsiMessage(reqSendMessageData.getIsiMessage());
				message.setFromUserId(user.getId());
				message.setToUserId(reqSendMessageData.getRecepientId());
				message.setIsRead(Constants.UNREAD);
				LOG.info("teacher send message : " + message);
				try {
					inboxMapper.insertMessage(message);	
					/**
					 * update status unread menjadi read
					 */
					inboxMapper.updateMessageToRead(reqSendMessageData.getRecepientId());
				} catch (Exception e) {
					throw new IkmEngineException(IkmEngineException.ENGINE_SEND_MESSAGE_FAILED);
				}
//			}
		}
		
		LOG.info("sendMessage done with param : " + " reqSendMessageData: " + reqSendMessageData);	
	}
	
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
			String passwordInput = CipherUtil.passwordDigest(reqListInboxData.getKodeSekolah()+reqListInboxData.getNoInduk(), reqListInboxData.getPassword());
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
				inboxVO.setCreatedOnVal(CommonUtil.displayTime(inboxVO.getCreatedOn()));
			}
			respListInboxVO.setListInboxVO(listInboxVOs);
		}
//		/**
//		 * update status unread menjadi read
//		 */
//		inboxMapper.updateMessageToRead(user.getId());
		
		
		
		LOG.info("findInboxByUser done with param : " + " respListInboxVO: " + respListInboxVO);
		return respListInboxVO;		
	}
	
}
