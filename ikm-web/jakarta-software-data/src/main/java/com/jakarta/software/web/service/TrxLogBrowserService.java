package com.jakarta.software.web.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jakarta.software.web.data.TrxLogBrowserVO;
import com.jakarta.software.web.data.param.TrxLogBrowserParamVO;
import com.jakarta.software.web.mapper.TrxLogBrowserMapper;

@Service
public class TrxLogBrowserService {
	protected static final Logger LOGGER = LoggerFactory.getLogger(TrxLogBrowserService.class);
	
	@Autowired
	private TrxLogBrowserMapper trxLogBrowserMapper;
	
	public int countTrxLogBrowserByParam(TrxLogBrowserParamVO paramVO) throws MmbsWebException{
		LOGGER.debug("Processing -> countMerchantByParam: [{}]", paramVO);
		try {
			int count = trxLogBrowserMapper.countTrxLogBrowserByParam(paramVO);
			return count;
		} catch (Exception e) {
			LOGGER.error("Error: " +e, e);
			return 0 ;
		}
	}
	
	public List<TrxLogBrowserVO> findTrxLogBrowserByParam(TrxLogBrowserParamVO paramVO) throws MmbsWebException{
		LOGGER.debug("Processing -> findTrxLogBrowserByParam: [{}]", paramVO);
		try {
			List<TrxLogBrowserVO> listTrxLogBrowser = trxLogBrowserMapper.findTrxLogBrowserByParam(paramVO);
			return listTrxLogBrowser;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TrxLogBrowserVO>();
		}
	}
	
	public List<TrxLogBrowserVO> findTrxLogBrowserByParamNoPaging(TrxLogBrowserParamVO paramVO){
		LOGGER.debug("Processing -> findTrxLogBrowserByParamNoPaging: [{}]", paramVO);
		try {
			List<TrxLogBrowserVO> listTrxLogBrowser = trxLogBrowserMapper.findTrxLogBrowserByParamNoPaging(paramVO);
			return listTrxLogBrowser;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return new ArrayList<TrxLogBrowserVO>();
		}
	}
	
	public TrxLogBrowserVO findTrxLogBrowserDetailBySyslogno(String syslogno) throws MmbsWebException{
		LOGGER.debug("Processing -> findTrxLogBrowserDetailBySyslogno: [{}]", syslogno);
		try {
			TrxLogBrowserVO listTrxLogBrowser = trxLogBrowserMapper.findTrxLogBrowserDetailBySyslogno(syslogno);
			return listTrxLogBrowser;
		} catch (Exception e) {
			LOGGER.error("ERROR: " + e, e);
			return null;
		}
	}
}
