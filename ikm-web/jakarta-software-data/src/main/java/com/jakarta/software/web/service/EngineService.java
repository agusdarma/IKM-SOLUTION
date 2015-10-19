package com.jakarta.software.web.service;

import java.util.ArrayList;
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

import com.jakarta.software.web.data.AccountResponseVO;
import com.jakarta.software.web.data.CifEngineRequestVO;
import com.jakarta.software.web.data.EngineResponseVO;
import com.jakarta.software.web.data.UserDataLoginVO;
import com.jakarta.software.web.data.WebAccountRequestVO;
import com.jakarta.software.web.data.WebConstants;
import com.jakarta.software.web.data.WebResultVO;
import com.jakarta.software.web.utils.Constants;
import com.jakarta.software.web.utils.SecureUtils;

@Service
public class EngineService {
	private static final Logger LOG = LoggerFactory.getLogger(EngineService.class);

	@Autowired
	private HttpClientService httpClientService;

	@Autowired
	private BizMessageService messageService;

	private ObjectMapper mapper;

	private String encryptionKey = "";
	
	public EngineService() {
		mapper = new ObjectMapper();
	}
	
	public AccountResponseVO sendToEngineForInqAcc(String cardNo, String accountNo) {
		LOG.debug("Send to engine for Inquiry Account: [{}], Card No: {}", accountNo, cardNo);

		WebAccountRequestVO accReqVO = new WebAccountRequestVO();
		accReqVO.setTrxCode(Constants.TRX_CODE_ACC_INFO);
		accReqVO.setState(Constants.STATE_CIF_REG_CHECK_ACC);
		accReqVO.setCardNo(cardNo);
		accReqVO.setAccountNo(accountNo);

		AccountResponseVO arv = null;
		try {
			String msg = mapper.writeValueAsString(accReqVO);

			String encMessage = encrypt(msg);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(
					Constants.HTTP_PARAM_MESSAGE_ENCRYPTED, encMessage));

			// Read the response body.
			String responseMessage = httpClientService.connectToEngine(params);
			LOG.info("respons message : " + responseMessage);

			Properties respParams = new Properties();
			parseQueryString(responseMessage, respParams);
			String x = respParams.getProperty(Constants.HTTP_PARAM_MESSAGE_ENCRYPTED);
			responseMessage = decrypt(x);
			
			ObjectMapper mapper = new ObjectMapper();
			arv = mapper.readValue(responseMessage,AccountResponseVO.class);
			return arv;
		} catch (MmbsWebException me) {
			arv = new AccountResponseVO();
			arv.setResultCode("" + me.getErrorCode());
			arv.setErrorMessage(me.getMessage());
			return arv;
		} catch (Exception e) {
			LOG.warn("Unexpected error when sendToEngineForInqAcc", e);
			arv = new AccountResponseVO();
			arv.setResultCode("" + MmbsWebException.NE_UNKNOWN_ERROR);
			arv.setErrorMessage(e.getMessage());
			return arv;
		}
	}

	public WebResultVO sendEngineCif(int cifHistoryId, String deviceCode, UserDataLoginVO loginVO, Locale language, String trxCode, int state) throws MmbsWebException {
		LOG.info("Send to engine with cifHistory id" + cifHistoryId);
		WebResultVO wrv = new WebResultVO();

		CifEngineRequestVO authVO = new CifEngineRequestVO();
		authVO.setTrxCode(trxCode);
		authVO.setState(state);
		authVO.setCifHistoryId(cifHistoryId);
		authVO.setUserId(loginVO.getId());
		authVO.setDeviceCode(deviceCode);
		try {
			String encMessage = encrypt(mapper.writeValueAsString(authVO));

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(Constants.HTTP_PARAM_MESSAGE_ENCRYPTED, encMessage));

			// Read the response body.
			String responseMessage = httpClientService.connectToEngine(params);
			LOG.info("respons message : " + responseMessage);
			
			Properties respParams = new Properties();
			parseQueryString(responseMessage, respParams);
			String x = respParams.getProperty(Constants.HTTP_PARAM_MESSAGE_ENCRYPTED);
			responseMessage = decrypt(x);

			ObjectMapper mapper = new ObjectMapper();
			EngineResponseVO receiveRespons = mapper.readValue(responseMessage,EngineResponseVO.class);

			if (receiveRespons != null) {
				String errorMsg = receiveRespons.getErrorMessage();
				String rc = receiveRespons.getResultCode();

				if (!rc.equals(Constants.RC_SUCCESS))
				{
					throw new MmbsWebException(
							MmbsWebException.NE_ENGINE_ERROR_MSG, errorMsg);
				}
			}
			
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setMessage(messageService.getMessageFor("rm.generalMessage", 
					new String[] {messageService.getMessageFor("l.newCifRegistration", language), 
					messageService.getMessageFor("l.created", language)}, language));
			wrv.setType(WebConstants.TYPE_UPDATE);
			
			wrv.setPath(WebConstants.PATH_UPDATE_AUTH_CIF);
		} catch (MmbsWebException me) {
			throw me;
		} catch (Exception e) {
			LOG.warn("Unexpected error when sendToEngineForCif", e);
			throw new MmbsWebException(MmbsWebException.NE_UNKNOWN_ERROR,
					e.getMessage());
		}
		return wrv;
	}

	protected String encrypt(String message) {
		if (StringUtils.isEmpty(encryptionKey))
		{
			return message;
		}			
		return SecureUtils.encryptDESede(message, encryptionKey);
	}

	protected String decrypt(String message){
		if (StringUtils.isEmpty(encryptionKey))
		{
			return message;
		}			
		return SecureUtils.decryptDESede(message, encryptionKey);
	}

	public static void parseQueryString(String query, Properties params) {
		StringTokenizer st = new StringTokenizer(query, "?&=", true);
		String previous = null;
		while (st.hasMoreTokens()) {
			String current = st.nextToken();
			if ("?".equals(current) || "&".equals(current)) {
				// ignore
			} else if ("=".equals(current)) {
				String value = "";
				if (st.hasMoreTokens())
					value = st.nextToken();
				if ("&".equals(value))
					value = ""; // ignore &
				params.setProperty(previous, value);
			} else {
				previous = current;
			}
		}
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

}
