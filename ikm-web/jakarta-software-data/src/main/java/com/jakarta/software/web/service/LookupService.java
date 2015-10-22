package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jakarta.software.web.entity.Lookup;
import com.jakarta.software.web.mapper.LookupMapper;

@Service
public class LookupService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LookupService.class);

	public static final int CAT_LOGIN_USER						= 1;
	public static final int CAT_AGENDA_TYPE						= 2;
	public static final int CAT_LIMIT_TYPE						= 3;
	public static final int CAT_CURR_CODE						= 4;
	public static final int CAT_ACCOUNT_TYPE_DISPLAY			= 5;
	public static final int CAT_CIF_GROUP						= 6;
	public static final int CAT_ACCOUNT_TYPE					= 7;
	public static final int CAT_STATUS							= 8;
	public static final int CAT_AUTH_STATUS						= 9;
	public static final int CAT_SWITCHING						= 10;
	public static final int CAT_PRODUCT_TYPE					= 11;
	public static final int CAT_LIMIT_TRF_CODE					= 12;
	public static final int CAT_TRX_TYPE						= 13;
	public static final int CAT_TRX_STATUS						= 14;
	public static final int CAT_CS_STATUS						= 15;
	public static final int CAT_VIEW_HISTORY_ACTIVITY			= 16;
	public static final int CAT_CHANNEL_TYPE					= 17;
	public static final int CAT_TRX_CODE						= 18;
	public static final int CAT_PIN_STATUS						= 19;
	public static final int CAT_TIME_TYPE						= 20;
	public static final int CAT_DAY_NAME						= 21;
	public static final int CAT_BILL_PAYMENT_TRX_CODE			= 22;
	public static final int CAT_ISO_TYPE						= 23;
	public static final int CAT_BILL_PAYMENT_CATEGORY			= 24;
	public static final int CAT_SERVICE_TYPE 					= 25;
	public static final int CAT_BILL_TYPE 						= 26;
	public static final int CAT_BLAST_STATUS					= 27;
	public static final int CAT_CHANNEL_SMI						= 28;
	public static final int CAT_OPERATOR						= 29;
	public static final int CAT_MONTH							= 30;
	
	@Autowired
	private LookupMapper lookupMapper;
	
	private List<Lookup> listLookup;
	
	private synchronized void loadLookup() {
		listLookup = lookupMapper.findLookupAll();
		if (listLookup == null)
			listLookup = new ArrayList<Lookup>();
		if (LOGGER.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder(listLookup.size());
			sb.append("Lookup has been loaded [");
			int i = 0;
			for (Lookup lookup: listLookup) {
				sb.append(lookup.getLookupCat()).append(":");
				sb.append(lookup.getLookupValue()).append("=");
				sb.append(lookup.getLookupDesc());
				if (++i < listLookup.size())
					sb.append(",");
			}
			sb.append("]");
			LOGGER.info(sb.toString());
		}
	}
	
	public Map<String, String> findLookupForSelection(int lookupCat) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<Lookup> list = findLookupByCat(lookupCat);
		for (Lookup lookup: list) {
			map.put("" + lookup.getLookupValue(), lookup.getLookupDesc());
		}
		return map;
	}
	
	public List<Lookup> findLookupByCat(int lookupCat) {
		try {
			if (listLookup == null)
				loadLookup();
			List<Lookup> list = new LinkedList<Lookup>();
			for (Lookup lookup: listLookup) {
				if (lookup.getLookupCat() == lookupCat)
					list.add(lookup);
			}
			return list;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return new ArrayList<Lookup>();
		}
	}
	
	public Lookup findLookupByCatValue(int lookupCat, String lookupValue) {
		try {
			if (listLookup == null)
				loadLookup();
			for (Lookup lookup: listLookup) {
				if (lookup.getLookupCat() == lookupCat && lookup.getLookupValue().equals(lookupValue))
					return lookup;
			}
			return null;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return null;
		}
	}
	
	public Lookup findLookupByCatDesc(int lookupCat, String lookupDesc) {
		try {
			if (lookupDesc == null) lookupDesc = "";
			if (listLookup == null)
				loadLookup();
			for (Lookup lookup: listLookup) {
				if (lookup.getLookupCat() == lookupCat && lookupDesc.equals(lookup.getLookupValue()) )
					return lookup;
			}
			return null;
		} catch (Exception e) {
			LOGGER.warn("Exception: " + e, e);
			return null;
		}
	}
}
