package com.jakarta.software.web.service;

import java.text.SimpleDateFormat;
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
import com.jakarta.software.web.data.param.SystemSettingParamVO;
import com.jakarta.software.web.entity.SystemSetting;
import com.jakarta.software.web.mapper.SystemSettingMapper;

@Service
public class SettingService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SettingService.class);

	public static final String TYPE_STRING 		= "STRING";
	public static final String TYPE_INT 		= "INT";
	public static final String TYPE_DATE 		= "DATE";
	public static final String TYPE_DATETIME 	= "DATETIME";
	public static final String TYPE_DOUBLE 		= "DOUBLE";
	public static final String TYPE_BOOLEAN 	= "BOOLEAN";
	
	// int, max wrong pin allowed
	public static final int SETTING_PASSWORD_MIN_LENGTH = 1;
	
	// int, max wrong pin allowed
	public static final int SETTING_MAX_INVALID_LOGIN = 2;
	
	// int, max wrong pin allowed
	public static final int SETTING_MAX_WRONG_PIN_ALLOWED = 3;

	// int, max data last update showed
	public static final int SETTING_MAX_LAST_UPDATED = 4;
	
	// int, max data last update showed
	public static final int SETTING_MAX_PASSWORD_EXPIRED = 5;
	
	// int, Define how many old password will be checked
	public static final int SETTING_CHECK_OLD_PASSWORD = 6;
	
	// int, Define how many old password will be checked
	public static final int SETTING_CHECK_LENGTH_PIN = 7;
	
	public static final int SETTING_CHECK_LENGTH_CARD_NO = 8;
	
	public static final int SETTING_CHALLENGE_PIN_LENGTH = 9;
	
	public static final int SETTING_AUTHORIZE_DATA_LIST = 10;
	
	public static final int SETTING_CHECK_LENGTH_ACC_NO = 16;
	
	public static final int SETTING_MINIMUM_LENGTH_BANK_CODE= 26;
	
	public static final int SETTING_BANK_ACCOUNT_LENGTH	= 28;
	
	public static final int SETTING_COUNTRY_CODE			= 51;  // string
	public static final int SETTING_DEFAULT_CURR_HOST		= 52;  // string
	public static final int SETTING_CACHE_TIMEOUT			= 53;  // int, timeout for cache (sec)
	public static final int SETTING_RETRY_CHALLENGE_PIN		= 54;  // int
	public static final int SETTING_RC_CIF_TERMINATED		= 55;  // string
	public static final int SETTING_MINIMUM_TRANSFER		= 56;  // double
	public static final int SETTING_BLAST_SMS_TIME			= 57;  // string
	
	public static final int SETTING_ROOT_ID_MENU_EMONEY				= 1;	  // int
	public static final int SETTING_ROOT_ID_MENU_BRANCHLESS			= 2;	  // int

	public static final int SETTING_USER_PASSWORD_MODE		= 70;//int  0=FILL MANUALLY, 1=RANDOMIZED BY SYSTEM
	public static final int SETTING_AUTHORIZE_CIF			= 71;//int  0=NOT REQUIRED IMMIDIATE AUTH, 1=REQUIRED IMMIDIATE AUTH
	
	private static final int[][] LIST_SETTING_DEFAULT_INT = {
			new int[] { SETTING_MAX_LAST_UPDATED, 5 },
			new int[] { SETTING_PASSWORD_MIN_LENGTH, 6 },
			new int[] { SETTING_MAX_INVALID_LOGIN, 3 },
			new int[] { SETTING_CHECK_OLD_PASSWORD, 3 },
			new int[] { SETTING_MINIMUM_LENGTH_BANK_CODE, 3 },
			new int[] { SETTING_USER_PASSWORD_MODE, 0 },
			new int[] { SETTING_BANK_ACCOUNT_LENGTH, 16 },};

	@Autowired
	private SystemSettingMapper settingMapper;

	@Autowired
	private BizMessageService messageService;

	private List<SystemSetting> settings;

	private SimpleDateFormat sdfShort = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat sdfLong = new SimpleDateFormat("yyyyMMddHHmmss");

	private int getDefaultInt(int settingId) {
		for (int i = 0; i < LIST_SETTING_DEFAULT_INT.length; i++) {
			int[] data = LIST_SETTING_DEFAULT_INT[i];
			if (data[0] == settingId)
				return data[1];
		}
		return 0;
	}

	private String getDefaultString(int settingId) {
		return "";
	}

	private SystemSetting getSetting(int id) throws MmbsWebException {
		if (settings == null) {
			reload();
		}
		for (SystemSetting setting : settings) {
			if (setting.getId() == id)
				return setting;
		}
		getLogger().warn("No Data for Setting for Id: {}", id);
		throw new MmbsWebException(
				MmbsWebException.NE_DATA_NOT_FOUND,
				new String[] { messageService.getMessageFor("label.setting"),"" + id });
	}

	// need to synchronize here to provide thread safe operation
	// NOT TESTED YET
	public synchronized void reload() throws MmbsWebException {
		settings = settingMapper.findSystemSettingAll();
		if (settings == null) {
			getLogger().warn("No Data for Setting");
			throw new MmbsWebException(
					MmbsWebException.NE_SETTING_EMPTY);
		}
		if (getLogger().isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Setting has been loaded [");
			int i = 0;
			for (SystemSetting setting : settings) {
				sb.append(setting.getId() + ":" + setting.getSettingName()
						+ "=" + setting.getSettingValue());
				if (++i < settings.size())
					sb.append(",");
			}
			sb.append("]");
			getLogger().info(sb.toString());
		}
	}

	public boolean getSettingAsBoolean(int id) throws MmbsWebException {
		SystemSetting setting = getSetting(id);
		if (setting.getValueType().equalsIgnoreCase(TYPE_BOOLEAN))
			return "TRUE".equalsIgnoreCase(setting.getSettingValue())
					|| "1".equals(setting.getSettingValue())
					|| "T".equalsIgnoreCase(setting.getSettingValue());
		getLogger().debug("Invalid setting type, setting: {}", setting);
		throw new MmbsWebException(
				MmbsWebException.NE_SETTING_INVALID_TYPE, new String[] {
						"" + setting.getId(), setting.getValueType() });
	}

	public Date getSettingAsDate(int id) throws MmbsWebException {
		SystemSetting setting = getSetting(id);
		try {
			if (setting.getValueType().equalsIgnoreCase(TYPE_DATE)) {
				return sdfShort.parse(setting.getSettingValue());
			} else if (setting.getValueType().equalsIgnoreCase(TYPE_DATETIME)) {
				return sdfLong.parse(setting.getSettingValue());
			}
		} catch (NumberFormatException pe) {
			getLogger().warn("Unable to parse to date/time, setting: {}",
					setting);
			throw new MmbsWebException(
					MmbsWebException.NE_SETTING_INVALID_TYPE,
					new String[] { "" + setting.getId(), setting.getValueType() });
		} catch (Exception e) {
			getLogger()
					.warn("Unknown error in getSettingAsDate, setting: {}, exception: {}",
							setting, e.getMessage());
			throw new MmbsWebException(e);
		}
		getLogger().debug("Invalid setting type, setting: {}", setting);
		throw new MmbsWebException(
				MmbsWebException.NE_SETTING_INVALID_TYPE, new String[] {
						"" + setting.getId(), setting.getValueType() });
	}

	public double getSettingAsDouble(int id) throws MmbsWebException {
		SystemSetting setting = getSetting(id);
		try {
			if (setting.getValueType().equalsIgnoreCase(TYPE_DOUBLE)) {
				return Double.parseDouble(setting.getSettingValue());
			}
		} catch (NumberFormatException pe) {
			getLogger().warn("Unable to parse to double, setting: {}", setting);
			throw new MmbsWebException(
					MmbsWebException.NE_SETTING_INVALID_TYPE,
					new String[] { "" + setting.getId(), setting.getValueType() });
		} catch (Exception e) {
			getLogger()
					.warn("Unknown error in getSettingAsDouble, setting: {}, exception: {}",
							setting, e.getMessage());
			throw new MmbsWebException(e);
		}
		getLogger().debug("Invalid setting type, setting: {}", setting);
		throw new MmbsWebException(
				MmbsWebException.NE_SETTING_INVALID_TYPE, new String[] {
						"" + setting.getId(), setting.getValueType() });
	}

	public int getSettingAsInt(int id) {
		SystemSetting setting = null;
		try {
			setting = getSetting(id);
			if (setting.getValueType().equalsIgnoreCase(TYPE_INT)) {
				return Integer.parseInt(setting.getSettingValue());
			}
			getLogger().debug("Invalid setting type, setting: {}", setting);
		} catch (NumberFormatException pe) {
			getLogger()
					.warn("Unable to parse to integer, setting: {}", setting);
		} catch (MmbsWebException jwe) {
		} catch (Exception e) {
			getLogger()
					.warn("Unknown error in getSettingAsInt, setting: {}, exception: {}",
							setting, e.getMessage());
		}
		return getDefaultInt(id);
	}

	public Integer[] getSettingAsRange(int id) {
		SystemSetting setting = null;
		try {
			setting = getSetting(id);
			if (setting.getValueType().equalsIgnoreCase(TYPE_STRING)) {
				String[] rangeStr = setting.getSettingValue().split(",");
				return new Integer[] { Integer.parseInt(rangeStr[0]), Integer.parseInt(rangeStr[1]) };
			}
			getLogger().debug("Invalid setting type, setting: {}", setting);
		} catch (NumberFormatException pe) {
			getLogger()
					.warn("Unable to parse to integer, setting: {}", setting);
		} catch (MmbsWebException jwe) {
		} catch (Exception e) {
			getLogger()
					.warn("Unknown error in getSettingAsInt, setting: {}, exception: {}",
							setting, e.getMessage());
		}
		return new Integer[] { Integer.MIN_VALUE, Integer.MAX_VALUE };
	}

	public String getSettingAsString(int id) {
		SystemSetting setting = null;
		try {
			setting = getSetting(id);
			if (setting.getValueType().equalsIgnoreCase(TYPE_STRING))
				return setting.getSettingValue();
			getLogger().debug("Invalid setting type, setting: {}", setting);
		} catch (MmbsWebException jwe) {
		} catch (Exception e) {
			getLogger()
					.warn("Unknown error in getSettingAsInt, setting: {}, exception: {}",
							setting, e.getMessage());
		}
		return getDefaultString(id);
	}

	public List<SystemSetting> getSettings() throws MmbsWebException {
		if (settings == null)
			reload();
		return settings;
	}
	
	public SystemSetting getSettingById(int id) throws MmbsWebException {
		if (settings == null)
			reload();
		for (SystemSetting systemSetting : settings) {
			if(systemSetting.getId() == id)
				return systemSetting;
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public WebResultVO update(SystemSetting setting, UserDataLoginVO loginVO, Locale language)
			throws MmbsWebException {
		WebResultVO wrv = new WebResultVO();
		getLogger().debug("saveSetting: {}", setting);
		setting.setUpdatedBy(loginVO.getId());
		if(StringUtils.isEmpty(setting.getSettingValue()))
		{
			throw new MmbsWebException(MmbsWebException.NE_MISSING_INPUT,new String[] {messageService.getMessageFor("l.settingValue")});
		}
		// validate setting
		if (TYPE_STRING.equals(setting.getValueType())) {
			// nothing todo, accept all value
		} else if (TYPE_BOOLEAN.equals(setting.getValueType())) {
			String[] listS = new String[] { "TRUE", "T", "1", "FALSE", "F", "0" };
			boolean found = false;
			for (String s : listS) {
				if (s.equalsIgnoreCase(setting.getSettingValue())) {
					found = true;
					break;
				}
			}
			if (!found){
				LOGGER.warn("Not Found for Setting Id : {}, Setting Value Type: {}" + setting.getId(), setting.getValueType());
//				throw new MmbsWebException(
//						MmbsWebException.NE_SETTING_INVALID_TYPE,
//						new String[] { "" + setting.getId(),
//								setting.getValueType() });
			}
		} else if (TYPE_INT.equals(setting.getValueType())
				|| TYPE_DOUBLE.equals(setting.getValueType())
				|| TYPE_DATE.equals(setting.getValueType())
				|| TYPE_DATETIME.equals(setting.getValueType())) {
			try {
				if (TYPE_INT.equals(setting.getValueType()))
					Integer.parseInt(setting.getSettingValue());
				else if (TYPE_DOUBLE.equals(setting.getValueType()))
					Double.parseDouble(setting.getSettingValue());
				else if (TYPE_DATE.equals(setting.getValueType()))
					sdfShort.parse(setting.getSettingValue());
				else if (TYPE_DATETIME.equals(setting.getValueType()))
					sdfLong.parse(setting.getSettingValue());
			} catch (NumberFormatException pe) {
				getLogger().warn("Unable to parse setting: {}, NumberFormatException: {}", setting, pe.getMessage());
//				throw new MmbsWebException(
//						MmbsWebException.NE_SETTING_INVALID_TYPE,
//						new String[] { "" + setting.getId(),
//								setting.getValueType() });
			} catch (Exception e) {
				getLogger()
						.warn("Unknown error in getSettingAsXXX, setting: {}, exception: {}",
								setting, e.getMessage());
//				throw new MmbsWebException(e);
			}
		} else {
			LOGGER.warn("Unknown Type Setting for Setting Id : {}, Setting Value Type: {}" + setting.getId(), setting.getValueType());
//			throw new MmbsWebException(
//					MmbsWebException.NE_SETTING_INVALID_TYPE,
//					new String[] { "" + setting.getId(), setting.getValueType() });
		}

		// save data
		try {
			settingMapper.updateSystemSetting(setting);
			
			wrv.setRc(WebConstants.RESULT_SUCCESS);
			wrv.setMessage(messageService.getMessageFor("rm.generalMessage",new String[] {messageService.getMessageFor("l.systemSetting", language), messageService.getMessageFor("l.updated", language)}, language));
			wrv.setType(WebConstants.TYPE_UPDATE);
			wrv.setPath(WebConstants.PATH_UPDATE_SYSTEM_SETTING);
			
			Collection<String> excludes = new ArrayList<String>();
			excludes.add("updatedBy");
			excludes.add("updatedOn");
			reload();
		} catch (Exception e) {
			getLogger().error("Exception: " + e, e);
			throw new MmbsWebException(e);
		}
		
		return wrv;
	}

//	public SystemSetting findById(int settingId) throws MayapadaWebException {
//		try {
//			SystemSetting SystemSetting = settingMapper.findSystemSettingById(settingId);
//			if (SystemSetting == null)
//				throw new MayapadaWebException(
//						MayapadaWebException.NE_DATA_NOT_FOUND, new String[] {
//								"Setting", "" + settingId });
//			return SystemSetting;
//		} catch (MayapadaWebException jme) {
//			throw jme;
//		} catch (Exception e) {
//			getLogger().warn("Exception: " + e, e);
//			throw new MayapadaWebException(e);
//		}
//	}

	protected Logger getLogger() {
		return LOGGER;
	}

	public List<SystemSetting> findByParam(SystemSettingParamVO systemParamVO)
			throws Exception {
		try {
			List<SystemSetting> listSetting = settingMapper
					.findSystemSettingByParam(systemParamVO);
			return listSetting;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<SystemSetting>();
		}
	}

	public int countByParam(SystemSettingParamVO systemParamVO) {
		try {
			int record = settingMapper.countSystemSettingByParam(systemParamVO);
			return record;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return 0;
		}
	}

	public SystemSetting findSystemSettingBySettingName(String settingName) {
		return settingMapper.findSystemSettingBySettingName(settingName);
	}

}
